import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class solver {
	
	interface Map<T> {
		void set(Comparable key, T value);
		T get(Comparable key);
		boolean containsKey(Comparable key);
	}

	class AvlSearchTreeNode<T> {
		public Comparable key;
		public T value;
		public AvlSearchTreeNode<T> left, right;
		public int height;
		public AvlSearchTreeNode(Comparable key, T value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
			height = 1;
		}
		public void updateHeight() {
			height = Math.max(
					null != left ? left.height : 0, 
					null != right ? right.height : 0) + 1;
		}
	}
	
	class AvlSearchTree<T> {
		private AvlSearchTreeNode<T> root; 
		
		public AvlSearchTree() {
			root = null;
		}

		public void insert(Comparable key, T value) {
			root = insertNode(root, key, value);
		}
		
		public T find(Comparable key) {
			AvlSearchTreeNode<T> node = findNode(root, key);
			return (null != node) ? node.value : null;
		}
		
		private int getHeightOfNode(AvlSearchTreeNode<T> node) {
			return null != node ? node.height : 0;
		}
		
		private AvlSearchTreeNode<T> rotateR(AvlSearchTreeNode<T> node) {
			AvlSearchTreeNode<T> result = node.left;
			try {
			node.left = result.right;
			result.right = node;
			result.updateHeight();
			node.updateHeight();
			} catch (Exception e) {
				System.out.print(node.value);
			}
			return result;
		}
		
		private AvlSearchTreeNode<T> rotateL(AvlSearchTreeNode<T> node) {
			AvlSearchTreeNode<T> result = node.right;
			node.right = result.left;
			result.left = node;
			result.updateHeight();
			node.updateHeight();
			return result;
		}

		private AvlSearchTreeNode<T> rotateLR(AvlSearchTreeNode<T> node) {
			node.left = rotateL(node.left);
			return rotateR(node);
		}
		
		private AvlSearchTreeNode<T> rotateRL(AvlSearchTreeNode<T> node) {
			node.right = rotateR(node.right);
			return rotateL(node);
		}

		private AvlSearchTreeNode<T> insertNode(AvlSearchTreeNode<T> node, Comparable key, T value) {
			AvlSearchTreeNode<T> result = node;
			if (null == node) {
				result =  new AvlSearchTreeNode<T>(key, value);
			}
			int comparingResult = 0;
			if (null != node) {
				comparingResult = key.compareTo(node.key);
			}
			if (0 == comparingResult) {
				result.value = value;
			} else {
				if (comparingResult < 0) {
					result.left = insertNode(result.left, key, value);
					if(getHeightOfNode(result.left) - getHeightOfNode(result.right) > 1) {
						if (key.compareTo(result.left.key) < 0) {
							result = rotateR(result);
						} else {
							result = rotateLR(result);
						}
					}
				} else {
					result.right = insertNode(result.right, key, value);
					if(getHeightOfNode(result.right) - getHeightOfNode(result.left) > 1) {
						if (key.compareTo(result.right.key) > 0) {
							result = rotateL(result);
						} else {
							result = rotateRL(result);
						}
					}
				}
				
				result.updateHeight();
			}
			return result;
		}
		
		private AvlSearchTreeNode<T> findNode(AvlSearchTreeNode<T> node, Comparable key) {
			if (null == node) return null;
			int comparingResult = key.compareTo(node.key); 
			if (0 == comparingResult) {
				return node;
			} else if (comparingResult < 0) {
				return findNode(node.left, key);
			} else {
				return findNode(node.right, key);
			}
		}

	}
	
	class AvlMap<T> implements Map<T> {
		private AvlSearchTree<T> searcher;
		
		public AvlMap() {
			searcher = new AvlSearchTree<T>();
		}
		
		public void set(Comparable key, T value) {
			searcher.insert(key, value);
		}
		
		public T get(Comparable key) {
			return searcher.find(key);
		}

		public boolean containsKey(Comparable key) {
			return null != get(key);
		}
	}
	
	public static void main(String[] args) throws IOException {
		solver s = new solver();
		s.solve();
	}
	
	
	public void solve() throws IOException {
		Scanner sc = new Scanner(System.in);
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
       	
       	AvlMap<String> dict = new AvlMap<String>();
       	
       	int N = sc.nextInt(); sc.nextLine();

       	for (int i=0; i < N; i++) {
       		String[] words = sc.nextLine().split(" ");
       		dict.set(words[0].trim(), words[1].trim());
       	}
       	
       	StringBuilder textRead = new StringBuilder();
       	while(sc.hasNextLine()) {
       		textRead.append(sc.nextLine());
       	}
       	
       	String[] text = textRead.toString().split(" ");
       	int wordCount = text.length;
       	for (int i=0; i < wordCount; i++) {
       		String translated = dict.get(text[i]);
       		if (null == translated) translated = text[i];
       		wr.write(translated + " ");
       	}
        wr.flush();
	}
}
