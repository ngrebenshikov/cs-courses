import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;
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

	private void genTest(int testNum, int wordsInDict, int wordsInText) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(wordsInDict + "\n"); 

    	for (int i = 0; i < wordsInDict; i++) {
        	wr.write(i + " " + (i+1)+ "\n"); 
       	}
       	
       	for (int i = 0; i < wordsInText; i++) {
        	wr.write(random.nextInt(wordsInDict) + " "); 
        	if (i % (random.nextInt(50)+1) == 0) {
        		wr.write("\n");
        	}
       	}
       	wr.close();
	}

	private void genTestNotTranslateable(int testNum, int wordsInDict, int wordsInText) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(wordsInDict + "\n"); 

    	for (int i = 0; i < wordsInDict; i++) {
        	wr.write(i + " " + (i+1)+ "\n"); 
       	}
       	
       	for (int i = 0; i < wordsInText; i++) {
        	wr.write((random.nextInt(wordsInDict) + wordsInDict + 1) + " "); 
        	if (i % (random.nextInt(50)+1) == 0) {
        		wr.write("\n");
        	}
       	}
       	wr.close();
	}

	public void generate() throws FileNotFoundException, IOException {
		genTest(2, 2, 5);
		genTestNotTranslateable(3, 10, 10);
		genTest(4, 10000, 10000);
		genTest(5, 100000, 100000);
		genTestNotTranslateable(6, 100000, 100000);
	}
}
