package org.example.pap.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.example.models.MatchingType;

import java.util.HashMap;
import java.util.List;

@Entity
public class MatchingCriteria {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public boolean isNotLogic() {
        return notLogic;
    }

    public void setNotLogic(boolean notLogic) {
        this.notLogic = notLogic;
    }

    private boolean notLogic = false;

    private String matchOperator;

    private String attributeDesignator;

    private String attributeValue;

    @Enumerated(EnumType.STRING)
    private MatchingType matchingType;

    @OneToMany(mappedBy = "parentCriteria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MatchingCriteria> subCriteria;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MatchingCriteria parentCriteria;

    // Constructors, getters, setters, etc.

    public MatchingCriteria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchOperator() {
        return matchOperator;
    }

    public void setMatchOperator(String matchOperator) {
        this.matchOperator = matchOperator;
    }

    public String getAttributeDesignator() {
        return attributeDesignator;
    }

    public void setAttributeDesignator(String attributeDesignator) {
        this.attributeDesignator = attributeDesignator;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public MatchingType getMatchingType() {
        return matchingType;
    }

    public void setMatchingType(MatchingType matchingType) {
        this.matchingType = matchingType;
    }

    public List<MatchingCriteria> getSubCriteria() {
        return subCriteria;
    }

    public void setSubCriteria(List<MatchingCriteria> subCriteria) {
        this.subCriteria = subCriteria;
        for (MatchingCriteria criteria : subCriteria) {
            criteria.setParentCriteria(this); // 設置子對象的 parentCriteria
        }
    }

    public void addSubCriteria(MatchingCriteria subCriterion) {
        this.subCriteria.add(subCriterion);
        subCriterion.setParentCriteria(this); // 設置子對象的 parentCriteria
    }

    public MatchingCriteria getParentCriteria() {
        return parentCriteria;
    }

    public void setParentCriteria(MatchingCriteria parentCriteria) {
        this.parentCriteria = parentCriteria;
    }

    public String toString() {
        if(matchingType == MatchingType.LEAF_NODE) {
            return "(" +attributeDesignator + " " + matchOperator + " " + attributeValue+")";
        } else {
            return "(" + subCriteria.get(0) + " " + (matchingType == MatchingType.ANY_OF ? "||" : "&&") + " " + subCriteria.get(1) + ")";
        }
    }

    public void processMatchingCriteria(HashMap<String, MatchingData> map) throws Exception{
        if (matchingType == MatchingType.LEAF_NODE) {
            // 解析 LEAF_NODE 類型的 attributeDesignator 和 attributeValue
            if (attributeDesignator != null && !attributeDesignator.trim().isEmpty()) {
                MatchingData.fromString(attributeDesignator, map);
            }
            if (attributeValue != null && !attributeValue.trim().isEmpty()) {
                MatchingData.fromString(attributeValue, map);
            }
        } else if (matchingType == MatchingType.ALL_OF || matchingType == MatchingType.ANY_OF) {
            // 遞迴處理 subCriteria
            if (subCriteria != null && !subCriteria.isEmpty()) {
                for (MatchingCriteria criteria : subCriteria) {
                    criteria.processMatchingCriteria(map);
                }
            }
        }
    }
}
