package org.example.pdp.filters;

import org.example.models.AccessRuleDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface AccessControlFilter {

    public boolean doFilter(HashMap<String,Object> attributes, List<AccessRuleDTO> accessRules);

    public ArrayList AttributeUsed();

    public boolean init() throws Exception;

}
