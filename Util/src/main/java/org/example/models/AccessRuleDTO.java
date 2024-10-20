package org.example.models;

public class AccessRuleDTO {
    private Long id;
    private String target;
    private MatchingCriteriaDTO criteria;
    private String decision;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public MatchingCriteriaDTO getCriteria() {
        return criteria;
    }

    public void setCriteria(MatchingCriteriaDTO criteria) {
        this.criteria = criteria;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    private String filterName;

}
