// --== CS400 Spring 2023 File Header Information ==--
// Name: Wen Jie Lee
// Email: wlee298@wisc.edu
// Team: BK Red
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: Please give suggestions on where and how to simplify my enforceRBT code, it is a huge mess of nested if's.



import java.util.LinkedList;
import java.util.Stack;


/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * This tree contains remove and insert methods to help with the college database
 */
public class RedBlackTreeAE<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree
    protected RedBlackTreeAE<Integer> test = null;

    /**
     * This recursive helper method will enforce all the insertion cases to maintain a valid RBT by
     * checking starting from the newly inserted node, up until the root worst-case
     *
     * @param newNode the node to check for integrity.
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
        // Stop early if current/ new node node is the root.
        if (root == newNode) {
            // Always makes sure the root is black.
            root.blackHeight = 1;
            //System.out.println("recoloring node to black:" + root.data);
            return;
        }

        Node<T> parent = newNode.context[0];
        // Stop early if the parent is a root(means parent is black), or if parent is black in general
        if (parent == root || parent.blackHeight != 0) {
            return;
        }
        // Assign the parent's sibling to reduce redundant code later on, as it will be referenced repeatedly
        Node<T> parentSibling = null;
        if (parent.isRightChild()) {
            parentSibling = parent.context[0].context[1];
        } else {
            parentSibling = parent.context[0].context[2];
        }

        //Checking if insertion leads to a double red parent and child
        if (newNode.blackHeight == 0 && parent.blackHeight == 0) {
            //Get the parent's sibling color
            if (parentSibling != null && parentSibling.blackHeight == 0) {
                // If parent's sibling is red, recolor the parent, sibling and grandparent

                //System.out.println("Recoloring, current newNode:" + newNode.data);
                //System.out.println("parent: "+ parent.data);
                //System.out.println("parentSibling: "+ parentSibling.data);
                parent.blackHeight = 1;
                parentSibling.blackHeight = 1;
                parent.context[0].blackHeight = 0;
                enforceRBTreePropertiesAfterInsert(parent.context[0]);
            } else {
                //Parent's sibling is black
                //Check if parent and child is on the same side
                if (parent.isRightChild() && !newNode.isRightChild() || !parent.isRightChild() && newNode.isRightChild()) {
                    //Not on the same side, rotate to bring them on the same side. Rotate will automatically check if it should do a left or right rotation
                    //System.out.println("Not on the same side, rotating "+ newNode.data + " and "+ parent.data);
                    rotate(newNode, parent);
                    // After rotation, there will be double red on the same side. The next recursive call handles the rotation.
                    // The order is essentially flipped, so the previous child is now the new parent, and the previous parent is now the new child.
                    enforceRBTreePropertiesAfterInsert(parent);
                } else {
                    //Parent and child are on the same side and are red, swap colors between grandparent and parent, and do the rotation
                    //System.out.println("Same side, rotating "+ parent.data + " and "+ parent.context[0].data);
                    int temp = parent.context[0].blackHeight;
                    parent.context[0].blackHeight = parent.blackHeight;
                    parent.blackHeight = temp;
                    // Rotate will automatically check if it should do a left or right rotation
                    rotate(parent, parent.context[0]);
                }
            }
        }
        // Always ensures the root is black.
        root.blackHeight = 1;
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     *
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when data is already contained in the tree
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                    //System.out.println("current data filter: "+ ((CollegeDW) current.data).getFilter() + "new node data filter: "+ ((CollegeDW)data).getFilter());
                    throw new IllegalArgumentException("This RedBlackTree already contains value " + data.toString() + "\ncurrent data: " + current.data.toString());
                } else if (compare < 0) {
                    // insert in left subtree
                    if (current.context[1] == null) {
                        // empty space to insert into
                        current.context[1] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[1];
                    }
                } else {
                    // insert in right subtree
                    if (current.context[2] == null) {
                        // empty space to insert into
                        current.context[2] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[2];
                    }
                }
            }
            
        }

    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if (parent == null || child == null) {
            throw new IllegalArgumentException("The provided child and parent node references "
                    + "are not initially related");
        }
        if (parent.context[1] == child && child.context[0] == parent) {
            // Child is a left child, Right rotation
            Node<T> rightChild = child.context[2];
            parent.context[1] = rightChild;
            //Rotating for root case
            if (rightChild != null) rightChild.context[0] = parent;
            if (parent.context[0] == null) {
                child.context[0] = null;
                root = child;
            }
            else {
                //Assigning parent and child references based on their sides
                if (parent.isRightChild()) {
                    child.context[0] = parent.context[0];
                    parent.context[0].context[2] = child;
                }
                else {
                    child.context[0] = parent.context[0];
                    parent.context[0].context[1] = child;
                }
            }
            // Lastly, maintain the doubly linked nodes of parent and child
            child.context[2] = parent;
            parent.context[0] = child;
        }
        //Child is a right child, left rotation
        else if (parent.context[2] == child && child.context[0] == parent) {
            Node<T> leftChild = child.context[1];
            parent.context[2] = leftChild;
            if (leftChild != null) leftChild.context[0] = parent;
            //Rotation for root case
            if (parent.context[0] == null) {
                child.context[0] = null;
                root = child;
            }
            // Assigning parent and child references based on their sides
            else {
                if (!parent.isRightChild()) {
                    child.context[0] = parent.context[0];
                    parent.context[0].context[1] = child;
                }
                else {
                    child.context[0] = parent.context[0];
                    parent.context[0].context[2] = child;
                }
            }
            // Maintain the doubly linked nodes of parent and child
            child.context[1] = parent;
            parent.context[0] = child;
        }
        else {
            throw new IllegalArgumentException("The provided child and parent node references "
                    + "are not initially related");
        }
    }

    /**
     * Get the size of the tree (its number of nodes).
     *
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     *
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Removes the value data from the tree if the tree contains the value.
     * This method will not attempt to rebalance the tree after the removal and
     * should be updated once the tree uses Red-Black Tree insertion.
     *
     * @return true if the value was remove, false if it didn't exist
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when data is not stored in the tree
     */

    public boolean remove(T data) throws NullPointerException, IllegalArgumentException {
        //System.out.println("Removing "+ data);
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        }
        Node<T> nodeWithData = this.findNodeWithData(data);
        // throw exception if node with data does not exist
        if (nodeWithData == null) {
            throw new IllegalArgumentException("The following value is not in the tree and cannot be deleted: " + data.toString());
        }

        boolean hasRightChild = (nodeWithData.context[2] != null);
        boolean hasLeftChild = (nodeWithData.context[1] != null);
        int deletedNodeColor = -1;
        Node<T> nodeToEnforce = null;

        if (hasRightChild && hasLeftChild) {
            // has 2 children
            Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
            // replace value of node with value of successor node
            nodeWithData.data = successorNode.data;


            // remove successor node
            if (successorNode.context[2] == null) {
                // successor has no children, replace with null
                deletedNodeColor = successorNode.blackHeight;
                Node<T> NIL = new Node<>(null);
                NIL.blackHeight = 2;
                nodeToEnforce = NIL;
                this.replaceNode(successorNode, NIL);
            } else {
                // successor has a right child, replace successor with its child
                nodeToEnforce = successorNode.context[2];
                this.replaceNode(successorNode, successorNode.context[2]);
            }

        } else if (hasRightChild) {
            // only right child, replace with right child
            nodeToEnforce = nodeWithData.context[2];
            nodeToEnforce.blackHeight = nodeWithData.blackHeight;
            this.replaceNode(nodeWithData, nodeWithData.context[2]);
        } else if (hasLeftChild) {
            // only left child, replace with left child
            nodeToEnforce = nodeWithData.context[1];
            nodeToEnforce.blackHeight = nodeWithData.blackHeight;
            this.replaceNode(nodeWithData, nodeWithData.context[1]);
        } else {
            // no children, replace node with a null node
            Node<T> NIL = new Node<>(null);
            NIL.blackHeight = 2;
            nodeToEnforce = NIL;
            deletedNodeColor = nodeWithData.blackHeight;
            this.replaceNode(nodeWithData, NIL);
        }
        this.size--;

        if (deletedNodeColor == 1 ){
            //System.out.println("Enforcing during "+ data + "Enforcing node "+ nodeToEnforce.data);

            enforceRBTPropertiesAfterDelete(nodeToEnforce);;
        }
        if(nodeToEnforce.data == null){
            replaceNode(nodeToEnforce, null);
        }
        return true;
    }


        protected void enforceRBTPropertiesAfterDelete (Node <T> node){
            if(root == node){
                return;
            }
            if(node.blackHeight!=2){
                return;
            }

            Node<T> sibling = null;
            Node<T> siblingChild = null;
            Node<T> siblingChild2 = null;
            if(node.isRightChild()) sibling = node.context[0].context[1];
            else sibling = node.context[0].context[2]; // node is left child
            if(sibling == null){
                return;
            }
            if(sibling.isRightChild()) {
                siblingChild = sibling.context[1];
                siblingChild2 = sibling.context[2];
            }
            else{
                siblingChild = sibling.context[2];
                siblingChild2 = sibling.context[1];
            }

            if(sibling.blackHeight == 1){

                // Case 1: black sibling with red child
               if(siblingChild!= null && siblingChild.blackHeight == 0) {
                   //System.out.println("red child, child is "+ siblingChild.data );
                   int temp = siblingChild.blackHeight;
                   //System.out.println("siblingChild color" + siblingChild.data+siblingChild.blackHeight);
                   siblingChild.blackHeight = sibling.blackHeight;
                   sibling.blackHeight = temp;
                   rotate(siblingChild, sibling);
                   //System.out.println("rotated" + siblingChild.data + " and "+ sibling.data);
                   enforceRBTPropertiesAfterDelete(node);
               } else{
                   // Case 2: Both children are black, recolor
                   if(siblingChild2 == null ||siblingChild2.blackHeight == 1){
                       node.blackHeight--;
                       sibling.blackHeight = 0;
                       sibling.context[0].blackHeight++;
                       enforceRBTPropertiesAfterDelete(sibling.context[0]);
                   }

               }
                if(siblingChild2!= null && siblingChild2.blackHeight == 0){
                    // Case 1.5 : Balance the double black node.
                    //System.out.println("sibling parent"+ sibling.context[0].data+sibling.context[0].blackHeight);

                    sibling.blackHeight = sibling.context[0].blackHeight;
                    //System.out.println("sibling color"+ sibling.data + sibling.blackHeight);
                    sibling.context[0].blackHeight = 1;
                    siblingChild2.blackHeight = 1;
                    node.blackHeight --;
                    rotate(sibling, sibling.context[0]);
                }


            }else{
                // Case 3 : Sibling is red. Rotate sibling and parent, color swap
                sibling.blackHeight = sibling.context[0].blackHeight;
                sibling.context[0].blackHeight = 0;
                rotate(sibling, sibling.context[0]);
                enforceRBTPropertiesAfterDelete(node);
            }
            root.blackHeight = 1;
        }

        /**
         * Checks whether the tree contains the value *data*.
         *
         * @param data the data value to test for
         * @return true if *data* is in the tree, false if it is not in the tree
         */
        public boolean contains (T data){
            // null references will not be stored within this tree
            if (data == null) {
                throw new NullPointerException("This RedBlackTree cannot store null references.");
            } else {
                Node<T> nodeWithData = this.findNodeWithData(data);
                // return false if the node is null, true otherwise
                return (nodeWithData != null);
            }
        }

        /**
         * Helper method that will replace a node with a replacement node. The replacement
         * node may be null to remove the node from the tree.
         *
         * @param nodeToReplace   the node to replace
         * @param replacementNode the replacement for the node (may be null)
         */
        protected void replaceNode (Node < T > nodeToReplace, Node < T > replacementNode){
            if (nodeToReplace == null) {
                throw new NullPointerException("Cannot replace null node.");
            }
            if (nodeToReplace.context[0] == null) {
                // we are replacing the root
                if (replacementNode != null)
                    replacementNode.context[0] = null;
                this.root = replacementNode;
            } else {
                // set the parent of the replacement node
                if (replacementNode != null)
                    replacementNode.context[0] = nodeToReplace.context[0];
                // do we have to attach a new left or right child to our parent?
                if (nodeToReplace.isRightChild()) {
                    nodeToReplace.context[0].context[2] = replacementNode;
                } else {
                    nodeToReplace.context[0].context[1] = replacementNode;
                }
            }
        }

        /**
         * Helper method that will return the inorder successor of a node with two children.
         *
         * @param node the node to find the successor for
         * @return the node that is the inorder successor of node
         */
        protected Node<T> findMinOfRightSubtree (Node < T > node) {
            if (node.context[1] == null && node.context[2] == null) {
                throw new IllegalArgumentException("Node must have two children");
            }
            // take a steop to the right
            Node<T> current = node.context[2];
            while (true) {
                // then go left as often as possible to find the successor
                if (current.context[1] == null) {
                    // we found the successor
                    return current;
                } else {
                    current = current.context[1];
                }
            }
        }

        /**
         * Helper method that will return the node in the tree that contains a specific
         * value. Returns null if there is no node that contains the value.
         *
         * @return the node that contains the data, or null of no such node exists
         */
        protected Node<T> findNodeWithData (T data){
            Node<T> current = this.root;
            while (current != null) {
                int compare = data.compareTo(current.data);
                if (compare == 0) {
                    // we found our value
                    return current;
                } else if (compare < 0) {
                    // keep looking in the left subtree
                    current = current.context[1];
                } else {
                    // keep looking in the right subtree
                    current = current.context[2];
                }
            }
            // we're at a null node and did not find data, so it's not in the tree
            return null;
        }


        // Implement at least 3 boolean test methods by using the method signatures below,
        // removing the comments around them and addind your testing code to them. You can
        // use your notes from lecture for ideas on concrete examples of rotation to test for.
        // Make sure to include rotations within and at the root of a tree in your test cases.
        // Give each of the methods a meaningful header comment that describes what is being
        // tested and make sure your test hafe inline comments to help developers read through them.
        // If you are adding additional tests, then name the method similar to the ones given below.
        // Eg: public static boolean test4() {}
        // Do not change the method name or return type of the existing tests.
        // You can run your tests by commenting in the calls to the test methods

        /**
         * This method performs an inorder traversal of the tree. The string
         * representations of each data value within this tree are assembled into a
         * comma separated string within brackets (similar to many implementations
         * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
         *
         * @return string containing the ordered values of this tree (in-order traversal)
         */
        public String toInOrderString () {
            // generate a string of all values of the tree in (ordered) in-order
            // traversal sequence
            StringBuffer sb = new StringBuffer();
            sb.append("[ ");
            if (this.root != null) {
                Stack<Node<T>> nodeStack = new Stack<>();
                Node<T> current = this.root;
                while (!nodeStack.isEmpty() || current != null) {
                    if (current == null) {
                        Node<T> popped = nodeStack.pop();
                        //sb.append(popped.data.toString() + " ("+popped.blackHeight+")");
                        sb.append(popped.data.toString());
                        if (!nodeStack.isEmpty() || popped.context[2] != null) sb.append("\n ");
                        current = popped.context[2];
                    } else {
                        nodeStack.add(current);
                        current = current.context[1];
                    }
                }
            }
            sb.append(" ]");
            return sb.toString();
        }

        /**
         * This method performs a level order traversal of the tree. The string
         * representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * This method will be helpful as a helper for the debugging and testing
         * of your rotation implementation.
         *
         * @return string containing the values of this tree in level order
         */
        public String toLevelOrderString () {
            StringBuffer sb = new StringBuffer();
            sb.append("[ ");
            if (this.root != null) {
                LinkedList<Node<T>> q = new LinkedList<>();
                q.add(this.root);
                while (!q.isEmpty()) {
                    Node<T> next = q.removeFirst();
                    if (next.context[1] != null) q.add(next.context[1]);
                    if (next.context[2] != null) q.add(next.context[2]);
                    //sb.append(next.data.toString() + " ("+next.blackHeight+")");
                    sb.append(next.data.toString());
                    if (!q.isEmpty()) sb.append("\n ");
                }
            }
            sb.append(" ]");
            return sb.toString();
        }

        public String toString () {
            return "level order: " + this.toLevelOrderString() +
                    "\nin order: " + this.toInOrderString();
        }

        public String toStringBlackHeight(){
            String result ="";
            {
                StringBuffer sb = new StringBuffer();
                sb.append("[ ");
                if (this.root != null) {
                    LinkedList<Node<T>> q = new LinkedList<>();
                    q.add(this.root);
                    while (!q.isEmpty()) {
                        Node<T> next = q.removeFirst();
                        if (next.context[1] != null) q.add(next.context[1]);
                        if (next.context[2] != null) q.add(next.context[2]);
                        sb.append(next.data.toString() + " ("+next.blackHeight+")");
                        //sb.append(next.data.toString());
                        if (!q.isEmpty()) sb.append(", ");
                    }
                }
                sb.append(" ]");
                result += "level order: " + sb.toString();
            }
            {
                // generate a string of all values of the tree in (ordered) in-order
                // traversal sequence
                StringBuffer sb = new StringBuffer();
                sb.append("[ ");
                if (this.root != null) {
                    Stack<Node<T>> nodeStack = new Stack<>();
                    Node<T> current = this.root;
                    while (!nodeStack.isEmpty() || current != null) {
                        if (current == null) {
                            Node<T> popped = nodeStack.pop();
                            sb.append(popped.data.toString() + " ("+popped.blackHeight+")");
                            //sb.append(popped.data.toString());
                            if (!nodeStack.isEmpty() || popped.context[2] != null) sb.append(", ");
                            current = popped.context[2];
                        } else {
                            nodeStack.add(current);
                            current = current.context[1];
                        }
                    }
                }
                sb.append(" ]");
                result+="\nin order: "+ sb.toString();
            }
            return result;

        }

        /**
         * This class represents a node holding a single value within a binary tree.
         */
        protected static class Node<T> {
            public T data;
            // The context array stores the context of the node in the tree:
            // - context[0] is the parent reference of the node,
            // - context[1] is the left child reference of the node,
            // - context[2] is the right child reference of the node.
            // The @SupressWarning("unchecked") annotation is used to supress an unchecked
            // cast warning. Java only allows us to instantiate arrays without generic
            // type parameters, so we use this cast here to avoid future casts of the
            // node type's data field.
            @SuppressWarnings("unchecked")
            public Node<T>[] context = (Node<T>[]) new Node[3];
            public int blackHeight;


            public Node(T data) {
                this.data = data;
                blackHeight = 0;
            }

            /**
             * @return true when this node has a parent and is the right child of
             * that parent, otherwise return false
             */
            public boolean isRightChild() {
                return context[0] != null && context[0].context[2] == this;
            }

            public boolean isRed() {
                return blackHeight == 0;
            }

            public boolean isBlack() {
                return blackHeight > 0;
            }
        }
    }

