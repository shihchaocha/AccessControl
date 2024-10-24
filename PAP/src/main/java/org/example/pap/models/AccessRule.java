package org.example.pap.models;

import jakarta.persistence.*;


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

    @ManyToOne(cascade = CascadeType.ALL)  // 加入這行來啟用級聯刪除
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

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    private int decision;

    public String getFilterName() {
        if(filterName==null || filterName.trim().length()==0)
            filterName = "default";
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    private String filterName;

}
