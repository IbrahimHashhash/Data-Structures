class BSTNode {
    private int element;
    private BSTNode left,right;

    public BSTNode(int element){
        this(element,null,null);
    }
    public BSTNode(int element, BSTNode left, BSTNode right){
        this.element=element;
        this.left=left;
        this.right=right;
    }

    public int getElement() {
        return element;
    }

    public BSTNode getLeft() {
        return left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}
public class BinaryTree {
    private BSTNode root;

    public BinaryTree(){
        this.root=null;
    }

    public void insert(int element){
        root = insert(element,root);
    }
    //Insert element function
    private BSTNode insert (int element, BSTNode current){
        if (current==null)
            current= new BSTNode(element); //create one node tree
        else
        {
            if (element<current.getElement())
                current.setLeft(insert(element,current.getLeft()));
            else
                current.setRight(insert(element,current.getRight()));
        }
        return current;
    }
    public boolean contains(int e){
        return contains(e,root);
    }
    public BSTNode find(int e){
        return find(e,root);
    }



    private boolean contains (int e,BSTNode current) {
        if (current==null)
            return false; // Not found, empty tree.
        else if (e<current.getElement()) // if smaller than root.
            return contains (e,current.getLeft()); // Search left subtree
        else if (e>current.getElement()) // if larger than root.
            return contains (e,current.getRight()); // Search right subtree
        return true; // found .
    }

    private BSTNode find(int element, BSTNode current) {
        if (current == null)
            return null;
        if (element < current.getElement())
            return find(element, current.getLeft());
        else if (element > current.getElement())
            return find(element, current.getRight());
        else
            return current;
    }
    private BSTNode findMin (BSTNode current){
        if (current==null)
            return null;
        else if (current.getLeft()==null)
            return current;
        else
            return findMin(current.getLeft()); //keep going to the left
    }
    private BSTNode findMax (BSTNode current){
        if (current==null)
            return null;
        else if (current.getRight()==null)
            return current;
        else
            return findMax(current.getRight()); //keep going to the right
    }

    public void remove(int element){
        root = remove(element,root);
    }

    private BSTNode remove (int e,BSTNode current){
        if (current==null) return null; // Item not found,Empty tree
        if (e<current.getElement())
            current.setRight(remove(e,current.getLeft()));
        else if (e>current.getElement())
            current.setRight(remove(e,current.getRight()));
        else // found element to be deleted
            if (current.getLeft()!=null && current.getRight()!=null)// two children
            {
                /*Replace with smallest in right subtree */
                current.setElement(findMin(current.getRight()).getElement());
                current.setRight(remove(current.getElement(),current.getRight()));
            }
            else// one or zero child
                current= (current.getLeft()!=null)?current.getLeft():current.getRight();
        return current;
    }

    public void preOrder(){
        // root -> left -> right
        preOrder(root);

    }

    private void preOrder(BSTNode root){
        if(root != null) {
            System.out.print(root.getElement() + " ");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }

    }
    public void inOrder()
    {
        inOrder(root);
    }

    private void inOrder(BSTNode r)
    {
        if (r != null)
        {
            inOrder(r.getLeft());
            System.out.print(r.getElement() + " ");
            inOrder(r.getRight());
        }
    }
    public void postOrder(){
        postOrder(root);
    }
    private void postOrder(BSTNode root){
        if(root!=null){
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getElement() + " ");
        }
    }
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BSTNode pos) {
        if (pos == null) {
            return 0;
        } else {
            int heightLeft = getHeight(pos.getLeft());
            int heightRight = getHeight(pos.getRight());
            return Math.max(heightLeft, heightRight) + 1;
        }
    }
    public boolean isEmpty(){
        return root==null;
    }


}
