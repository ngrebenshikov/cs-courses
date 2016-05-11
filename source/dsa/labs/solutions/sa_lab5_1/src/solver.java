import java.io.*;
import java.util.Scanner;

public class solver {
	
	int used[] = new int[3];
	boolean fin[] = new boolean[3];
	boolean fin1[] = new boolean[3];
	int current[][] = new int[3][2];

	private void getRecord(DataInputStream f1, DataInputStream f2, int i, int k) throws IOException {
		try {
			if (used[i] == k) {
				fin[i] = true;
			} else if (i == 1) {
				current[1][0] = f1.readInt();
				current[1][1] = f1.readInt();
				used[1]++;
			} else {
				current[2][0] = f2.readInt();
				current[2][1] = f2.readInt();
				used[2]++;
			}
		} catch (EOFException e) {
			fin[i] = true;
			fin1[i] = true;
		}
	}
	
	private void merge(int k, String fname1, String fname2, String gname1, String gname2) throws IOException {
		boolean outswitch = true;
		int winner;

		DataOutputStream g1 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(gname1)));
		DataOutputStream g2 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(gname2)));
		DataInputStream f1 = new DataInputStream(new BufferedInputStream(new FileInputStream(fname1)));
		DataInputStream f2 = new DataInputStream(new BufferedInputStream(new FileInputStream(fname2)));
		
		fin1[1]=false; fin1[2]=false;
		while (!fin1[1] || !fin1[2]) {
			used[1]=0; used[2]=0;
			fin[1]=false; fin[2]=false;
			getRecord(f1, f2, 1, k); getRecord(f1, f2, 2, k);
			while (!fin[1] || !fin[2]) {
				if (fin[1]) winner=2;
				else if (fin[2]) winner=1;
				else 
					if (current[1][0] < current[2][0]) winner=1;
					else winner=2;
				if (outswitch) {
					g1.writeInt(current[winner][0]);
					g1.writeInt(current[winner][1]);
				} else {
					g2.writeInt(current[winner][0]);
					g2.writeInt(current[winner][1]);
				}
				getRecord(f1, f2, winner, k);
			}
			outswitch = !outswitch;
		}
		
		f1.close();	f2.close();
		g1.close();	g2.close();
	}
	
	public void solve() throws IOException, Exception {
		
		String fnames[][] = new String[2][2];
		
		fnames[0][0] = "f1.txt";
		fnames[0][1] = "f2.txt";
		fnames[1][0] = "g1.txt";
		fnames[1][1] = "g2.txt";
		
		Scanner scIn = new Scanner(new BufferedInputStream(System.in));
		BufferedWriter wrOut = new BufferedWriter(new OutputStreamWriter(System.out));
		DataOutputStream wrTemp1 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fnames[0][0])));
		DataOutputStream wrTemp2 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fnames[0][1])));
		
		int N = scIn.nextInt();
		
		for (int i=0; i < N; i++) {
			int number = scIn.nextInt();
			int data = scIn.nextInt();
			if (i <= N/2) {
				wrTemp1.writeInt(number);
				wrTemp1.writeInt(data);
			} else {
				wrTemp2.writeInt(number);
				wrTemp2.writeInt(data);
			}
		}
		wrTemp1.close();
		wrTemp2.close();
		
		int k = 1;
		int nameIndex = 0;
		String fname1;
		String fname2;
		String gname1="";
		String gname2;
		while(k < 2*N) {
			if (nameIndex == 0) {
				fname1 = fnames[0][0];
				fname2 = fnames[0][1];
				gname1 = fnames[1][0];
				gname2 = fnames[1][1];
				nameIndex = 1;
			} else {
				fname1 = fnames[1][0];
				fname2 = fnames[1][1];
				gname1 = fnames[0][0];
				gname2 = fnames[0][1];
				nameIndex = 0;
			}
			merge(k, fname1, fname2, gname1, gname2);
			k *= 2;
		}

		DataInputStream res = new DataInputStream(new BufferedInputStream(new FileInputStream(gname1)));
		try {
			while(true) {
				res.readInt();
				wrOut.write(res.readInt() + "\n");
				wrOut.flush();
			}
		} catch (EOFException e){}
		
        wrOut.close();
	}
	
	public static void main(String[] args) throws IOException, Exception {
		solver s = new solver();
		s.solve();
	}
}
