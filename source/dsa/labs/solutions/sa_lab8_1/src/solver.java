import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class solver {

	int[][] delta;
	
	private void createDelta(String p) {
		delta = new int[p.length()+1][25];
		for (int i = 0; i < 25; i++) delta[0][i] = 0;
		for(int i = 0; i < p.length(); i++) {
			int j = delta[i][p.charAt(i) - 'a'];
			delta[i][p.charAt(i) - 'a'] = i+1;
			for (int k = 0; k < 25; k++) {
				delta[i+1][k] = delta[j][k];
			}
		}
	}
	
	public void solve() throws IOException, Exception {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String S = r.readLine();
		String P = r.readLine();
		
		int success = P.length();
		
		createDelta(P);

		int current = 0;
		for (int i = 0; i < S.length(); i++) {
			current = delta[current][S.charAt(i) - 'a'];
			if (current == success) {
				wr.write((i - P.length() + 2) + " ");
			}
		}
        wr.flush();
	}
	
	public static void main(String[] args) throws IOException, Exception {
		solver s = new solver();
		s.solve();
	}
}
