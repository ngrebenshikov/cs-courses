import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;


public class solver {
	
	interface BinaryTree<T> {
		T getData();
		void setData(T o);

		BinaryTree<T> getParent();
		void setParent(BinaryTree<T> parent);

		void setLeft(BinaryTree<T> value);
		BinaryTree<T> getLeft();
		
		void setRight(BinaryTree<T> value);
		BinaryTree<T> getRight();
	}
	
	class BinaryTreeImpl<T> implements BinaryTree<T> {
		BinaryTree<T> parent;
		BinaryTree<T> left;
		BinaryTree<T> right;
		T data;

		public BinaryTreeImpl(T data) { this.data = data; }
		
		public T getData() { return data; }
		public void setData(T o) { this.data = o; }

		public BinaryTree<T> getParent() { return parent; }
		public void setParent(BinaryTree<T> parent) { this.parent = parent; }

		public void setLeft(BinaryTree<T> value) { left = value;}
		public BinaryTree<T> getLeft() { return left; }
		
		public void setRight(BinaryTree<T> value) { right = value; }
		public BinaryTree<T> getRight() { return right; }
	}
	
	class HaffmanTree extends BinaryTreeImpl<String> {
		public double weight;
		
		public HaffmanTree(String str, double weight) {
			super(str);
			this.weight = weight;
		}
	}
	
	final int MAX_COUNT = 26;
	HaffmanTree[] forest = new HaffmanTree[MAX_COUNT];
	int forestLength = 0;
	String[] chars = new String[MAX_COUNT];
	String[] codes = new String[MAX_COUNT];
	
	
	public static void main(String[] args) throws IOException {
		solver s = new solver();
		s.solve();
	}
	
	BufferedWriter wr;
	public void solve() throws IOException {
		Scanner sc = new Scanner(System.in);
       	wr = new BufferedWriter(new OutputStreamWriter(System.out));
       	
       	int N = sc.nextInt(); sc.nextLine();
       	
       	for (int i=0; i<N; i++) {
       		String line = sc.nextLine();
       		String[] strs = line.split("\\s+");
       		forest[i] = new HaffmanTree(strs[0], Double.valueOf(strs[1]));
       		chars[i] = strs[0];
       	}
       	
       	forestLength = N;

       	while (forestLength > 1) {
       		swapInForest(getIndexWithMinWeight(), forestLength-1);
       		forestLength--;
       		int min = getIndexWithMinWeight();
       		HaffmanTree newTree = new HaffmanTree(forest[min].getData()+forest[forestLength].getData(), forest[min].weight + forest[forestLength].weight);
       		newTree.setLeft(forest[min]);
       		newTree.setRight(forest[forestLength]);
       		forest[min] = newTree;
       	}

       	saveCodes(forest[0], "");
       	
       	for(int i=0; i < N; i++) {
			wr.write(chars[i] + " " + codes[i] + "\n");
       	}

       	wr.flush();
	}


	private void swapInForest(int i, int j) {
		HaffmanTree temp = forest[i];
		forest[i] = forest[j];
		forest[j] = temp;
	}
	
	private int getIndexWithMinWeight() {
		int index = 0;
		double value = forest[0].weight;
		for (int i=1; i<forestLength; i++) {
			if (forest[i].weight < value) {
				index = i;
				value = forest[i].weight; 
			}
		}
		return index;
	}
	
	private void saveCodes(HaffmanTree cur, String code) throws IOException {
		if (null == cur.getLeft() && null == cur.getRight()) {
			putIntoCodes(cur.getData(), code);
		} else {
			if (null != cur.getLeft()) saveCodes((HaffmanTree)cur.getLeft(), code + "1");
			if (null != cur.getRight()) saveCodes((HaffmanTree)cur.getRight(), code + "0");
		}
	}
	
	private void putIntoCodes(String ch, String code) {
		for(int i=0; i < MAX_COUNT; i++) {
			if (chars[i] == ch) {
				codes[i] = code;
				break;
			}
		}
	}
}
