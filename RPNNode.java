class RPNNode {
    String value;
    RPNNode left;
    RPNNode right;

    public RPNNode(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}