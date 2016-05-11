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

	private void genTest(int testNum, int numS, int numP) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	for(int i=0; i<numS; i++) {
       		wr.write(((char)(random.nextInt(25)+'a'))); 
       	}
   		wr.write("\n"); 
       	
   		for(int i=0; i<numP; i++) {
       		wr.write(((char)(random.nextInt(25)+'a'))); 
       	}
   		wr.write("\n"); 
       	
   		wr.close();
	}

	private void genPeriodTest(int testNum, int numP, int count) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	StringBuilder builder = new StringBuilder();
       	
       	for(int i=0; i<numP; i++) {
       		builder.append(((char)(random.nextInt(25)+'a'))); 
       	}
       	
   		for(int i=0; i<count; i++) {
       		wr.write(builder.toString()); 
       	}
   		wr.write("\n"); 
   		wr.write(builder.toString() + "\n"); 
       	
   		wr.close();
	}
	
	public void generate() throws FileNotFoundException, IOException {
		genTest(3, 10, 5);
		genPeriodTest(4, 3, 5);
		genPeriodTest(5, 10000, 10);
		genPeriodTest(6, 100000, 1);
		genPeriodTest(7, 1, 100000);
	}
}
