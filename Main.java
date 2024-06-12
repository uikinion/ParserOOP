public class Main {
    public static void main(String[] args) {
        String expression = "(2 + 9) * 88 + 3/(5-9)";
        RPNNode root = Parser.convertToRPN(expression);
        TreePrinter.printRPN(root);
        double result = Parser.calculateRPN(root);
        System.out.println("\nResult: " + result);
    }
}


//
