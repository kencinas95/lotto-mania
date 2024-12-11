package ar.com.kaijusoftware.lotto_mania.configs;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RulesConfiguration {
    @Value("${app.rules.files}")
    private String[] ruleFiles;

    @Bean
    public KieServices kieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieFileSystem kieFileSystem(KieServices ks) {
        if (Arrays.asList(ruleFiles).isEmpty()) {
            throw new IllegalArgumentException("The rule files are empty!");
        }

        KieFileSystem kfs = ks.newKieFileSystem();
        for (String ruleFile : ruleFiles) {
            kfs.write(ResourceFactory.newClassPathResource(ruleFile));
        }
        return kfs;
    }

    @Bean
    public KieContainer kieContainer(KieServices kieServices, KieFileSystem kieFileSystem) {
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();

        KieRepository kr = kieServices.getRepository();

        ReleaseId releaseId = kr.getDefaultReleaseId();

        return kieServices.newKieContainer(releaseId);
    }
}
