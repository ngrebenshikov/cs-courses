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

	private void genTestProbablyDistinct(int testNum, int planetsCount) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(planetsCount + "\n"); 

       	for (int i = 0; i < planetsCount; i++) {
        	wr.write(random.nextInt(1000*1000*1000) + " "); 
        	wr.write(random.nextInt(1000*1000*1000) + "\n"); 
       	}
       	
       	wr.close();
	}

	private void genTestTheSame(int testNum, int planetsCount) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(planetsCount + "\n"); 

       	for (int i = 0; i < planetsCount; i++) {
        	wr.write("-1000000 1000000\n"); 
       	}
       	
       	wr.close();
	}

	private void genTest(int testNum, int planetsCount) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(planetsCount + "\n"); 

       	for (int i = 0; i < planetsCount; i++) {
        	wr.write(random.nextInt(100) + " "); 
        	wr.write(random.nextInt(100) + "\n"); 
       	}
       	
       	wr.close();
	}

	public void generate() throws FileNotFoundException, IOException {
		genTestProbablyDistinct(2, 10);
		genTestProbablyDistinct(3, 100);
		genTestProbablyDistinct(4, 1000);
		genTestProbablyDistinct(5, 10000);
		genTestProbablyDistinct(6, 99999);
		genTestProbablyDistinct(7, 100000);
		genTestTheSame(8, 10);
		genTestTheSame(9, 100);
		genTestTheSame(10, 1000);
		genTestTheSame(11, 10000);
		genTestTheSame(12, 99999);
		genTestTheSame(13, 100000);
		genTest(14, 10);
		genTest(15, 100);
		genTest(16, 1000);
		genTest(17, 10000);
		genTest(18, 99999);
		genTest(19, 100000);
	}
}
