import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Scanner;


public class solver {
	interface List<T, P> {
		void add(T o) throws Exception;
		T getAt(P position);
		void setAt(P position, T value);
		P next(P position);
		P previous(P position);
		P end();
		P first();
		void clear();
		void sort(Comparator<T> comparator);
	}
	
	class ArrayList<T> implements List<T, Integer> {
		
		T objects[];
		int count;
		int realCount = 0;
		
		@SuppressWarnings("unchecked")
		public ArrayList(int count) {
			this.count = count;
			objects = (T[])new Object[count];
			clear();
		}
		
		public void add(T o) throws Exception {
			if (realCount == count) {
				throw new Exception("ArrayList overflow");
			}
			objects[realCount++] = o;
		}
		
		public T getAt(Integer position) { return objects[position]; } 
		public void setAt(Integer position, T value) { 
			objects[position] = value;
			if (position + 1 > realCount) {
				realCount = position + 1;
			}
		} 
		
		public Integer next(Integer position) { return position + 1; }
		public Integer previous(Integer position) { return position - 1;}
		public Integer end() { return realCount; }
		public Integer first() { return 0; }
		
		public void clear() {
			realCount = 0;
			for(int i=0; i < count; i++) {
				objects[i] = null;
			}
		}
		
		private void swap(Integer pos1, Integer pos2) {
			T temp = objects[pos1];
			objects[pos1] = objects[pos2];
			objects[pos2] = temp;
		}
		
		private void heapify(Comparator<T> comparator, Integer position, Integer border) {
			if (position > border) return;
			Integer pos = position;
			if (2*position <= border) { //left child exists
				if (comparator.compare(objects[pos], objects[2*position]) < 0)
					pos = 2*position;
				if (2*position+1 <= border) { //right child exists
					if (comparator.compare(objects[pos], objects[2*position+1]) < 0)
						pos = 2*position+1;
				}
			}
			if (pos != position) {
				swap(pos, position);
				heapify(comparator, pos, border);
			}
		}
		
		public void sort(Comparator<T> comparator) {
			for (int i = realCount/2; i >= 0; i--) {
				heapify(comparator, i, realCount-1);
			}
			for(int i = realCount-1; i > 0; i--) {
				swap(0, i);
				heapify(comparator, 0, i-1);
			}
		}
	}
	
	public class DoubleComparator implements Comparator<Double> {
		public int compare(Double d1, Double d2) {
			return Double.compare(d1, d2);
		}
		public boolean equals(Object o) {
			return hashCode() == o.hashCode();
		}
		
	}
	
	public void solve() throws IOException, Exception {
		Scanner sc = new Scanner(System.in);
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

        ArrayList<Double> a = new ArrayList<Double>(100000);

        int N = sc.nextInt();

       	for (int i=0; i < N; i++) {
       		double x = (double)sc.nextInt();
       		double y = (double)sc.nextInt();
       		a.add(x*x+y*y); 
       	}
       	
       	a.sort(new DoubleComparator());
       	
       	Double currentOrbit = a.getAt(a.first());
       	int planetsOnOrbit = 0;
       	for(Integer i = a.first(); i < a.end(); i = a.next(i)) {
       		if (currentOrbit.compareTo(a.getAt(i)) == 0 ) {
       			planetsOnOrbit++;
       		} else {
       			wr.write(planetsOnOrbit + " ");
       			planetsOnOrbit = 1;
       			currentOrbit = a.getAt(i);
       		}
       	}
		wr.write(planetsOnOrbit + "");
        wr.flush();
	}
	
	public static void main(String[] args) throws IOException, Exception {
		solver s = new solver();
		s.solve();
	}
	
	
}
