package org.example.pdp.filters;

import java.util.ArrayList;
import java.util.HashMap;

public interface AccessControlFilter {

    public boolean doFiter(HashMap<String,String> attributes);

    public ArrayList AttributeUsed();

}
