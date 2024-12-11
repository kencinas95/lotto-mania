package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.services.RulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RulesServiceImpl implements RulesService {
    private final KieContainer kieContainer;

    @Override
    public <F, R> Pair<List<R>, List<Throwable>> executeRuleGroup(
            final String ruleGroupName,
            final Class<R> resultClass,
            final Map<String, Object> parameters,
            final List<F> facts) {
        log.debug("Executing rule group {}", ruleGroupName);
        log.debug("Parameters: {}", parameters);
        log.debug("Facts: {}", facts);

        // Rules engine session
        KieSession kieSession = kieContainer.newKieSession();

        // Set global parameters
        kieSession.setGlobal("logger", LoggerFactory.getLogger(ruleGroupName));
        for (String paramName : parameters.keySet()) {
            kieSession.setGlobal(paramName, parameters.get(paramName));
        }

        List<R> results = Lists.newArrayList();
        List<Throwable> errors = Lists.newArrayList();

        try {
            for (F fact : facts) {
                kieSession.insert(fact);
            }

            kieSession.getAgenda().getAgendaGroup(ruleGroupName).setFocus();
            kieSession.fireAllRules();

            results = kieSession.getObjects(resultClass::isInstance)
                    .stream()
                    .map(resultClass::cast)
                    .toList();

            errors = kieSession.getObjects(Throwable.class::isInstance)
                    .stream()
                    .map(Throwable.class::cast)
                    .toList();

        } catch (Exception ex) {
            log.error("Error executing rule group {}", ruleGroupName, ex);
        } finally {
            kieSession.dispose();
        }

        return Pair.of(results, errors);
    }
}
