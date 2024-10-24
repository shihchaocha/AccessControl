package org.example.pdp.filters;

import org.example.models.AccessRuleDTO;
import org.example.models.MatchingCriteriaDTO;
import org.example.models.MatchingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultAccessControlFilter implements AccessControlFilter {

    @Override
    public boolean doFilter(HashMap<String, Object> attributes, List<AccessRuleDTO> accessRules) {

        HashMap<String, MatchingData> map = new HashMap<>();

        for(AccessRuleDTO accessRule: accessRules) {
            MatchingCriteriaDTO matchingCriteriaDTO =  accessRule.getCriteria();
            try {
                matchingCriteriaDTO.processMatchingCriteria(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MatchingData.dump(map);

        for(AccessRuleDTO accessRule: accessRules) {
            MatchingCriteriaDTO matchingCriteriaDTO = accessRule.getCriteria();
        }

        return false;
    }

    @Override
    public ArrayList AttributeUsed() {
        return null;
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }
}
