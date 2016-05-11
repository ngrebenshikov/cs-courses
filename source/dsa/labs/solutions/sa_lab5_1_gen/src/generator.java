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

	private void genTest(int testNum, int number) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(number + "\n"); 

       	for(int i=0; i<number; i++) {
           	wr.write(random.nextInt(Integer.MAX_VALUE) + " " + random.nextInt(Integer.MAX_VALUE) + "\n");
       	}
       	wr.close();
	}
	
	public void generate() throws FileNotFoundException, IOException {
		genTest(2, 100);
		genTest(3, 10000);
		genTest(4, 1000000);
		genTest(5, 5000000);
	}
}
