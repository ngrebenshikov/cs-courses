import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class checker {
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
		public int getMaxNodesCount() { return maxNodesCount; }

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

	class solver {
		public Graph graph;
		public int groupsNumber;
		
		public void solve(Scanner sc) throws IOException, Exception {
			int N = sc.nextInt();
			int M = sc.nextInt();
			
			graph = new Graph(N+1);
			
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
			
	        groupsNumber = graph.dfs(null);
	        graph.floyd();
		}
		
		public int getMaxLength(int group) {
	        int from = 1;
	        int to = 1;
	        int length = -1;
			for (int i=1; i<=graph.getMaxNodesCount()-1; i++) {
				Graph.NodeInfo fromNode = graph.getNodeInfo(i);
				if (null == fromNode || fromNode.group != group) continue;
				for (int j=1; j<=graph.getMaxNodesCount()-1; j++) {
					Graph.NodeInfo toNode = graph.getNodeInfo(j);
					if (null == toNode || toNode.group != group) continue;
					if (length < graph.getMinimalPath(i, j)) {
						from = i;
						to = j;
						length = graph.getMinimalPath(i, j);
					}
				}
			}
			return length;
		}
		
		public boolean isInOneGroup(int from, int to) {
			Graph.NodeInfo fromNode = graph.getNodeInfo(from);
			Graph.NodeInfo toNode = graph.getNodeInfo(to);
			if (null != fromNode && null != toNode && fromNode.group == toNode.group) {
				return true;
			} else {
				return false;
			}
		}
		
	}

	final static int WrongAnswer = 0xAB; 
	final static int Accepted = 0xAC; 
	final static int PresentationError = 0xAA; 
	final static int BadCheckerOrInput = 0xA3; 

	public static void main(String[] args) {
		checker c = new checker();
		try {
			if (!c.check(
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
	
	public boolean check(InputStream input,InputStream ans,InputStream res) throws IOException, Exception {
		Scanner resSc = new Scanner(res);
		
		solver s = new solver();
		s.solve(new Scanner(input));

		int groupsCount = resSc.nextInt();
		
		if (groupsCount != s.groupsNumber) return false;

		int groups[] = new int[groupsCount];
		for (int i=0; i < groupsCount; i++) groups[i]=0;
		
		for (int i=0; i < groupsCount; i++) {
			int from = resSc.nextInt();
			int to = resSc.nextInt();
			int length = resSc.nextInt();
			
			if (!s.isInOneGroup(from, to)) return false;
			int group = s.graph.getNodeInfo(from).group;
			
			if (groups[group] > 0) return false; //this group has been processed

			if (length != s.graph.getMinimalPath(from, to) || 
					length != s.getMaxLength(group)) return false;
			
			groups[group] = 1;
			
		}
		
		return true;
	}
}
