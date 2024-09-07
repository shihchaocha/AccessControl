package org.example.pap.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchingCriteriaTest {

    @Test
    void testSimpleFromString() {
        MatchingCriteria criteria = MatchingCriteria.fromString("age > 30");
        assertEquals(criteria.getMatchingType(), MatchingCriteria.MatchingType.LEAF_NODE);
        assertEquals(criteria.getAttributeDesignator(), "age");
        assertEquals(criteria.getAttributeValue(), "30");
        assertEquals(criteria.getMatchOperator(), ">");
    }
}