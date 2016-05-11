import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class solver {

	interface Stack {
		void push(Object o);
		Object pop();
		Object top();
		Boolean isEmpty();
	}
	
	class StackArray implements Stack {
		private final int MAX_COUNT = 10000;
		private Object[] objects = new Object[MAX_COUNT];
		private int count = 0;
		
		public void push(Object o) {
			if (count < MAX_COUNT) {
				objects[count++] = o;
			}
		}
		public Object pop() {
			if (count > 0) {
				return objects[--count];
			} else {
				return null;
			}
		}
		public Object top() {
			if (count > 0) {
				return objects[count-1];
			} else {
				return null;
			}
		}
		public Boolean isEmpty() {
			return count <= 0;
		}
		
		public void clear() {
			count = 0;
		}
	}

	interface Map<K,V> {
		void set(K key, V value);
		V get(K key);
	}
	
	class Pair<L, R> {
		public L left;
		public R right;
		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}
	}
	
	interface Comparator<T> {
		int compare(T x, T y);
	}
	
	class ArrayMap<K,V> implements Map<K,V> {
		private final int MAX_COUNT = 10000;
		Object[] pairs = new Object[MAX_COUNT];
		Comparator<K> comparator;
		
		public ArrayMap(Comparator<K> comparator) {
			for (int i=0; i<MAX_COUNT; i++) {
				pairs[i] = null;
			}
			this.comparator = comparator;
		}
		
		public void set(K key, V value) {
			for(int i=0; i<MAX_COUNT; i++) {
				if (null != pairs[i] && comparator.compare(((Pair<K,V>)pairs[i]).left, key) == 0) {
					((Pair<K,V>)pairs[i]).right = value;
					return;
				}
			}
			for(int i=0; i<MAX_COUNT; i++) {
				if (null == pairs[i]) {
					pairs[i] = new Pair<K,V>(key, value);
					return;
				}
			}
		}
		
		public V get(K key) {
			for(int i=0; i<MAX_COUNT; i++) {
				if (null != pairs[i] && comparator.compare(((Pair<K,V>)pairs[i]).left, key) == 0) {
					return ((Pair<K,V>)pairs[i]).right;
				}
			}
			return null;
		}
	}
	
	interface Queue<T> {
		void push(T o);
		T pop();
		T nextValue();
		Boolean isEmpty();
	}
	
	class QueueArray<T> implements Queue<T> {
		private final int MAX_COUNT = 10000;
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
		void preOrder(Action<T> a);
		void postOrder(Action<T> a);
		void levelPreOrder(Action<T> a); 
		void descendantsPreOrder(Action<T> a);
		void parentsFromRoot(Action<T> a);
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
		
		public void preOrder(Action<T> a) {
			a.doAction(data);
			Tree<T> child = leftMostChild;
			while(null != child) {
				child.preOrder(a);
				child = child.getRightSibling();
			}
		}

		public void postOrder(Action<T> a) {
			Tree<T> child = rightMostChild;
			while(null != child) {
				child.postOrder(a);
				child = child.getLeftSibling();
			}
			a.doAction(data);
		}
		
		public void levelPreOrder(Action<T> a) {
			QueueArray<Tree<T>> queue = new QueueArray<Tree<T>>();
			queue.push(this);
			while(!queue.isEmpty()) {
				Tree<T> cur = queue.pop();
				Tree<T> child = cur.getLeftMostChild();
				while(null != child) {
					queue.push(child);
					child = child.getRightSibling();
				}
				a.doAction(cur.getData());
			}
		} 

		public void descendantsPreOrder(Action<T> a) {
			Tree<T> child = leftMostChild;
			while(null != child) {
				child.preOrder(a);
				child = child.getRightSibling();
			}
		}

		public void parentsFromRoot(Action<T> a) {
			Stack stack = new StackArray();
			Tree<T> cur = this.getParent();
			while(null != cur) {
				stack.push(cur);
				cur = cur.getParent();
			}
			while(!stack.isEmpty()) {
				a.doAction(((Tree<T>)stack.pop()).getData());
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		solver s = new solver();
		s.solve();
	}
	
	class PrintData implements Action<Integer> {
		public void doAction(Integer data) {
			try {
				wr.write(data + " ");
			} catch (Exception e) {}
		}
	}
	
	private int nodeCount;
	class CountNodes implements Action<Integer> {
		public void doAction(Integer data) {
			nodeCount++;
		}
	}
	
	class IntegerComparator implements Comparator<Integer> {
		public int compare(Integer x, Integer y) {
			if (x.intValue() > y.intValue()) return 1;
			else if (x.intValue() < y.intValue()) return -1;
			else return 0;
		}
	}
	
	BufferedWriter wr;
	public void solve() throws IOException {
		Scanner sc = new Scanner(System.in);
       	wr = new BufferedWriter(new OutputStreamWriter(System.out));
       	
       	int N = sc.nextInt();
       	int R = sc.nextInt();
       	int X = sc.nextInt();
       	
       	Map<Integer, Tree<Integer>> nodes = new ArrayMap<Integer, Tree<Integer>>(new IntegerComparator());
       	Tree<Integer> tree = new PointerTree<Integer>(R); 
       	nodes.set(R, tree);
       	
       	for (int i=0; i < N; i++) {
       		int parentId = sc.nextInt();
       		int childId = sc.nextInt();
       		
       		Tree<Integer> parent = nodes.get(parentId);
       		if (null == parent) {
       			parent = new PointerTree<Integer>(parentId);
       			nodes.set(parentId, parent);
       		}
       		Tree<Integer> child = nodes.get(childId);
       		if (null == child) {
       			child = new PointerTree<Integer>(childId);
       			nodes.set(childId, child);
       		}
       		
       		parent.insertChildAsRightMost(child);
       	}
       	
//       	nodeCount = 0;
//       	tree.preOrder(new CountNodes()); wr.write(nodeCount + "\n");

       	tree.preOrder(new PrintData()); wr.write("\n");
       	tree.postOrder(new PrintData()); wr.write("\n");
       	tree.levelPreOrder(new PrintData()); wr.write("\n");
       	nodes.get(X).descendantsPreOrder(new PrintData()); wr.write("\n");
       	nodes.get(X).parentsFromRoot(new PrintData()); wr.write("\n");
       		
        wr.flush();
	}
}
