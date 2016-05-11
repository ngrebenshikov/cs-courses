import java.io.*;
import java.util.*;


public class generator {
	private static final int MAX_N = 100;
	private static final int MAX_M = 1000;
	private static final int TESTS_COUNT = 10;

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
	
	private String getRandomName(int length) {
		String result = new String();
		for (int i = 0; i < length; i++) {
			result += random.nextInt(9);
			
		}
		return result;
	}
	
	private void genTest(int testNum, int n, int m, int namesLength) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt"))); 
		wr.write(n + "\n");
		for (int i = 0; i < n; i++) {
			wr.write(getRandomName(namesLength) + "\n");
		}
		wr.write(m + "\n");
		for (int i = 0; i < m; i++) {
			wr.write(getRandomName(namesLength) + "\n");
		}
		wr.close();
	}

	public void generate() throws FileNotFoundException, IOException {
		genTest(2,1,10,3);
		genTest(3,5,10,3);
		genTest(4,100,1000,255);
	}
}
