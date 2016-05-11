import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.Vector;


public class solver {
	
	interface Queue<T> {
		void push(T o);
		T pop();
		T nextValue();
		Boolean isEmpty();
	}
	
	class QueueArray<T> implements Queue<T> {
		private final int MAX_COUNT = 1000;
		private T[] objects;
		private int top = 0;
		private int bottom = 0;
		
		public QueueArray() {
			Vector<T> vector = new Vector<T>(MAX_COUNT);
			for(int i=0; i < MAX_COUNT; i++) {
				vector.add(null);
			}
			objects = (T[])vector.toArray();
		}
		
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

	public static void main(String[] args) throws IOException {
		solver s = new solver();
		s.solve();
	}
	
	public void solve() throws IOException {
		QueueArray<String> tugs = new QueueArray<String>();
		
		Scanner sc = new Scanner(System.in);
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
       	
       	int tugsCount = sc.nextInt(); sc.nextLine();
       	for (int i=0; i<tugsCount; i++) {
       		tugs.push(sc.nextLine());
       	}

       	int shipsCount = sc.nextInt(); sc.nextLine();
       	for (int i=0; i<shipsCount; i++) {
       		String ship = sc.nextLine();
       		String tug = (String)tugs.pop();
       		wr.write(ship + " - " + tug + "\n");
       		tugs.push(tug);
       	}
        wr.flush();
	}
}
