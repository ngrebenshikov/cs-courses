import java.io.*;
import java.util.Scanner;



public class solver {

	interface BigNumber {
		int[] getDigits();
		int getCount();
		void set(String str);
		String toString();
		void add(BigNumber num);
	}

	class BigNumberClass implements BigNumber {
		private final int MaxDigitCount = 100;
		private final int NotError = 0;
		private final int ArithmeticsOverflow = 1;
		private final int InvalidData = 2;

		private int[] digits = new int[MaxDigitCount];
		private int count = 0;
		private int error = NotError;

		public BigNumberClass() {
			set("0");
		}
		public BigNumberClass(String str) {
			set(str);
		}
		
		public int[] getDigits() { return digits; };
		public int getCount() { return count; };
		
		public void set(String str) {
			for (int i=0; i < MaxDigitCount; i++) { digits[i] = 0; }

			int length = str.length();
			if (length > MaxDigitCount) {
				error = ArithmeticsOverflow;
				return;
			}
			try {
				for(int i = length - 1; i >= 0; i--) {
					digits[length - i - 1] = Integer.parseInt("" + str.charAt(i));
				}
				count = length;
			} catch (NumberFormatException e) {
				error = InvalidData;
				return;
			}
		}
		
		public String toString() {
			String result = "";
			for(int i = count - 1; i >= 0; i--) {
				result += new String(Integer.toString(digits[i]));
			}
			return result;
		}
		
		public void add(BigNumber num) {
			int z = 0;
			int maxCount = Math.max(count, num.getCount());
			int[] numDigits = num.getDigits();
			
			for(int i = 0; i < maxCount; i++) {
				int value = digits[i] + numDigits[i] + z;
				digits[i] = value % 10;
				z = Math.max(0, value / 10);
			}
			
			count = maxCount;
			
			if (z > 0) {
				if (maxCount + 1 > MaxDigitCount) {
					error = ArithmeticsOverflow;
					return;
				} else {
					digits[maxCount] = z;
					count = maxCount + 1;
				}
			}
		}
		public String getError() {
			if (ArithmeticsOverflow == error) {
				return "arithmetics overflow";
			} else if (InvalidData == error) {
				return "invalid string";
			} else {
				return null;
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		solver s = new solver();
		s.solve();
	}
	
	public void solve() throws FileNotFoundException, IOException {
		BigNumberClass number = new BigNumberClass();
		
		Scanner sc = new Scanner(new FileInputStream("input.txt"));
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
        while(sc.hasNextLine()) {
        	BigNumberClass current = new BigNumberClass(sc.nextLine());
        	if (null != current.getError()) {
                wr.write(current.getError()); wr.flush();
                return;
        	}
        	number.add(current);
        	if (null != number.getError()) {
                wr.write(number.getError()); wr.flush();
                return;
        	}
        }
        wr.write(number.toString());
        wr.flush();
	}
}
