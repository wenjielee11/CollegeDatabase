// --== CS400 Spring 2023 File Header Information ==--
// Name: Kiet Pham
// Email: kvpham@wisc.edu
// Team: BK
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: The test are not written in chronological order, therefore, it can look very weird. 
// 					Nevertheless, it is still correct in theory.

import java.util.LinkedList;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;
        public int blackHeight; // 0 = red, 1 = black, 2 = double black
        // The context array stores the context of the node in the tree:
        // - context[0] is the parent reference of the node,
        // - context[1] is the left child reference of the node,
        // - context[2] is the right child reference of the node.
        // The @SupressWarning("unchecked") annotation is used to supress an unchecked
        // cast warning. Java only allows us to instantiate arrays without generic
        // type parameters, so we use this cast here to avoid future casts of the
        // node type's data field.
        @SuppressWarnings("unchecked")
        public Node<T>[] context = (Node<T>[])new Node[3];
        public Node(T data) { this.data = data; this.blackHeight = 0; }
        
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return context[0] != null && context[0].context[2] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is already contained in the tree
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (this.root == null) {
            // add first node to an empty tree
            root = newNode; size++;
            enforceRBTreePropertiesAfterInsert(newNode); 
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                    throw new IllegalArgumentException("This RedBlackTree already contains value " + data.toString());
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
     * Resolves any red-black tree property violations that are introduced after the insertion of a new node.
     * @param newNode - the new node of insertion
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
    	// If the current node is the root, turn the root to black and then do nothing
    	if (newNode.data == this.root.data) {
    		newNode.blackHeight = 1; 
    		return;
    	}
    	
    	Node<T> newNodeParent = newNode.context[0]; 
    	
    	// If the current node's parent is black, then there is no violation
    	if (newNodeParent.blackHeight == 1) {
    		return;
    	}
    	 
    	Node<T> newNodeGrandparent = newNodeParent.context[0]; 
    	Node<T> newNodeParentSibling = (newNodeGrandparent.context[1] == newNodeParent ? 
    			newNodeGrandparent.context[2] : newNodeGrandparent.context[1]);
    			// If the parent is the left child then the parent's sibling is the right child of the
    			// grandparent and vice versa. 
    	
    	// Case 3: If the parent's sibling is red, then change the color of parent, parent's parent, and 
    	// parent's siblings. After that, resolve any violations from the parent's parent. 
    	if (newNodeParentSibling != null && newNodeParentSibling.blackHeight == 0) {
    		newNodeParent.blackHeight = 1; 
    		newNodeParentSibling.blackHeight = 1; 
    		newNodeGrandparent.blackHeight = 0; 
    		enforceRBTreePropertiesAfterInsert(newNodeGrandparent); 
    		return;
    	}
    	
    	int side = 0; // Get the side of the new node to its parent. 
    	int sideParent = 0; // Get the side of the new node's parent to its grandparent. 
    	// The side is 1 = left, 2 = right
    	side = (newNodeParent.context[1] == newNode ? 1 : 2); 
    	sideParent = (newNodeGrandparent.context[1] == newNodeParent ? 1 : 2); 
    	
    	// Case 2: If the parent's sibling is black but the new node and the parent are on different sides, 
    	// rotate so that they are on the same side first, and then rotate and swap color 
    	if (side != sideParent) {
    		rotate(newNode, newNodeParent);
    		rotate(newNode, newNodeGrandparent); 
    		// Swap color
        	int temp = newNode.blackHeight;
        	newNode.blackHeight = newNodeGrandparent.blackHeight;
        	newNodeGrandparent.blackHeight = temp; 
    	}
    	
    	// Case 1: If the parent's sibling is black and the new node and the parent are on same side, rotate
    	// the parent and the grandparent of the node and swap their color
    	else {
        	rotate(newNodeParent, newNodeGrandparent); 
        	// Swap color
        	int temp = newNodeParent.blackHeight;
        	newNodeParent.blackHeight = newNodeGrandparent.blackHeight;
        	newNodeGrandparent.blackHeight = temp; 
    	}

    	
    	// Assuring that the root is always black
    	root.blackHeight = 1; 
    	
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
    	if (parent == null || child == null) {
    		throw new IllegalArgumentException("The provided child and parent node references "
    				+ "are not initially related (pre-rotation)"); 
    	}
    	if (parent.context[1] == child && child.context[0] == parent) { 
    		// Child is a left child => Right rotation
    		Node<T> rightChild = child.context[2]; 
    		parent.context[1] = rightChild; 
    		if (rightChild != null) rightChild.context[0] = parent; 
    		if (parent.context[0] == null) {
    			child.context[0] = null; 
    			root = child; 
    		}
    		else {
    			if (parent.isRightChild()) {
    				child.context[0] = parent.context[0]; 
    				parent.context[0].context[2] = child; 
    			}
    			else {
    				child.context[0] = parent.context[0]; 
    				parent.context[0].context[1] = child; 
    			}
    		}
    		child.context[2] = parent; 
    		parent.context[0] = child; 
    	}
    	else if (parent.context[2] == child && child.context[0] == parent) {
    		// Child is a right child => Left rotation
    		Node<T> leftChild = child.context[1]; 
    		parent.context[2] = leftChild; 
    		if (leftChild != null) leftChild.context[0] = parent; 
    		if (parent.context[0] == null) {
    			child.context[0] = null; 
    			root = child; 
    		}
    		else {
    			if (parent.isRightChild()) {
    				child.context[0] = parent.context[0]; 
    				parent.context[0].context[2] = child; 
    			}
    			else {
    				child.context[0] = parent.context[0]; 
    				parent.context[0].context[1] = child; 
    			}
    		}
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
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Removes the value data from the tree if the tree contains the value.
     * This method will not attempt to rebalance the tree after the removal and
     * should be updated once the tree uses Red-Black Tree insertion.
     * @return true if the value was remove, false if it didn't exist
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is not stored in the tree
     */
    public boolean remove(T data) throws NullPointerException, IllegalArgumentException {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // throw exception if node with data does not exist
            if (nodeWithData == null) {
                throw new IllegalArgumentException("The following value is not in the tree and cannot be deleted: " + data.toString());
            }  
            boolean hasRightChild = (nodeWithData.context[2] != null);
            boolean hasLeftChild = (nodeWithData.context[1] != null);
            if (hasRightChild && hasLeftChild) {
                // has 2 children
                Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
                // replace value of node with value of successor node
                nodeWithData.data = successorNode.data;
                // remove successor node
                if (successorNode.context[2] == null) {
                    // successor has no children, replace with null
                    this.replaceNode(successorNode, null);
                } else {
                    // successor has a right child, replace successor with its child
                    this.replaceNode(successorNode, successorNode.context[2]);
                }
            } else if (hasRightChild) {
                // only right child, replace with right child
                this.replaceNode(nodeWithData, nodeWithData.context[2]);
            } else if (hasLeftChild) {
                // only left child, replace with left child
                this.replaceNode(nodeWithData, nodeWithData.context[1]);
            } else {
                // no children, replace node with a null node
                this.replaceNode(nodeWithData, null);
            }
            this.size--;
            return true;
        } 
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(T data) {
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
     * @param nodeToReplace the node to replace
     * @param replacementNode the replacement for the node (may be null)
     */
    protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode) {
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
     * @param node the node to find the successor for
     * @return the node that is the inorder successor of node
     */
    protected Node<T> findMinOfRightSubtree(Node<T> node) {
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
     * @return the node that contains the data, or null of no such node exists
     */
    protected Node<T> findNodeWithData(T data) {
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

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
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
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.context[2] != null) sb.append(", ");
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
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.context[1] != null) q.add(next.context[1]);
                if(next.context[2] != null) q.add(next.context[2]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

// =================================== TESTERS ================================================================
    
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
    
    protected RedBlackTree<Integer> rbtIntTest = null; 
    protected RedBlackTree<String> rbtStringTest = null; 
    
    @BeforeEach
    public void createInstance() {
    	rbtIntTest = new RedBlackTree<Integer>(); 
    	rbtStringTest = new RedBlackTree<String>(); 
    }
    
    /**
     * Testing method for edge cases
     * This includes insert to an empty tree and add two red node as a child of a black node.
     * There should be no rotation and color changing (except change the root from red to black).
     */
    @Test 
    public void test0() {
    	rbtIntTest.insert(0); 
    	assertEquals(1, rbtIntTest.root.blackHeight, "The root is not black");
    	rbtIntTest.insert(-1); 
    	assertEquals(0, rbtIntTest.root.context[1].blackHeight, "The newly added left node change color when "
    			+ "it is not supposed to"); 
    	rbtIntTest.insert(1); 
    	assertEquals(0, rbtIntTest.root.context[2].blackHeight, "The newly added right node change color when "
    			+ "it is not supposed to");
    }

    /**
     * Testing method for case 1
     * The case where the sibling is black or null and the new node and the parent are on the same side.
     * Comparison by specific output on basic tree cases.
     */
    @Test
    public void test1() {
        // Case 1: Root (black) - Right (red). Left is null. Insert Right
    	// Should return a Left (red) - Root (black) - Right (red)
    	createInstance(); 
    	rbtIntTest.root = new Node<Integer>(100);  rbtIntTest.root.blackHeight = 1; 
    	rbtIntTest.root.context[2] = new Node<Integer>(200); 
    	rbtIntTest.root.context[2].context[0] = rbtIntTest.root; 
    	rbtIntTest.insert(300); 
    	assertEquals(200, rbtIntTest.root.data, "The tree does not solve violations"); 
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree rotates but does not swap color");
    	Node<Integer> leftChild = rbtIntTest.root.context[1]; 
    	assertEquals(100, leftChild.data, "The tree rotates it wrongly");
    	assertEquals(0, leftChild.blackHeight, "The tree rotates but does not swap color"); 
    	Node<Integer> rightChild = rbtIntTest.root.context[2]; 
    	assertEquals(300, rightChild.data, "The tree rotates it wrongly");
    	assertEquals(0, rightChild.blackHeight, "The tree rotates and swap color when it is not supposed to"); 
    	
        // Case 2: Root (black) - Left (red). Right is null. Insert Left
    	// Should return a Left (red) - Root (black) - Right (red)
    	createInstance(); 
    	rbtIntTest.root = new Node<Integer>(300);  rbtIntTest.root.blackHeight = 1; 
    	rbtIntTest.root.context[1] = new Node<Integer>(200); 
    	rbtIntTest.root.context[1].context[0] = rbtIntTest.root; 
    	rbtIntTest.insert(100); 
    	assertEquals(200, rbtIntTest.root.data, "The tree does not solve violations"); 
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree rotates but does not swap color");
    	leftChild = rbtIntTest.root.context[1]; 
    	assertEquals(100, leftChild.data, "The tree rotates it wrongly");
    	assertEquals(0, leftChild.blackHeight, "The tree rotates but does not swap color"); 
    	rightChild = rbtIntTest.root.context[2]; 
    	assertEquals(300, rightChild.data, "The tree rotates it wrongly");
    	assertEquals(0, rightChild.blackHeight, "The tree rotates and swap color when it is not supposed to"); 
    	
    	// Case 3: 
    }

    /**
     * Testing method for case 2
     * The case where the sibling is black or null and the new node and the parent are on the different side.
     * Comparison by specific output on basic tree cases.
     */
    @Test
    public void test2() {
        // Case 1: Root (black) - Right (red). Left is null. Insert value between root and right
    	// Should return a Left (red) - Root (black) - Right (red)
    	createInstance(); 
    	rbtIntTest.root = new Node<Integer>(100);  rbtIntTest.root.blackHeight = 1; 
    	rbtIntTest.root.context[2] = new Node<Integer>(300); 
    	rbtIntTest.root.context[2].context[0] = rbtIntTest.root; 
    	rbtIntTest.insert(200); 
    	assertEquals(200, rbtIntTest.root.data, "The tree does not solve violations"); 
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree rotates but does not swap color");
    	Node<Integer> leftChild = rbtIntTest.root.context[1]; 
    	assertEquals(100, leftChild.data, "The tree rotates it wrongly");
    	assertEquals(0, leftChild.blackHeight, "The tree rotates but does not swap color"); 
    	Node<Integer> rightChild = rbtIntTest.root.context[2]; 
    	assertEquals(300, rightChild.data, "The tree rotates it wrongly");
    	assertEquals(0, rightChild.blackHeight, "The tree rotates and swap color when it is not supposed to"); 
    	
        // Case 2: Root (black) - Left (red). Right is null. Insert value between root and left
    	// Should return a Left (red) - Root (black) - Right (red)
    	createInstance(); 
    	rbtIntTest.root = new Node<Integer>(300);  rbtIntTest.root.blackHeight = 1; 
    	rbtIntTest.root.context[1] = new Node<Integer>(100); 
    	rbtIntTest.root.context[1].context[0] = rbtIntTest.root; 
    	rbtIntTest.insert(200); 
    	assertEquals(200, rbtIntTest.root.data, "The tree does not solve violations"); 
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree rotates but does not swap color");
    	leftChild = rbtIntTest.root.context[1]; 
    	assertEquals(100, leftChild.data, "The tree rotates it wrongly");
    	assertEquals(0, leftChild.blackHeight, "The tree rotates but does not swap color"); 
    	rightChild = rbtIntTest.root.context[2]; 
    	assertEquals(300, rightChild.data, "The tree rotates it wrongly");
    	assertEquals(0, rightChild.blackHeight, "The tree rotates and swap color when it is not supposed to"); 
    }
    
    /**
     * Testing method for case 3
     * The case where the sibling is red.
     * Comparison by specific output on basic tree cases.
     */
    @Test
    public void test3() {
        // Case 1: Left (Red) - Root (Black) - Right (Red). Insert all positions. 
    	createInstance(); 
    	rbtIntTest.insert(0); 
    	rbtIntTest.insert(-100); 
    	rbtIntTest.insert(100);
    	// This part is correct according to test0
    	rbtIntTest.insert(-200); 
    	Node<Integer> toCheck = rbtIntTest.findNodeWithData(-200);
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree does not make the root goes to 0"); 
    	assertEquals(1, rbtIntTest.root.context[1].blackHeight, "The tree does not swap color"); 
    	assertEquals(1, rbtIntTest.root.context[2].blackHeight, "The tree does not swap color"); 
    	assertEquals(0, toCheck.blackHeight, "The newly add node change color when not needed"); 
    	
    	createInstance(); 
    	rbtIntTest.insert(0); 
    	rbtIntTest.insert(-100); 
    	rbtIntTest.insert(100);
    	// This part is correct according to test0
    	rbtIntTest.insert(-50); 
    	toCheck = rbtIntTest.findNodeWithData(-50);
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree does not make the root goes to 0"); 
    	assertEquals(1, rbtIntTest.root.context[1].blackHeight, "The tree does not swap color"); 
    	assertEquals(1, rbtIntTest.root.context[2].blackHeight, "The tree does not swap color"); 
    	assertEquals(0, toCheck.blackHeight, "The newly add node change color when not needed"); 
    	
    	createInstance(); 
    	rbtIntTest.insert(0); 
    	rbtIntTest.insert(-100); 
    	rbtIntTest.insert(100);
    	// This part is correct according to test0
    	rbtIntTest.insert(50); 
    	toCheck = rbtIntTest.findNodeWithData(50);
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree does not make the root goes to 0"); 
    	assertEquals(1, rbtIntTest.root.context[1].blackHeight, "The tree does not swap color"); 
    	assertEquals(1, rbtIntTest.root.context[2].blackHeight, "The tree does not swap color"); 
    	assertEquals(0, toCheck.blackHeight, "The newly add node change color when not needed");
    	
    	createInstance(); 
    	rbtIntTest.insert(0); 
    	rbtIntTest.insert(-100); 
    	rbtIntTest.insert(100);
    	// This part is correct according to test0
    	rbtIntTest.insert(200); 
    	toCheck = rbtIntTest.findNodeWithData(200);
    	assertEquals(1, rbtIntTest.root.blackHeight, "The tree does not make the root goes to 0"); 
    	assertEquals(1, rbtIntTest.root.context[1].blackHeight, "The tree does not swap color"); 
    	assertEquals(1, rbtIntTest.root.context[2].blackHeight, "The tree does not swap color"); 
    	assertEquals(0, toCheck.blackHeight, "The newly add node change color when not needed"); 
    	
    	// Case 2: Left (Red) - Parent (Black) - Right(Red), Parent is not root, Grandparent is black. 
    	// The color of grandparent should still be black, while color of left, parent, right is inverted. 
    	rbtIntTest.insert(50); 
    	rbtIntTest.insert(75); 
    	assertEquals(0, rbtIntTest.findNodeWithData(75).blackHeight, "The added node change color when it is not"
    			+ "supposed to");
    	assertEquals(1, rbtIntTest.findNodeWithData(50).blackHeight, "The left node does not change color");
    	assertEquals(1, rbtIntTest.findNodeWithData(200).blackHeight, "The right node does not change color");
    	assertEquals(0, rbtIntTest.findNodeWithData(100).blackHeight, "The parent does not change color"); 
    	
    	// Case 3: Same as case 2 but grandparent is red. 
    	// Since this can turn into rotation case, the new root is 50. 
    	rbtIntTest.insert(20); 
    	rbtIntTest.insert(30); 
    	assertEquals(50, rbtIntTest.root.data, "There is no rotation when violation from case 3 is created"); 
    	assertEquals(0, rbtIntTest.findNodeWithData(30).blackHeight, "This node change color when it's not supposed to");
    	assertEquals(0, rbtIntTest.root.context[1].data);
    	assertEquals(100, rbtIntTest.root.context[2].data); 
    	assertEquals(20, rbtIntTest.findNodeWithData(30).context[0].data, "The newly added node rotate to the wrong parent");
    }
    
    /**
     * Testing method for adding from 1 to 8 to the red black tree
     * Comparison through specific output
     */
    @Test
    public void test4() {
    	// Gradually add from 1 to 8 to the Red Black Tree
    	
    	// Test when first added the root if the root is black and there is no node connecting to it
    	rbtIntTest.insert(1); 
    	assertEquals(1, rbtIntTest.root.data);
    	assertEquals(1, rbtIntTest.root.blackHeight);
    	assertEquals(null, rbtIntTest.root.context[0]); 
    	assertEquals(null, rbtIntTest.root.context[1]); 
    	assertEquals(null, rbtIntTest.root.context[2]); 
    	
    	// Test when add 2 if the second node is red and be the right child of 1
    	rbtIntTest.insert(2); 
    	assertEquals(1, rbtIntTest.root.data);
    	assertEquals(1, rbtIntTest.root.blackHeight);
    	assertEquals(0, rbtIntTest.root.context[2].blackHeight);
    	assertEquals(null, rbtIntTest.root.context[0]); 
    	assertEquals(null, rbtIntTest.root.context[1]); 
    	
    	// Test when add 3 if the root becomes 2 and the two children become red
    	rbtIntTest.insert(3); 
    	assertEquals(2, rbtIntTest.root.data);
    	assertEquals(1, rbtIntTest.root.blackHeight);
    	assertEquals(1, rbtIntTest.root.context[1].data) ;
    	assertEquals(0, rbtIntTest.root.context[1].blackHeight);
    	assertEquals(3, rbtIntTest.root.context[2].data);
    	assertEquals(0, rbtIntTest.root.context[2].blackHeight); 
    	
    	// Test when add 4 if the red black tree still satisfy the BST properties. 
    	// We do not need to test for this case because we already test it in test1()
    	rbtIntTest.insert(4); 
    	assertEquals("[ 1, 2, 3, 4 ]", rbtIntTest.toInOrderString());
    	
    	// When insert 5, the rotation is the same as test1() but the parent is not the root, 
    	// so we test if the parents-child relationship around 5 is correct.
    	rbtIntTest.insert(5); 
    	assertEquals(4, rbtIntTest.root.context[2].data);
    	assertEquals(1, rbtIntTest.root.context[2].blackHeight);
    	assertEquals(3, rbtIntTest.root.context[2].context[1].data) ;
    	assertEquals(5, rbtIntTest.root.context[2].context[2].data) ;
    	
    	rbtIntTest.insert(6); 
    	assertEquals("[ 1, 2, 3, 4, 5, 6 ]", rbtIntTest.toInOrderString());
    	
    	// When insert 8, test if the color and the data is correct on the tree.
    	rbtIntTest.insert(7); 
    	rbtIntTest.insert(8); 
    	assertEquals("[ 1, 2, 3, 4, 5, 6, 7, 8 ]", rbtIntTest.toInOrderString()); 
    	assertEquals(4, rbtIntTest.root.data) ;
    	assertEquals(2, rbtIntTest.root.context[1].data) ;
    	assertEquals(0, rbtIntTest.root.context[1].blackHeight);
    	assertEquals(6, rbtIntTest.root.context[2].data) ;
    	assertEquals(0, rbtIntTest.root.context[2].blackHeight);
    	assertEquals(1, rbtIntTest.root.context[1].context[1].data) ;
    	assertEquals(1, rbtIntTest.root.context[1].context[1].blackHeight);
    	assertEquals(3, rbtIntTest.root.context[1].context[2].data) ;
    	assertEquals(1, rbtIntTest.root.context[1].context[2].blackHeight);
    	assertEquals(5, rbtIntTest.root.context[2].context[1].data) ;
    	assertEquals(1, rbtIntTest.root.context[2].context[1].blackHeight);
    	assertEquals(7, rbtIntTest.root.context[2].context[2].data) ;
    	assertEquals(1, rbtIntTest.root.context[2].context[2].blackHeight);
    	assertEquals(8, rbtIntTest.root.context[2].context[2].context[2].data) ;
    	assertEquals(0, rbtIntTest.root.context[2].context[2].context[2].blackHeight);
    	
    }
    
    public static void main(String[] args) {

    }
}
