import java.util.*;

class Parser {
    private static final Set<String> operators = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
    private static final Map<String, Integer> precedence = new HashMap<>();
    static {
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
    }

    public static RPNNode convertToRPN(String expression) {
        Stack<String> operatorStack = new Stack<>();
        Stack<RPNNode> operandStack = new Stack<>();

        List<String> tokens = tokenize(expression);

        for (String token : tokens) {
            if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processOperator(operatorStack, operandStack);
                }
                operatorStack.pop(); // Discard the '('
            } else if (operators.contains(token)) {
                while (!operatorStack.isEmpty() && precedence.getOrDefault(operatorStack.peek(), 0) >= precedence.getOrDefault(token, 0)) {
                    processOperator(operatorStack, operandStack);
                }
                operatorStack.push(token);
            } else {
                operandStack.push(new RPNNode(token));
            }
        }

        while (!operatorStack.isEmpty()) {
            processOperator(operatorStack, operandStack);
        }


        return operandStack.pop(); // The root of the RPN tree
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                sb.append(c);
            } else if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/') {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (c == ' ') {
                continue;
            } else {
                throw new IllegalArgumentException("Unsupported character: " + c);
            }
        }
        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }
        return tokens;
    }


    private static void processOperator(Stack<String> operatorStack, Stack<RPNNode> operandStack) {
        String operator = operatorStack.pop();
        RPNNode right = operandStack.pop();
        RPNNode left = operandStack.pop();
        RPNNode operatorNode = new RPNNode(operator);
        operatorNode.left = left;
        operatorNode.right = right;
        operandStack.push(operatorNode);
    }

    public static double calculateRPN(RPNNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return Double.parseDouble(root.value);
        }

        double leftValue = calculateRPN(root.left);
        double rightValue = calculateRPN(root.right);

        switch (root.value) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return leftValue / rightValue;
            default:
                throw new IllegalArgumentException("Invalid operator: " + root.value);
        }
    }

}

