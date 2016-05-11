import java.io.*;


public class checker {
	final static int WrongAnswer = 0xAB; 
	final static int Accepted = 0xAC; 
	final static int PresentationError = 0xAA; 
	final static int BadCheckerOrInput = 0xA3; 

	public static void main(String[] args) {
		try {
			if (!check(
					new FileInputStream(new File("input.txt")),
					new FileInputStream(new File("pattern.txt")),
					new FileInputStream(new File("output.txt"))
					)) {
				System.exit(WrongAnswer);
			} else {
				System.exit(Accepted);
			}
		} catch(Exception e) {
			System.exit(WrongAnswer);
		}
	}
	
	public static boolean check(InputStream input,InputStream ans,InputStream res) throws IOException {
		BufferedReader
			r=new BufferedReader(new InputStreamReader(res)),
			a=new BufferedReader(new InputStreamReader(ans));
		for(;;) {
			String sr=r.readLine(),sa=a.readLine();
			if(sr==null && sa==null) return true;
			else if(sr==null) {
				while(sa!=null && sa.trim().length()==0) sa=a.readLine();
				return sa==null;
			} else if(sa==null) {
				while(sr!=null && sr.trim().length()==0) sr=r.readLine();
				return sr==null;
			}
			sa=sa.trim();sr=sr.trim();
			if(!sa.equals(sr)) return false;
		}
	}
}
