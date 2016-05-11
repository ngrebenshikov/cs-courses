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
		private final int MAX_COUNT = 255;
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
	
	public static void main(String[] args) throws IOException {
		solver s = new solver();
		s.solve();
	}
	
	private String getCloseBracket(String openBracket) {
		if (openBracket == null) {
			return null;
		} else if (openBracket.contentEquals("[")) {
			return "]";
		} else if (openBracket.contentEquals("(")){
			return ")";
		} else {
			return null;
		}
	}
	
	public void solve() throws IOException {
		StackArray stack = new StackArray();
		
		Scanner sc = new Scanner(System.in);
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
        while(sc.hasNextLine()) {
        	String brackets = sc.nextLine();
        	stack.clear();
        	for(int i=0; i < brackets.length(); i++) {
        		if (brackets.charAt(i) == '[' || brackets.charAt(i) == '(') {
        			stack.push(brackets.charAt(i)+"");
        		} else {
        			String sB = getCloseBracket((String)(stack.top()));
        			String bB = brackets.charAt(i)+""; 
        			if (null != sB && bB.contentEquals(sB)) {
        				stack.pop();
        			} else {
        				stack.push(""); break;
        			}
        		}
        	}
    		if (stack.isEmpty()) {
				wr.write("correct\n");
    		} else {
				wr.write("incorrect\n");
    		}
        }
        wr.flush();
	}
}
