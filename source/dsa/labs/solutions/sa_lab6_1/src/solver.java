import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

interface Action<T> {
	void doAction(T o);
}

class Graph {
	public class NodeInfo {
		public static final int Cleared = 0;
		public static final int Checked = 1;
		public int data;
		public int checked;
		public int group = 0;
		
		public NodeInfo(int data) {
			this.data = data;
			checked = Cleared;
		}
	}
	
	private NodeInfo nodes[];
	private int arcs[][];
	private int pathes[][];
	private int count;
	private int maxNodesCount;

	public Graph(int maxNodesCount) {
		this.maxNodesCount = maxNodesCount;
		nodes = new NodeInfo[maxNodesCount];
		arcs =  new int[maxNodesCount][maxNodesCount];
		for (int i=0; i<maxNodesCount; i++)
			for (int j=0; j<maxNodesCount; j++) {
				arcs[i][j] = Integer.MAX_VALUE / 2;
			}
		pathes = null;
	}

	public void addArc(int from, int to, int data) {
		arcs[from][to] = data;
		addSingleNode(from);
		addSingleNode(to);
	}
	public void addSingleNode(int node) {
		if (nodes[node] == null) {
			nodes[node] = new NodeInfo(node);
			count++;
		}
	}
	
	public int getNodesCount() { return count; }

	/**
	 * 
	 * @param action
	 * @return number of groups
	 */
	public int dfs(Action<Integer> action) {
		int group = 0;
		for(int i = 0; i < maxNodesCount; i++) {
			if (null != nodes[i]) {
				if (nodes[i].checked != NodeInfo.Checked) {
					dfs(i, action, group);
					group++;
				}
			}
		}
		return group;
	}
	
	private void dfs(int node, Action<Integer> action, int group) {
		if (nodes[node].checked == NodeInfo.Checked) {
			return;
		}
		if (null != action) {
			action.doAction(node);
		}
		nodes[node].checked = NodeInfo.Checked;
		nodes[node].group = group;
		for(int i = 0; i < maxNodesCount; i++) {
			if (arcs[node][i] < Integer.MAX_VALUE / 2) {
				dfs(i, action, group);
			}
		}
	}
	
	public void floyd() {
		pathes = new int[count+1][count+1];
		for (int i=1; i<=count; i++)
			for (int j=0; j<=count; j++)
				pathes[i][j] = arcs[i][j];
		for (int j=1; j<=count; j++)
			pathes[j][j] = 0;
		for (int k=1; k<=count; k++)
			for (int i=1; i<=count; i++)
				for (int j=1; j<=count; j++)
					if (pathes[i][k] + pathes[k][j] < pathes[i][j]) {
						pathes[i][j] = pathes[i][k] + pathes[k][j]; 
					}
	}
	
	public int getMinimalPath(int from, int to) {
		return pathes[from][to];
	}
	
	public NodeInfo getNodeInfo(int node) {
		return nodes[node];
	}
}

class PrintAction implements Action<Integer> {
	public void doAction(Integer i) {
		System.out.print(i + " ");
	}
}

public class solver {
	public void solve() throws IOException, Exception {
		Scanner sc = new Scanner(System.in);
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = sc.nextInt();
		int M = sc.nextInt();
		
		Graph graph = new Graph(N+1);
		
		for (int i=0; i < M; i++) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			int r = sc.nextInt();
			graph.addArc(p, q, r);
			graph.addArc(q, p, r);
		}
		for (int i=1; i <= N; i++) {
			graph.addSingleNode(i);
		}
		
        int groupsNumber = graph.dfs(null);
		wr.write(groupsNumber + "\n");
        graph.floyd();
//		for (int i=1; i<=N; i++)
//			for (int j=1; j<=N; j++) {
//				wr.write("(" + i + ", " + j + ") = " + graph.getMinimalPath(i, j) + "\n");
//			}
		for (int group=0; group < groupsNumber; group++) {
	        int from = 1;
	        int to = 1;
	        int length = -1;
			for (int i=1; i<=N; i++) {
				Graph.NodeInfo fromNode = graph.getNodeInfo(i);
				if (null == fromNode || fromNode.group != group) continue;
				for (int j=1; j<=N; j++) {
					Graph.NodeInfo toNode = graph.getNodeInfo(j);
					if (null == toNode || toNode.group != group) continue;
					if (length < graph.getMinimalPath(i, j)) {
						from = i;
						to = j;
						length = graph.getMinimalPath(i, j);
					}
				}
			}
			wr.write(from + " " + to + " " + length + "\n");
        }
        
        wr.flush();
	}
	
	public static void main(String[] args) throws IOException, Exception {
		solver s = new solver();
		s.solve();
	}
}
