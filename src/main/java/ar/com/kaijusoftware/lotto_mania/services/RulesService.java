package ar.com.kaijusoftware.lotto_mania.services;

import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface RulesService {
    <F, R> Pair<List<R>, List<Throwable>> executeRuleGroup(
            String ruleGroupName,
            Class<R> resultClass,
            Map<String, Object> parameters,
            List<F> facts
    );
}
