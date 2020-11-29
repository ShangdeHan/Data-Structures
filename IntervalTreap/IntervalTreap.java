/**
 * Team members: Shangde Han, Tao Li
 */

import java.util.Random;

public class IntervalTreap {
	public Node root;
	public int size;
	
	/**
	 * Constructor with no parameters.
	 */
	public IntervalTreap() {
		root = null;
		size = 0;
	}
	

	/**
	 * Returns a reference to the root node.
	 * @return
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Returns the number of nodes in the treap.
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns the height of the treap.
	 * @return
	 */
	public int getHeight() {
		return findHeight(root);
	}
	
	/**
	 * adds node z, whose interv attribute references 
	 * an Interval object, to the interval treap. This 
	 * operation must maintain the required interval treap
	 * properties. The expected running time of this method 
	 * should be O(logn) on an n-node interval treap.
	 * @param z
	 */
	public void intervalInsert(Node z) {
		z.priority = new Random().nextInt(100);
		z.imax = z.i.high;  
		if(root == null) {
			root = z;
		}else {
		   Node x = root;
		    //insert node z to the leaf
		   Node y = null;
		   while(x != null) {
		    y = x;
		    y.imax = Math.max(y.imax, z.i.high);
		    if(z.i.low < x.i.low) {
		     x = x.left;
		    }else {
		     x = x.right;
		    }
		   }
		   z.parent = y;
		   if(y == null) {
		    root = z;
		   }else if(z.i.low < y.i.low) {
		    y.left = z;
		   }else {
		    y.right = z;
		   }
		   
		   while(z.parent != null) {
		    if(z.priority < z.parent.priority ) {
		    //left rotation		     
		     if(z.parent.right == z) {
		      z = rotation_left(z);
		     }
		     //right rotation;
		     if(z.parent.left == z) {
		      z = rotation_right(z);
		     }
		     
		     if(z.parent.parent != null){
		      if(z.parent.parent.left == z.parent) {
		       z.parent.parent.left = z;
		       z.parent = z.parent.parent;

		      }else {
		       z.parent.parent.right = z;
		       z.parent = z.parent.parent;
		      }
		     }else {
		      root = z;
		     }
		    }else {
		     break;
		    }

		    if(root.i == z.i) {
		     root.parent = null;
		    }
		   }
		  }
	}
		 
	public Node rotation_left(Node z) {
		Node sub_root = z.parent;
		sub_root.right = z.left;
        z.left = sub_root;
        z.left.imax = imax_count(z.left);
        return z;
	}
	public Node rotation_right(Node z) {
		Node sub_root = z.parent;
		sub_root.left = z.right;
		z.right = sub_root;
	    z.right.imax = imax_count(z.right);
	    return z;
	}
	public int imax_count(Node x) {
		int imax = x.i.high;
		if(x.right == null && x.left != null) {
		   imax = Math.max(imax, x.left.imax);
		}else if(x.right !=null && x.left == null) {
		   imax = Math.max(imax,x.right.imax);
        }else if(x.right != null && x.left != null) {
		   imax = Math.max(imax, x.right.imax);
		   imax = Math.max(imax, x.left.imax);
	   }
		return imax;
	}	 

	
	/**
	 * removes node z from the interval treap. This operation 
	 * must maintain the required interval treap properties. 
	 * The expected running time of this method should be O(logn)
	 * on an n-node interval treap.
	 * @param z
	 */
	public void intervalDelete(Node z) {
		Node n = intervalSearchExactly(z.i);
		if(n == null) System.out.println("Cannot find this node");
		else {
			size--;
			if(n.left == null) {
				if(n.parent != null) {
					if(n.parent.left == n) n.parent.left = n.right;
					else n.parent.right = n.right;
				}
				if(n.right != null) {
					n.right.parent = n.parent;
				}
				/* update imax*/
				Node temp = n.parent;
				while(temp != null) {
					updateImax(temp);
					temp = temp.parent;
				}
			}else if(n.left != null && n.right == null) {
				if(n.parent != null) {
					if(n.parent.left == n) n.parent.left = n.left;
					else n.parent.right = n.left;
				}
				n.left.parent = n.parent;
				/* update imax*/
				Node temp = n.parent;
				while(temp != null) {
					updateImax(temp);
					temp = temp.parent;
				}
			}else if(n.left != null && n.right != null) {
				Node y = Minimum(n.right);
				if(n.parent != null) {
					if(n.parent.left == n) n.parent.left = y;
					else n.parent.right = y;
				}
				y.left = n.left;
				y.right = n.right;
				y.parent = n.parent;
				/* rotation */
				Node newNode = y;
				if(newNode.left.priority > newNode.right.priority) rotateLeft(newNode);
				else rotateRight(newNode);
				while(newNode.left != null &&  newNode.left.priority < newNode.priority) {
					rotateRight(newNode);
					updateImax(newNode);
					updateImax(newNode.parent);
				}
				while(newNode.right != null &&  newNode.right.priority < newNode.priority) {
					rotateLeft(newNode);
					updateImax(newNode);
					updateImax(newNode.parent);
				}
			}
		}
	}
	
	/**
	 * returns a reference to a node x in the interval treap such 
	 * that x.interv overlaps interval i, or null if no such element 
	 * is in the treap. This method must not modify the interval 
	 * treap. The expected running time of this method should be
	 * O(logn) on an n-node interval treap.
	 * @param z
	 */
	public Node intervalSearch(Interval i) {
		  Node x = root;
		  while(x != null && (x.i.low > i.high || x.i.high < i.low)) {
		    if(x.left != null && x.left.imax >=i.low) {
		     x = x.left;
		    }else {
		     x = x.right;
		    }
		   
		  }
		  return x;
	}
	
	/**
	 * return the successor of the node z
	 * @param z
	 * @return
	 */
	public Node Minimum(Node z) {
		while(z.left != null) {
			z = z.left;
		}
		return z;
	}
	
	/**
	 * update node's imax
	 * @param z
	 */
	public void updateImax(Node z) {
		if(z.left == null && z.right == null) {
			z.imax = z.i.high;
		}else if(z.left == null && z.right != null) {
			z.imax = Math.max(z.i.high, z.right.imax);
		}else if(z.left != null && z.right == null) {
			z.imax = Math.max(z.i.high, z.left.imax);
		}else {
			z.imax = Math.max(z.left.imax, z.right.imax);
			z.imax = Math.max(z.i.high, z.imax);
		}
	}
	
	/**
	 * this method rotate left from root
	 * @param root
	 */
	public void rotateLeft(Node root) {
		Node n1 = root.right;
		Node n2 = root.right.left;
		if(root.parent != null) {
			if(root.i == root.parent.left.i) root.parent.left = n1;
			else root.parent.right = n1;
			n1.parent = root;
		} 
		n1.left = root;
		root.parent = n1;
		root.right = n2;
		if(n2 != null) n2.parent = root;
	}
	
	
	/**
	 * this method rotate right from root
	 * @param root
	 */
	public void rotateRight(Node root) {
		Node n1 = root.left;
		Node n2 = root.left.right;
		if(root.parent != null) {
			if(root.i == root.parent.left.i) root.parent.left = n1;
			else root.parent.right = n1;
			n1.parent = root;
		} 
		n1.right = root;
		root.parent = n1;
		root.left = n2;
		if(n2 != null) n2.parent = root;
	}
	
	/**
	 * returns a reference to a Node object x in the treap such that 
	 * x.interv.low = i.low and x.interv.high = i.high, or null if no
	 * such node exists. The expected running time of this method should
	 *  be O(logn) on an n-node interval treap.
	 * @param i
	 * @return
	 */
	public Node intervalSearchExactly(Interval i) {
		Node temp = root;
		while(temp.i.low != i.low && temp.i.high != i.high) {
			if(i.low < temp.i.low) {
				temp.left.parent = temp;
				temp = temp.left;
			}else if(i.low > temp.i.low) {
				temp.right.parent = temp;
				temp = temp.right;
			}else if(i.low == temp.i.low && i.high != temp.i.high) {
				temp.right.parent = temp;
				temp = temp.right;
			}
			if(temp == null) return temp;
		}
		return temp;
	}
	
	/**
	 * This method can find node's height and findHeight(root) will return 
	 * the height of treap
	 * @param z
	 * @return
	 */
	public int findHeight(Node z) {
		if(z == null) return -1;
		int leftlength = findHeight(z.left);
		int rightlength = findHeight(z.right);
		if(leftlength > rightlength) return leftlength+1;
		else return rightlength+1;
	}
}
