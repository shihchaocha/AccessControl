package org.example.pap;

import org.example.pap.models.AccessRule;
import org.example.pap.models.AccessRuleRepository;
import org.example.pap.models.MatchingCriteria;
import org.example.pap.models.MatchingCriteriaRepository;
import org.example.pap.services.AccessRuleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PapApplication {

    public static void main(String[] args) {
        SpringApplication.run(PapApplication.class, args);
    }
}
