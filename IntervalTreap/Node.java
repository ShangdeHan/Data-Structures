/**
 * Team members: Shangde Han, Tao Li
 */

import java.util.Random;

public class Node{
	public Node left;
	public Node right;
	public Node parent;
	public Interval i;
	public int priority;
	public int imax;

	/**
	 * Constructor that takes an Interval object i as 
	 * its parameter. The constructor must generate a 
	 * priority for the node. Therefore, after creation 
	 * of a Node object, getPriority()(defined below) 
	 * must return the priority of this node.
	 * @param i
	 */
	public Node(Interval i) {
		this.i = i;
		this.left = this.right = null;
		this.priority = new Random().nextInt(10000);
		parent = null;
		imax = 0;
	}
	

	/**
	 * Returns the parent of this node.
	 * @return
	 */
	public Node getParent() {
		
		return parent;
	}
	
	/**
	 * Returns the left child of this node.
	 * @return
	 */
	public Node getLeft() {
		return left;
	}
	
	/**
	 * Returns the right child of this node.
	 * @return
	 */
	public Node getRight() {
		return right;
	}
	
	/**
	 * Returns the interval object stored in this node.
	 * @return
	 */
	public Interval getInterv() {
		return i;
	}
	
	/**
	 * Returns the value of theimaxfield of this node.
	 * @return
	 */
	public int getIMax() {
		return imax;
	}
	
	/**
	 * Returns the priority of this node.
	 * @return
	 */
	public int getPriority() {
		return priority;
	}
}
