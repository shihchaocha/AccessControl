package org.example.pap.models;

import java.util.*;

public class MatchingCriteriaParser {

    public static MatchingCriteria fromComplexString(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // Tokenization: 將字串分割成 tokens
        List<String> tokens = tokenize(input);

        // Parsing: 轉換為 MatchingCriteria
        return parseExpression(tokens);
    }

    private static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Set<String> comparisonOperators = new HashSet<>(Arrays.asList(">", "<", ">=", "<=", "==", "!="));
        Set<Character> logicalOperators = new HashSet<>(Arrays.asList('(', ')', ' ', 'o', 'a')); // handling 'or' and 'and'

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            // Handling parenthesis
            if (c == '(' || c == ')') {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                tokens.add(String.valueOf(c));
                continue;
            }

            // Handling logical operators "and" and "or"
            if (c == 'o' && input.startsWith("or", i)) {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                tokens.add("or");
                i += 1;
                continue;
            } else if (c == 'a' && input.startsWith("and", i)) {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                tokens.add("and");
                i += 2;
                continue;
            }

            // Building operators or variables
            sb.append(c);

            // Check for double-character operators
            if (i + 1 < input.length()) {
                String possibleOperator = sb.toString() + input.charAt(i + 1);
                if (comparisonOperators.contains(possibleOperator)) {
                    tokens.add(possibleOperator);
                    sb.setLength(0);
                    i++; // skip next character
                    continue;
                }
            }

            // Check for single-character operators
            if (comparisonOperators.contains(sb.toString())) {
                tokens.add(sb.toString());
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }

        return tokens;
    }

    // Parsing expressions: 解析 tokens 並構建 MatchingCriteria 樹
    private static MatchingCriteria parseExpression(List<String> tokens) {
        Stack<MatchingCriteria> criteriaStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    MatchingCriteria right = criteriaStack.pop();
                    MatchingCriteria left = criteriaStack.pop();
                    String operator = operatorStack.pop();
                    criteriaStack.push(combineCriteria(left, right, operator));
                }
                operatorStack.pop();
            } else if (token.equals("and") || token.equals("or")) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token)) {
                    MatchingCriteria right = criteriaStack.pop();
                    MatchingCriteria left = criteriaStack.pop();
                    String operator = operatorStack.pop();
                    criteriaStack.push(combineCriteria(left, right, operator));
                }
                operatorStack.push(token);
            } else {
                criteriaStack.push(MatchingCriteria.fromString(token));
            }
        }

        while (!operatorStack.isEmpty()) {
            MatchingCriteria right = criteriaStack.pop();
            MatchingCriteria left = criteriaStack.pop();
            String operator = operatorStack.pop();
            criteriaStack.push(combineCriteria(left, right, operator));
        }

        return criteriaStack.pop();
    }

    private static int precedence(String operator) {
        if (operator.equals("and")) {
            return 2;
        } else if (operator.equals("or")) {
            return 1;
        }
        return 0;
    }

    private static MatchingCriteria combineCriteria(MatchingCriteria left, MatchingCriteria right, String operator) {
        MatchingCriteria parent = new MatchingCriteria();
        List<MatchingCriteria> subCriteria = new ArrayList<>();
        subCriteria.add(left);
        subCriteria.add(right);

        if (operator.equals("and")) {
            parent.setMatchingType(MatchingCriteria.MatchingType.ALL_OF);
        } else if (operator.equals("or")) {
            parent.setMatchingType(MatchingCriteria.MatchingType.ANY_OF);
        }

        parent.setSubCriteria(subCriteria);
        return parent;
    }
}