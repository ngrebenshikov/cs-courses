import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class generator {
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

	private void genTest(int testNum, int num, int distance, int V, int L) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(num + " " + V + " " + L + "\n"); 

       	for(int i=0; i<num; i++) {
       		wr.write((random.nextInt(999)+1) + " " + (distance*i)+ "\n"); 
       	}
       	wr.close();
	}
	
	public void generate() throws FileNotFoundException, IOException {
		genTest(5, 10, 5, 100, 1);
		genTest(6, 10, 100, 100, 1);
		genTest(7, 10, 1, 100, 1);
		genTest(8, 1000, 1, 1000, 1);
		genTest(9, 1000, 1000, 1000, 1);
		genTest(10, 10000, 1, 10000, 1);
		genTest(11, 10000, 10000, 10000, 1);
	}
}
