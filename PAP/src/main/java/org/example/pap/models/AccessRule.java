package org.example.pap.models;

import jakarta.persistence.*;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.DefaultRulesEngine;

@Entity
public class AccessRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String target;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private MatchingCriteria criteria;

    public MatchingCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(MatchingCriteria criteria) {
        this.criteria = criteria;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static void main(String argc[]){
        AccessRule accessRule = new AccessRule();
        accessRule.setTarget("user.age>18 and user.gender=='M'");
        System.out.println(accessRule.getTarget());
        MVELRule weatherRule = new MVELRule()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when("rain == true")
                .then("System.out.println(\"It rains, take an umbrella!\");");
    }
}
