package org.example.models;

import java.util.HashMap;
import java.util.Map;

public class MatchingData {
    String name;
    HashMap<String, Long> attributes;

    public MatchingData(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
    }

    public static void fromString(String input, HashMap<String, MatchingData> map) throws Exception{
        // 先去除前後空白
        input = input.trim();

        // 根據 "." 分割物件和屬性
        String[] parts = input.split("\\.");
        if (parts.length == 1) {
            // 單一變數情況
            String varName = parts[0].trim();
            if (isValidName(varName)) {
                map.put(varName, new MatchingData(varName));
            }
        } else {
            // 物件和屬性情況
            String objectName = parts[0].trim();
            String attributePart = parts[1].trim();

            if (isValidName(objectName)) {
                MatchingData data = map.getOrDefault(objectName, new MatchingData(objectName));

                if (attributePart.contains("_")) {
                    String[] attrParts = attributePart.split("_");
                    if (attrParts.length == 2 && isValidName(attrParts[0])){
                        String key = attrParts[0];
                        if(isNonNegativeLong(attrParts[1])) {
                            Long value = Long.parseLong(attrParts[1]);
                            data.attributes.putIfAbsent(key, value);
                        } else {
                            data.attributes.putIfAbsent(key, -1L);
                        }
                    } else {
                        throw new Exception("attribute name is invalid");
                    }
                } else {
                    if (isValidName(attributePart)) {
                        // 如果屬性名稱不存在才加進去
                        data.attributes.putIfAbsent(attributePart, -1L);
                    } else {
                        throw new Exception("attribute name is invalid");
                    }
                }

                map.put(objectName, data);
            } else {
                throw new Exception("object name is invalid");
            }
        }
    }

    // 驗證變數名稱是否有效（不以數字開頭）
    private static boolean isValidName(String name) {
        return name.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }

    // 驗證是否為非負數長整數
    private static boolean isNonNegativeLong(String value) {
        try {
            long num = Long.parseLong(value);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void dump(HashMap<String, MatchingData> map) {
        // 顯示 HashMap 的內容
        for (Map.Entry<String, MatchingData> entry : map.entrySet()) {
            System.out.println("Object: " + entry.getKey());
            MatchingData data = entry.getValue();
            System.out.println("  Name: " + data.name);
            if (data.attributes == null || data.attributes.isEmpty()) {
                System.out.println("  Attributes: None");
            } else {
                System.out.println("  Attributes:");
                for (Map.Entry<String, Long> attr : data.attributes.entrySet()) {
                    System.out.println("    " + attr.getKey() + " = " + attr.getValue());
                }
            }
        }
    }

    public static void main(String[] args) {
        HashMap<String, MatchingData> map = new HashMap<>();

        try {
            // 單一變數
            MatchingData.fromString("A", map);

            // 物件和屬性
            MatchingData.fromString("A.B", map);
            MatchingData.fromString("A.B_3", map);
            MatchingData.fromString("A.C_5", map);
            MatchingData.fromString("A.B_10", map);  // 不會加進去因為 B 已經存在
            MatchingData.fromString("D", map);       // 單一變數
            MatchingData.fromString("E.F_G", map);   // G 無法轉為 Long，不會加進去

            // 顯示 HashMap 的內容
            dump(map);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

