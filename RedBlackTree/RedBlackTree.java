// --== CS400 RedBlackTree File Header ==--
// Name: <Changjae Han>
// CSL Username: <changjae>
// Email: <chan82@wisc.edu>
// Lecture #: <001 @11:00am>
// Notes to Grader: <any optional extra notes to your grader>


import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree 
that
 * we will turn into a red black tree by modifying the insert 
functionality.
 * In this activity, we will start with implementing rotations for the 
binary
 * search tree insert algorithm. You can use this class' insert method to 
build
 * a regular binary search tree, and its toString method to display a 
level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements 
SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary 
tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public Node(T data) { this.data = data; }
        /**
         * @return true when this node has a parent and is the left child 
of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        
        //newly created to check
        public boolean isRightChild() {
            return parent != null && parent.rightChild == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when 
empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the 
input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the 
tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is 
null
     * @throws IllegalArgumentException when the newNode and subtree 
contain
     *      equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, 
IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; size++; return true; } // add 
first node to an empty tree
        else{
            boolean returnValue = insertHelper(newNode,root); // 
recursively insert into subtree
            if (returnValue) size++;
            else throw new IllegalArgumentException(
                    "This RedBlackTree already contains that value.");
            return returnValue;
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference 
in the
     * position that the newNode should be inserted, and then extend this 
tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which 
the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

            // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add 
here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                return true;
                // otherwise continue recursive search for location to 
insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if(subtree.rightChild == null) { // right subtree empty, add 
here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                return true;
                // otherwise continue recursive search for location to 
insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this 
tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left 
rotation.
     * When the provided nodes are not related in one of these ways, this 
method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent 
position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child 
position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that 
way
     */
    private void rotate(Node<T> child, Node<T> parent) throws 
IllegalArgumentException {
            	    	
    	//Throw exception when either parent or child is null 
    	if (parent == null || child == null) {
    		throw new IllegalArgumentException("Either child or parent 
is(are) null: IllegalArgumentException");
    	}
    	
    	//left rotation when child is bigger than parent
    	if (child.data.compareTo(parent.data) == 1 && child == 
parent.rightChild) {
    		
    		System.out.println("Left rotation");
    		
    		//Define grandparent which is parent of parent in case 
rotate is implemented in the subtree
    		Node<T> GrandP = parent.parent;    	
 
    		//no grandparent => child becomes root
    		if (GrandP == null) {
    		    this.root = child;
    		}
    		//yes grandparent => child becomes child of grandparent 
with 2 cases
    		else if (GrandP.leftChild == parent) {
    			  GrandP.leftChild = child;
    		}
    		else if (GrandP.rightChild == parent) {
    			  GrandP.rightChild = child;
    		}
    		//Exception if no such condition
    		else {
    		    throw new IllegalArgumentException("Wrong with 
grandparent");
    		}
    		
    		//Assigns
    		child.parent = GrandP;
    		parent.rightChild = child.leftChild;
    		if (child.leftChild != null) {
    		    child.leftChild.parent = parent;
    		}
    		child.leftChild = parent;
    		parent.parent = child;
 	
    	}
    	
    	//when child is smaller than parent
    	else if(child.data.compareTo(parent.data) == -1 && child == 
parent.leftChild) {
    		
    		System.out.println("Right rotation");
    		
    		//Define grandparent which is parent of parent in case 
rotate is implemented in the subtree
    		Node<T> GrandP = parent.parent;
    	
    		//no grandparent => child becomes root
    		if (GrandP == null) {
    		    this.root = child;
    		} 
    		//yes grandparent => child becomes child of grandparent 
with 2 cases
    		else if (GrandP.leftChild == parent) {
    			  GrandP.leftChild = child;
    		}
    		else if (GrandP.rightChild == parent) {
    			  GrandP.rightChild = child;
    		}
    		//Exception if no such condition
    		else {
    		    throw new IllegalArgumentException("Wrong with 
grandparent");
    		}
    		
    		//Assigns
    		child.parent = GrandP;
    		parent.leftChild = child.rightChild;
    		if (child.rightChild != null) {
    		    child.rightChild.parent = parent;
    		}
    		child.rightChild = parent;
    		parent.parent = child;
    		
    	}
    	//IllegalArgumentException
    	else {
    		throw new IllegalArgumentException("Incomparable or 
illegal relationship: IllegalArgumentException");
    	}
    	
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the 
tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * @return iterator object that traverses the tree in in-order 
sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator 
interface
        // we create a new on-off object of this class everytime the 
iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the 
traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal 
sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements 
in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and 
current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in 
un8til we hit a null
                // leaf (current is null), pushing all the nodes we fund 
on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished 
the traversal yet;
                // take the next element from the stack and return it, 
then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our 
traversal
                    throw new NoSuchElementException("There are no more 
elements in the tree");
                }

            }

            /**
             * Returns a boolean that indicates if the iterator has more 
elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements 
/ steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current 
reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || 
stack.isEmpty()) );
            }

        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled 
into a
     * comma separated string within brackets (similar to many 
implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, 
etc).
     * Note that this RedBlackTree class implementation of toString 
generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order 
traversal)
     */
    public String toInOrderString() {
        // use the inorder Iterator that we get by calling the iterator 
method above
        // to generate a string of all values of the tree in (ordered) 
in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level
     * order traversal. The toString of the RedBlackTree class below
     * produces an inorder traversal of the nodes / values of the tree.
     * This method will be helpful as a helper for the debugging and 
testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        LinkedList<Node<T>> q = new LinkedList<>();
        q.add(this.root);
        while(!q.isEmpty()) {
            Node<T> next = q.removeFirst();
            if(next.leftChild != null) q.add(next.leftChild);
            if(next.rightChild != null) q.add(next.rightChild);
            output += next.data.toString();
            if(!q.isEmpty()) output += ", ";
        }
        return output + " ]";
    }

    @Override
    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "/nin order: " + this.toInOrderString();
    }

    
    // Implement at least 3 boolean test methods by using the method 
signatures below,
    // removing the comments around them and addind your testing code to 
them. You can
    // use your notes from lecture for ideas on concrete examples of 
rotation to test for.
    // Make sure to include rotations within and at the root of a tree in 
your test cases.
    // If you are adding additional tests, then name the method similar to 
the ones given below.
    // Eg: public static boolean test4() {}
    // Do not change the method name or return type of the existing tests.
    // You can run your tests by commenting in the calls to the test 
methods 

    
    /*
     * test1
     * left rotation test
     */
    public static boolean test1() {
    	
    	System.out.println("test 1-1");
    	RedBlackTree<Integer> L_tree = new RedBlackTree<Integer>();
    	
    	L_tree.root = null;
    	boolean add = L_tree.insert(10); // root
    	boolean add2 = L_tree.insert(30);
    	boolean add3 = L_tree.insert(40);
    	boolean add4 = L_tree.insert(20);
    	boolean add5 = L_tree.insert(5);
    	
    	System.out.println(L_tree.toLevelOrderString());
    	    	    	    	
    	L_tree.rotate(L_tree.root.rightChild, L_tree.root);
    	System.out.println(L_tree.toLevelOrderString());
    	
    	String expectedOutput = "[ 30, 10, 40, 5, 20 ]";
    	if(!L_tree.toLevelOrderString().equals(expectedOutput)) {
    		System.out.println("output is wrong");
    		return false;
    	}
    	
    	System.out.println("test 1-2");
    	RedBlackTree<Integer> L_tree2 = new RedBlackTree<Integer>();
    	
    	L_tree2.root = null;
    	boolean add6 = L_tree2.insert(15); //root
    	boolean add7 = L_tree2.insert(20);
    	boolean add8 = L_tree2.insert(25);
    	boolean add9 = L_tree2.insert(8);
    	
    	System.out.println(L_tree2.toLevelOrderString());
    	
    	//create Exception because it contains null
    	/*
    	System.out.println("Make exception");
    	L_tree2.rotate(null, L_tree2.root);
    	*/
    	
        return true;
    }
    
    
    /*
     * test2
     * right rotation test
     */
    public static boolean test2() {
    	
    	System.out.println("test 2-1");
    	RedBlackTree<Integer> R_tree = new RedBlackTree<Integer>();
    	
    	R_tree.root = null;
    	boolean add = R_tree.insert(25); // root
    	boolean add2 = R_tree.insert(35);
    	boolean add3 = R_tree.insert(15);
    	boolean add4 = R_tree.insert(20);
    	boolean add5 = R_tree.insert(5);
    	
    	System.out.println(R_tree.toLevelOrderString());
    	    	    	    	
    	R_tree.rotate(R_tree.root.leftChild, R_tree.root);
    	System.out.println(R_tree.toLevelOrderString());
    	
    	String expectedOutput = "[ 15, 5, 25, 20, 35 ]";
    	if(!R_tree.toLevelOrderString().equals(expectedOutput)) {
    		System.out.println("output is wrong");
    		return false;
    	}
    	System.out.println("test 2-2");
    	RedBlackTree<Integer> R_tree2 = new RedBlackTree<Integer>();
    	
    	R_tree2.root = null;
    	boolean add6 = R_tree2.insert(15); //root
    	boolean add7 = R_tree2.insert(20);
    	boolean add8 = R_tree2.insert(25);
    	boolean add9 = R_tree2.insert(8);
    	
    	System.out.println(R_tree2.toLevelOrderString());
    	
    	//create Exception because it contains null
    	/*
    	System.out.println("Make exception");
    	R_tree2.rotate(null, R_tree2.root);
    	*/
    	
    	return true;
    }
    
    
    /*
     * test3
     * complex RBT test
     *  - when rotation is implemented in the subtree
     *  - and see if it makes exception if two parameters are not 
parent-child relationship
     */
    public static boolean test3() {
        
    	System.out.println("test 3");
    	RedBlackTree<Integer> LR_tree = new RedBlackTree<Integer>();
    	
    	LR_tree.root = null;
    	boolean add = LR_tree.insert(50); // root
    	boolean add2 = LR_tree.insert(30);
    	boolean add3 = LR_tree.insert(70);
    	boolean add4 = LR_tree.insert(40);
    	boolean add5 = LR_tree.insert(10);
    	boolean add6 = LR_tree.insert(5);
    	boolean add7 = LR_tree.insert(60);
    	boolean add8 = LR_tree.insert(80);
    	boolean add9 = LR_tree.insert(20);
    	boolean add10 = LR_tree.insert(95);
    	boolean add11 = LR_tree.insert(75);
    	System.out.println(LR_tree.toLevelOrderString());
    	    	    	    	
    	//test rotate in the subtree -> right rotation
    	LR_tree.rotate(LR_tree.root.leftChild.leftChild, 
LR_tree.root.leftChild);
    	System.out.println(LR_tree.toLevelOrderString());
    	String expectedOutput = "[ 50, 10, 70, 5, 30, 60, 80, 20, 40, 75, 
95 ]";
    	if(!LR_tree.toLevelOrderString().equals(expectedOutput)) {
    		System.out.println("output is wrong");
    		return false;
    	}
    	
    	//test rotate in the subtree -> left rotation
    	LR_tree.rotate(LR_tree.root.rightChild.rightChild, 
LR_tree.root.rightChild);
    	System.out.println(LR_tree.toLevelOrderString());
    	String expectedOutput2 = "[ 50, 10, 80, 5, 30, 70, 95, 20, 40, 60, 
75 ]";
    	if(!LR_tree.toLevelOrderString().equals(expectedOutput2)) {
    		System.out.println("output is wrong");
    		return false;
    	}
    	
    	//make exception because it is not parent-child relationship
    	/*
    	System.out.println("Make exception");
    	LR_tree.rotate(LR_tree.root.leftChild.leftChild, LR_tree.root);
    	*/
        return true;
    }
    
    
    /*
     * test4
     * random big tree rotation test
     */
    public static boolean test4() {
    	
    	Random rand = new Random();
    	
    	System.out.println("test 4");
    	RedBlackTree<Integer> Random_tree = new RedBlackTree<Integer>();
    	
    	//avoid duplicates
    	int[] temp = new int[7];
    	for(int i=0; i<7; i++) {
    	
        	int maxVal = 20;
        	temp[i] = rand.nextInt(maxVal) + 1;
    		for(int j=0; j<i; j++) {
    			if (temp[i] == temp[j]) {
    				i--;
    			}
    		}
    	}
    	for (int t=0; t<7; t++) {
    		boolean add = Random_tree.insert(temp[t]);	
    	}
    	
    	
    	String insertedNum = "[ " + temp[0] + ", " + temp[1] + ", " +
    			temp[2] + ", " + temp[3] + ", " + temp[4] + ", " +
    			temp[5] + ", " + temp[6] + " ]";
    	System.out.println("Inserted Numbers are: " + insertedNum);
    	
    	//rotate left and right; it may make exception if tree is 
unbalanced
    	//but it will be solved after RedBlackTree has its own property 
(self-balanced)
    	Random_tree.rotate(Random_tree.root.leftChild, Random_tree.root);
    	System.out.println(Random_tree.toLevelOrderString());
    	Random_tree.rotate(Random_tree.root.rightChild, Random_tree.root);
    	System.out.println(Random_tree.toLevelOrderString());
    	
    	return true;
    }
    
    /**
     * Main method to run tests. Comment out the lines for each test
     * to run them.
     * @param args
     * 
     * For test1 ~ test3, please uncomment each exception part at bottom
     * to check whether or not they made exceptions
     * test1: left rotation test & make exception
     * test2: right rotation test & make exception
     * test3: both rotations in the subtree of complex tree & make 
exception
     * test4: Big random tree and rotations
     */
    public static void main(String[] args) {
    	test1();
    	test2();
    	test3();
    	test4();
    }

}
