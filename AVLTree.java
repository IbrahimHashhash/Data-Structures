class AVLTreeNode {
    int element; //store data
    AVLTreeNode left; // left child
    AVLTreeNode right; //right child
    int height; //Height
    public AVLTreeNode(int element){
        this(element, null, null);
    }
    public AVLTreeNode(int element, AVLTreeNode left, AVLTreeNode right)
    {
        this.element=element;
        this.left=left;
        this.right=right;
        this.height=0;
    }
}



public class AVLTree {
    private AVLTreeNode root;
    public AVLTree(){
        root=null;
    }
    private int height( AVLTreeNode e ){
        if( e == null )
            return -1;
        return e.height;
    }
    private AVLTreeNode rotateWithLeftChild(AVLTreeNode k2){
        AVLTreeNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left),height( k2.right ))+ 1;
        k1.height = Math.max(height(k1.left),k2.height );
        return k1;
    }
    private AVLTreeNode rotateWithRightChild(AVLTreeNode k1) {
        AVLTreeNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }
    private AVLTreeNode DoubleWithLeftChild(AVLTreeNode k3){
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }
    private AVLTreeNode DoubleWithRightChild(AVLTreeNode k3){
        k3.left = rotateWithLeftChild( k3.left );
        return rotateWithRightChild( k3 );
    }


}
