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

	private void genUniformTest(int testNum) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
       	char[] chars = new char[26];
       	double[] probabilities = new double[26];
       	int i = 0;
       	double sum = 0;
       	for (char ch = 'a'; ch <= 'z'; ch++) {
       		chars[i] = ch;
       		probabilities[i] = random.nextInt(100);
       		sum += probabilities[i];
       		i++;
       	}
       	wr.write("26\n"); 
       	for (int j=0; j < 26; j++) {
       		double value = probabilities[j] / sum;
        	wr.write(chars[j] + " " + String.format(new Locale("Russian"), "%1$.5f", value) + "\n"); 
       	}
       	
		wr.close();
	}

	private void genOneLetterTest(int testNum) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
       	char[] chars = new char[26];
       	double[] probabilities = new double[26];
       	int i = 0;
       	for (char ch = 'a'; ch <= 'z'; ch++) {
       		chars[i] = ch;
       		probabilities[i] = 0;
       		i++;
       	}

       	wr.write("26\n"); 
       	probabilities[random.nextInt(26)] = 1;
       	for (int j=0; j < 26; j++) {
        	wr.write(chars[j] + " " + String.format(new Locale("Russian"), "%1$.5f", probabilities[j]) + "\n"); 
       	}
       	
		wr.close();
	}

	public void generate() throws FileNotFoundException, IOException {
		genUniformTest(3);
		genOneLetterTest(4);
	}
}
