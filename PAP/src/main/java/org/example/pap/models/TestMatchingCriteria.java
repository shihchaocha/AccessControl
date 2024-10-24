package org.example.pap.models;


import org.example.models.MatchingData;

import java.util.HashMap;

public class TestMatchingCriteria {
    public static void main(String[] args) {
        // 創建 MatchingCriteria 對象
        String expression = "(user.id == '123' && (user.age >= 18 || (user.risk_5 < 5)) || (device.registered == 'true'))";
        MatchingCriteria criteria = MatchingCriteriaParser.parse(expression);
        HashMap<String, MatchingData> map = new HashMap<>();
        try {
            System.out.println(criteria.toString());
            MatchingCriteria criteria2 = MatchingCriteriaParser.parse(criteria.toString());
            System.out.println(criteria2.toString());

            //criteprocessMatchingCriteria(map);
            //MatchingData.dump(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
