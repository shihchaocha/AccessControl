package org.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.example.models.MatchingType;

import java.util.List;

public class MatchingCriteriaDTO {
    private String attributeDesignator;
    private String matchOperator;
    private String attributeValue;

    @JsonManagedReference
    private List<MatchingCriteriaDTO> subCriteria;
    private boolean notLogic = false;
    private MatchingType matchingType;

    public boolean isNotLogic() {
        return notLogic;
    }

    public void setNotLogic(boolean notLogic) {
        this.notLogic = notLogic;
    }

    // Getters and Setters
    public String getAttributeDesignator() {
        return attributeDesignator;
    }

    public void setAttributeDesignator(String attributeDesignator) {
        this.attributeDesignator = attributeDesignator;
    }

    public String getMatchOperator() {
        return matchOperator;
    }

    public void setMatchOperator(String matchOperator) {
        this.matchOperator = matchOperator;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public List<MatchingCriteriaDTO> getSubCriteria() {
        return subCriteria;
    }

    public void setSubCriteria(List<MatchingCriteriaDTO> subCriteria) {
        this.subCriteria = subCriteria;
    }

    public MatchingCriteriaDTO getParentCriteria() {
        return parentCriteria;
    }

    public void setParentCriteria(MatchingCriteriaDTO parentCriteria) {
        this.parentCriteria = parentCriteria;
    }

    @JsonBackReference
    private MatchingCriteriaDTO parentCriteria;

    public void addSubCriteria(MatchingCriteriaDTO subCriterion) {
        this.subCriteria.add(subCriterion);
        subCriterion.setParentCriteria(this); // 設置子對象的 parentCriteria
    }

    public MatchingType getMatchingType() {
        return matchingType;
    }

    public void setMatchingType(MatchingType matchingType) {
        this.matchingType = matchingType;
    }
}
