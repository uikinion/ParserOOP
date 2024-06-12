class TreePrinter {
    public static void printRPN(RPNNode root) {
        if (root == null) {
            return;
        }
        printRPN(root.left);
        printRPN(root.right);
        System.out.print(root.value + " ");
    }

}