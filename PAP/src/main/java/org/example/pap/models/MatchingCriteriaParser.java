package org.example.pap.models;

import org.example.models.MatchingType;

import java.util.*;

public class MatchingCriteriaParser {

    // 支持的操作符優先級
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = Map.of(
            "||", 1,
            "&&", 2,
            ">=", 3,
            "<=", 3,
            "==", 3,
            ">", 3,
            "<", 3,
            "in", 3,
            "like", 3
    );

    public static MatchingCriteria parse(String input) {
        List<String> tokens = tokenize(input);
        Stack<MatchingCriteria> outputStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        String leftOperand = null;
        String operator = null;

        for (String token : tokens) {
            if (isOperator(token)) {
                // 確認是邏輯運算符還是比較運算符
                if (leftOperand != null && operator == null) {
                    operator = token;
                } else if (token.equals("&&") || token.equals("||")) {
                    operatorStack.push(token);
                } else {
                    throw new IllegalArgumentException("運算符前沒有操作數");
                }
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processOperator(operatorStack.pop(), outputStack);
                }
                operatorStack.pop();  // 移除 '('
            } else if (operator != null) {
                // 確定有操作數和運算符後，生成 LEAF_NODE
                MatchingCriteria leafNode = createLeafNode(leftOperand, operator, token);
                outputStack.push(leafNode);
                leftOperand = null;
                operator = null;
            } else {
                leftOperand = token;
            }
        }

        // 處理剩下的運算符
        while (!operatorStack.isEmpty()) {
            processOperator(operatorStack.pop(), outputStack);
        }

        return outputStack.pop();
    }

    // Tokenizer - 分割字串成為 token
    private static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            // 處理多字符運算符，例如 >=, <=, ==, !=
            if (c == '>' || c == '<' || c == '=' || c == '!') {
                if (i + 1 < input.length() && input.charAt(i + 1) == '=') {
                    if (token.length() > 0) {
                        tokens.add(token.toString());
                        token.setLength(0);
                    }
                    tokens.add(c + "=");  // 添加 >=, <=, ==, !=
                    i++;  // 跳過下個字符
                    continue;
                }
            }
            // 處理 && 和 ||
            if (c == '&' && i + 1 < input.length() && input.charAt(i + 1) == '&') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add("&&");
                i++;  // 跳過下個字符
                continue;
            } else if (c == '|' && i + 1 < input.length() && input.charAt(i + 1) == '|') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add("||");
                i++;  // 跳過下個字符
                continue;
            }
            // 處理單字符運算符，例如 <, >, = 等
            if (c == '<' || c == '>' || c == '=' || c == '!' || c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(String.valueOf(c));  // 添加運算符或括號
            } else {
                token.append(c);  // 將字符加入當前 token
            }
        }

        // 添加最後的 token
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens;
    }


    private static boolean isOperator(String token) {
        return OPERATOR_PRECEDENCE.containsKey(token);
    }

    private static int precedence(String operator) {
        return OPERATOR_PRECEDENCE.get(operator);
    }

    private static void processOperator(String operator, Stack<MatchingCriteria> outputStack) {
        if (outputStack.size() < 2) {
            throw new IllegalArgumentException("無法解析的運算符 " + operator + " 左右的表達式不完整。");
        }

        MatchingCriteria right = outputStack.pop();
        MatchingCriteria left = outputStack.pop();

        MatchingCriteria criteria = new MatchingCriteria();

        if (operator.equals("&&")) {
            criteria.setMatchingType(MatchingType.ALL_OF);
        } else if (operator.equals("||")) {
            criteria.setMatchingType(MatchingType.ANY_OF);
        } else {
            throw new IllegalArgumentException("未知的運算符: " + operator);
        }

        List<MatchingCriteria> subCriteria = new ArrayList<>();
        subCriteria.add(left);
        subCriteria.add(right);
        criteria.setSubCriteria(subCriteria);
        outputStack.push(criteria);
    }

    private static MatchingCriteria createLeafNode(String leftOperand, String operator, String rightOperand) {
        MatchingCriteria leafNode = new MatchingCriteria();
        leafNode.setMatchingType(MatchingType.LEAF_NODE);
        leafNode.setMatchOperator(operator);
        leafNode.setAttributeDesignator(removeExtraParentheses(leftOperand.trim()));
        leafNode.setAttributeValue(removeExtraParentheses(rightOperand.trim()));
        return leafNode;
    }

    private static String removeExtraParentheses(String token) {
        while (token.startsWith("(") && token.endsWith(")") && matchingParentheses(token)) {
            token = token.substring(1, token.length() - 1).trim();
        }
        return token;
    }

    private static boolean matchingParentheses(String token) {
        int counter = 0;
        for (char c : token.toCharArray()) {
            if (c == '(') {
                counter++;
            } else if (c == ')') {
                counter--;
                if (counter < 0) {
                    return false;  // 早期關閉的括號
                }
            }
        }
        return counter == 0;
    }
}
