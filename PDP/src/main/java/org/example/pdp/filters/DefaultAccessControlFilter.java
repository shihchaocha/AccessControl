package org.example.pdp.filters;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultAccessControlFilter implements AccessControlFilter {
    @Override
    public boolean doFiter(HashMap<String, String> attributes) {
        return false;
    }

    @Override
    public ArrayList AttributeUsed() {
        return null;
    }
}
