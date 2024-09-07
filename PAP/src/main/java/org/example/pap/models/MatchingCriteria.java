package org.example.pap.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class MatchingCriteria {

    public enum MatchingType {
        ANY_OF,
        ALL_OF,
        LEAF_NODE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public boolean isNotLogic() {
        return notLogic;
    }

    public void setNotLogic(boolean notLogic) {
        this.notLogic = notLogic;
    }

    boolean notLogic = false;

    private String matchOperator;

    private String attributeDesignator;

    private String attributeValue;

    @Enumerated(EnumType.STRING)
    private MatchingType matchingType;

    @OneToMany(mappedBy = "parentCriteria", cascade = CascadeType.ALL, orphanRemoval = true)
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
    }

    public MatchingCriteria getParentCriteria() {
        return parentCriteria;
    }

    public void setParentCriteria(MatchingCriteria parentCriteria) {
        this.parentCriteria = parentCriteria;
    }

    public static String[] splitString(String input) {
        if (input == null || input.isEmpty()) {
            return new String[] { null, null };
        }

        String[] result = input.split("\\.", 2);
        if (result.length == 1) {
            return new String[] { result[0], null };
        }
        return result;
    }

    public static MatchingCriteria fromString(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // 支援的比較運算子
        String[] operators = { ">", "<", ">=", "<=", "==", "!=" };

        String selectedOperator = null;
        for (String operator : operators) {
            if (input.contains(operator)) {
                selectedOperator = operator;
                break;
            }
        }

        if (selectedOperator == null) {
            throw new IllegalArgumentException("Input string must contain a valid comparison operator");
        }

        // 依據運算子分割字串
        String[] parts = input.split(selectedOperator, 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Input string must contain two parts separated by the operator");
        }

        String attributeDesignator = parts[0].trim();
        String attributeValue = parts[1].trim();

        MatchingCriteria criteria = new MatchingCriteria();
        criteria.setAttributeDesignator(attributeDesignator);
        criteria.setMatchOperator(selectedOperator);
        criteria.setAttributeValue(attributeValue);
        criteria.setMatchingType(MatchingType.LEAF_NODE);

        return criteria;
    }
}
