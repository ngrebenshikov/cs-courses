import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class solver {
	class Town {
		public int cost;
		public int distance;
		public int minCost;
		
		public Town(int cost, int distance) {
			this.cost = cost;
			this.distance = distance;
			minCost = Integer.MAX_VALUE;
		}
	}
	
	public void solve() throws IOException, Exception {
		Scanner sc = new Scanner(System.in);
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = sc.nextInt();
		int V = sc.nextInt();
		int L = sc.nextInt();
		
		Town towns[] = new Town[N];
		
		for (int i=0; i < N; i++) {
			towns[i] = new Town(sc.nextInt(), sc.nextInt());
		}

		towns[0].minCost = V*towns[0].cost;

		for (int i=1; i<N; i++) {
			int j=i-1;
			while (j >= 0 && (towns[i].distance - towns[j].distance) * L <= V ) {
				int curCost = towns[j].minCost + (towns[i].distance - towns[j].distance)*L*towns[i].cost;
				if (towns[i].minCost > curCost) {
					towns[i].minCost = curCost;
				}
				j--;
			}
		}
		int j=N-2;
		int result = Integer.MAX_VALUE;
		while (j >= 0 && (towns[N-1].distance - towns[j].distance) * L <= V ) {
			if (result > towns[j].minCost) {
				result = towns[j].minCost;
			}
			j--;
		}
		
		if (result < Integer.MAX_VALUE) {
			wr.write(result+"");
		} else {
			wr.write("-1");
		}
        wr.flush();
	}
	
	public static void main(String[] args) throws IOException, Exception {
		solver s = new solver();
		s.solve();
	}
}
