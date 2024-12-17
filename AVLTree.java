public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    public AVLTree(){
        super();
    }

    // Inserts a new node into the AVL Tree and ensures
    // AVL properties are held
    public V insert(K key, V value){
        if(root == null){
            this.root = new TreeNode<>(key, value);
            size++;
            return null;
        } 
        V answer = find(key);
        this.root = insertHelper(this.root, key, value);
        root.updateHeight();
        return answer;
    }

    // The insert method's recursive helper that handles inserting, checking
    // for imbalances, and rotatations if needed.
    private TreeNode<K,V> insertHelper(TreeNode<K, V> node, K key, V value){
        if (node == null) {
            size++;
            return new TreeNode<>(key, value);
        }

        if(key.compareTo(node.key) == 0) {
            node.value = value;
            return node;
        } else if (key.compareTo(node.key) < 0) {
            node.left = insertHelper(node.left, key, value);
        } else {
            node.right = insertHelper(node.right, key, value);
        }
        
        node.updateHeight();
        int balance = balanceFactor(node);

        // Left heavy case
        if(balance < -1) {
            if(balanceFactor(node.left) <= 0) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }

        // Right heavy case
        if(balance > 1) {
            if(balanceFactor(node.right) >= 0) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }

        return node;
        
    }

    // Method that rotates given node to the right
    private TreeNode<K,V> rotateRight(TreeNode<K, V> node) {
        TreeNode<K, V> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        node.updateHeight();
        leftChild.updateHeight();

        return leftChild;

    } 

    // Method that rotates given node to the left
    private TreeNode<K,V> rotateLeft(TreeNode<K, V> node) {
        TreeNode<K, V> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        node.updateHeight();
        rightChild.updateHeight();

        return rightChild;
    }

    // Calculates and returns an int representing the
    // balance which helps with finding imbalances
    private int balanceFactor(TreeNode<K, V> node) {
        if(node == null) {
            return 0;
        }
        int rightHeight = (node.right == null) ? -1 : node.right.height;
        int leftHeight = (node.left == null) ? -1 : node.left.height;
        return rightHeight - leftHeight;
    }

   
}

