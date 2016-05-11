import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class generator {
	interface Queue<T> {
		void push(T o);
		T pop();
		T nextValue();
		Boolean isEmpty();
		void clear();
		int getLength();
	}
	
	class QueueArray<T> implements Queue<T> {
		private final int MAX_COUNT = 100000;
		private T[] objects = (T[]) new Object[MAX_COUNT];
		private int top = 0;
		private int bottom = 0;
		
		public void push(T o) {
			if (top < MAX_COUNT) {
				objects[top++] = o;
			}
		}
		public T pop() {
			if (top - bottom > 0) {
				return objects[bottom++];
			} else {
				return null;
			}
		}
		public T nextValue() {
			if (top - bottom > 0) {
				return objects[bottom-1];
			} else {
				return null;
			}
		}
		public Boolean isEmpty() {
			return top - bottom <= 0;
		}
		
		public void clear() {
			top = 0; bottom = 0;
		}
		
		public int getLength() {
			return top-bottom;
		}
	}

	interface Action<T> {
		void doAction(T o);
	}

	interface Tree<T> {
		T getData();
		void setData(T o);
		Tree<T> getLeftMostChild();
		Tree<T> getRightMostChild();
		Tree<T> getLeftSibling();
		void setLeftSibling(Tree<T> sibling);
		Tree<T> getRightSibling();
		void setRightSibling(Tree<T> sibling);
		Tree<T> getParent();
		void setParent(Tree<T> parent);
		void insertChildAsRightMost(Tree<T> child);
		void preOrder(Action<Tree<T>> a);
		void postOrder(Action<Tree<T>> a);
		void levelPreOrder(Action<Tree<T>> a); 
	}
	
	class PointerTree<T> implements Tree<T> {
		private T data;
		private Tree<T> parent; 
		private Tree<T> leftMostChild; 
		private Tree<T> rightMostChild; 
		private Tree<T> leftSibling; 
		private Tree<T> rightSibling; 
		
		public PointerTree(T data) {
			this.data = data;
		}
		
		public T getData() { return data; }
		public void setData(T data) { this.data = data; }
		
		public Tree<T> getParent() { return parent; }
		public void setParent(Tree<T> parent) { this.parent = parent; }

		public Tree<T> getLeftMostChild() { return leftMostChild; }
		public Tree<T> getRightMostChild() { return rightMostChild; }
		
		public Tree<T> getLeftSibling() { return leftSibling;}
		public void setLeftSibling(Tree<T> sibling) { leftSibling = sibling; }
		public Tree<T> getRightSibling() { return rightSibling;}
		public void setRightSibling(Tree<T> sibling) { rightSibling = sibling; }

		public void insertChildAsRightMost(Tree<T> child) {
			if (null != rightMostChild) {
				rightMostChild.setRightSibling(child);
				child.setLeftSibling(rightMostChild);
				rightMostChild = child;
			} else {
				rightMostChild = child;
				leftMostChild = child;
			}
			child.setParent(this);
		}
		
		public void preOrder(Action<Tree<T>> a) {
			a.doAction(this);
			Tree<T> child = leftMostChild;
			while(null != child) {
				child.preOrder(a);
				child = child.getRightSibling();
			}
		}

		public void postOrder(Action<Tree<T>> a) {
			Tree<T> child = rightMostChild;
			while(null != child) {
				child.postOrder(a);
				child = child.getLeftSibling();
			}
			a.doAction(this);
		}
		
		public void levelPreOrder(Action<Tree<T>> a) {
			QueueArray<Tree<T>> queue = new QueueArray<Tree<T>>();
			queue.push(this);
			while(!queue.isEmpty()) {
				Tree<T> cur = queue.pop();
				Tree<T> child = cur.getLeftMostChild();
				while(null != child) {
					queue.push(child);
					child = child.getRightSibling();
				}
				a.doAction(cur);
			}
		} 
	}

	class TreeEdge<T> {
		public Tree<T> parent;
		public Tree<T> child;
		public TreeEdge(Tree<T> parent, Tree<T> child) {
			this.parent = parent;
			this.child = child;
		}
	}
	
	private Queue<TreeEdge<Integer>> edges = new QueueArray<TreeEdge<Integer>>();
	
	class GetEdgesAction implements Action<Tree<Integer>> {
		public void doAction(Tree<Integer> node) {
			edges.push(new TreeEdge<Integer>(node.getParent(), node));
		}
	}

	private Random random = new Random();

	public static void main(String[] args) throws FileNotFoundException, IOException {
		generator g = new generator();
		g.generate();
	}

	private String getNumberWithLeadingZeros(int x, int length) {
		String result = String.valueOf(x);
		for (int i=result.length(); i < length; i++) {
			result = "0" + result;
		}
		return result;
	}
	
	private Tree<Integer> makeTree(int start, int count, int maxChildren) {
		Tree<Integer> tree = new PointerTree<Integer>(start++);
		count--;
		QueueArray<Tree<Integer>> queue = new QueueArray<Tree<Integer>>();
		queue.push(tree);
		while(count > 0 && !queue.isEmpty()) {
			Tree<Integer> cur = queue.pop();
			int numChildren = Math.min(count, random.nextInt(maxChildren-1)+1);
			count -= numChildren;
			while (numChildren > 0) {
				Tree<Integer> child = new PointerTree<Integer>(start++); 
				queue.push(child);
				cur.insertChildAsRightMost(child);
				numChildren--;
			}
		}
		queue.clear();
		return tree;
	}
	
	
	private void genTest(int testNum, int start, int n, int maxChildren) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt"))); 
       	Tree<Integer> tree = makeTree(start, n, maxChildren);
       	edges.clear();
       	tree.preOrder(new GetEdgesAction());
       	wr.write(edges.getLength()-1 + "\n"); // N
       	wr.write(tree.getData() + "\n"); // R
       	wr.write((random.nextInt(n)+start) + "\n"); // X
		while (!edges.isEmpty()) {
			TreeEdge<Integer> edge = edges.pop();
			if (null != edge.parent) {
				wr.write(edge.parent.getData() + " " + edge.child.getData() + "\n"); // Edge
			}
		}
		wr.close();
	}


	public void generate() throws FileNotFoundException, IOException {
		genTest(2,1,10,4); // height = ~log3(N), N=10
		genTest(3,1,100,10); // height = ~log9(N), N=100
		genTest(4,1,5000,10); // height = ~log9(N), N=5000
		genTest(5,1,5000,5000); // wide tree; height = ~2
		genTest(6,5,5000,2); // high tree; height = ~5000
		genTest(7,100000,1,1); // only root
	}

}
