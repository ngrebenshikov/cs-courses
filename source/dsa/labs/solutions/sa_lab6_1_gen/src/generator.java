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

	private void genFullTest(int testNum, int vertexCount) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(vertexCount + " " + (vertexCount*vertexCount/2-vertexCount)+ "\n"); 

       	for(int i=1; i<=vertexCount; i++) {
           	for(int j=1; j<=vertexCount; j++) {
           		if (i == j) continue;
           		wr.write(i + " " + j + " "); 
            	wr.write(random.nextInt(Integer.MAX_VALUE/vertexCount) + "\n");
           	}
       	}
       	wr.close();
	}
	
	private void genTest(int testNum, int vertexCount, int edgeCount, int edgesForNode, int groups) throws FileNotFoundException, IOException {
       	BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input" + getNumberWithLeadingZeros(testNum, 2) + ".txt")));
    	
       	wr.write(vertexCount + " " + edgeCount+ "\n"); 

       	int nodes[] = new int[vertexCount+1];
       	for(int i=0; i<=vertexCount; i++)
      		nodes[i] = 0;
       	int nodeEdges[] = new int[vertexCount+1];
       	for(int i=0; i<=vertexCount; i++)
      		nodeEdges[i] = 0;
       	int edges[][] = new int[vertexCount+1][vertexCount+1];
       	for(int i=0; i<=vertexCount; i++) {
           	for(int j=0; j<=vertexCount; j++) {
           		edges[i][j] = 0;
           	}
       	}
       	

       	int group = 1;
       	nodes[1] = 1;
       	for (int i = 0; i < edgeCount; i++) {
       		int from = 0;
       		int to = 0;
       		for (int j=1; j <= vertexCount; j++) {
       			if (nodeEdges[j] >= edgesForNode) continue;
       			boolean b = false;
           		for (int k=1; k <= vertexCount; k++) {
           			if (j!=k && (nodes[j] == group || nodes[j] == 0) && (nodes[k] == group || nodes[k] == 0) && edges[j][k] == 0 && nodeEdges[k] < edgesForNode) {
           				from = j;
           				to = k;
           				b = true;
           				break;
           			}
           		}
           		if (b) break;
       		}
       		
       		wr.write(from + " " + to + " "); 
        	wr.write(random.nextInt(Integer.MAX_VALUE/vertexCount) + "\n");
        	
        	nodes[to] = group;
        	nodes[from] = group;
        	edges[from][to] = 1;
        	edges[to][from] = 1;
        	nodeEdges[from]++;
        	nodeEdges[to]++;
        	
        	if (i+1 % (edgeCount/groups) == 0) group++;
        }
       	
       	wr.close();
	}

	public void generate() throws FileNotFoundException, IOException {
		genTest(2, 10, 5, 2, 2);
		genTest(3, 10, 10, 3, 2);
		genTest(4, 100, 100, 3, 2);
		genTest(5, 1000, 1000, 100, 10);
		genTest(6, 1000, 50000, 500, 1);
		genFullTest(7, 1000);
		genTest(8, 1000, 20000, 1000, 100);
		genTest(9, 1000, 0, 1000, 100);
	}
}
