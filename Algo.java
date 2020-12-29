import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Algo {
	ArrayList<Data> data_List = new ArrayList<>();
	DiGraph D;
	ArrayList<String> t_list = new ArrayList<>();
	int BEGINNING_EDGE_COUNT = 0;
	ArrayList<Result> result_list = new ArrayList<>();
	public Algo(ArrayList<Data> data_List2){
		data_List = data_List2;
		System.out.println(data_List.size());
		//Get all the teams and put them into the graph first
		for(int i = 0;i < data_List.size();i++){
			t_list.add(data_List.get(i).team1);
			t_list.add(data_List.get(i).team2);
		}
		ArrayList<String> new_team_list = new ArrayList<>();
		for(int i = 0;i < t_list.size();i++){
			if(new_team_list.size() == 0){
				new_team_list.add(t_list.get(i));
			}
			else{
				boolean found = false;
				for(int a = 0;a < new_team_list.size();a++){
					if(new_team_list.get(a).equals(t_list.get(i))){
						found = true;
					}
				}
				if(found == false){
					new_team_list.add(t_list.get(i));
				}
			}
		}
		t_list = new_team_list;
		D = new DiGraph(t_list);
		//now we can add edges
		for(int i = 0;i < data_List.size();i++){
			String t1 = data_List.get(i).winner;
			String t2 = data_List.get(i).loser;
			int i1 = -1 , i2 = -1;
			for(int a = 0;a < t_list.size();a++){
				if(t1.equals(t_list.get(a))){
					i1 = a;
				}
				else if(t2.equals(t_list.get(a))){
					i2 = a;
				}
			}
			D.addEdge(i1, i2);
		}
		BEGINNING_EDGE_COUNT = D.E;
		//Remove Some edges to reduce
		
		int t = 132;
		//System.out.println(t_list.get(t));
		DirectedDFS r = new DirectedDFS(D,t);
		for(int i =  0;i < r.order.size();i++){
			//System.out.println(t_list.get(r.order.get(i)) + " , " + r.order.get(i));
		}
		
		//ALGORITHM!!!!!!!!!!!!!!!
		//Set abitrarily to 100, though this should be adjusted for future use in case it doesn not work
		for(int i = 0;i < 100;i++){
			if(numCycles() > 0){
				ArrayList<Integer> s = len_cycles();
				breakLoop(s.get(0),s.get(1));
			}
			else{
				System.out.println("Num of Iterations: " + i);
				break;
			}
		}

		//Calculating results
		for(int i = 0;i < t_list.size();i++){
			Result res = new Result(t_list.get(i));
			result_list.add(res);
		}
		for(int i = 0;i < t_list.size();i++){
			DirectedDFS ra = new DirectedDFS(D,i);
			for(int a = 0;a < ra.order.size();a++){
				result_list.get(i).beatWins++;
				result_list.get(ra.order.get(a)).beatLosses++;
			}
		}
		//RESULT PRINTING
		System.out.println("ORIGINAL EDGES: " + BEGINNING_EDGE_COUNT + " , NEW EDGE COUNT AFTER ITERATIONS: " + D.E);
		System.out.println("EDGE LOSS: " + (1 - ((double)D.E / (double)BEGINNING_EDGE_COUNT))*100 + "%");
		printRankings();
		//printAdjacencyMatrix();
		//printDirectEdges();
	}
	//Breaking a Loop, takes in the id of the team and the number of elements that need to be deleted from its list
	public void breakLoop(int id,int num_del){
		DirectedDFS r = new DirectedDFS(D,id);
		//System.out.println(id + " . " + num_del);
		for(int i = 0;i < r.order.size();i++){
			if(i <= num_del){
				//System.out.println(r.order.size());
				D.removeEdge(id, r.order.get(i));
			}
		}
	}
	//Function that looks att the length of each loop/cycle
	public ArrayList<Integer> len_cycles(){
		//System.out.println("************************************");
		//System.out.println("************************************");
		//System.out.println("************************************");
		int shortest = 0;
		int shortest_id = 0;
		int counter = 0;
		boolean loop = false;
		for(int i = 0;i < t_list.size();i++){
			loop = false;
			DirectedDFS r = new DirectedDFS(D,i);
			counter = 0;
			for(int a = 0;a < r.order.size();a++){
				//System.out.print("        " + t_list.get(r.order.get(a)));
				if(i == r.order.get(a)){
					loop = true;
					break;
				}
				else{
					counter++;
				}
			}
			//System.out.println(" ");
			if(shortest == 0 && loop == true){
				shortest = counter;
				shortest_id = i;
			}
			else if(counter < shortest && counter > 0 && loop == true){
				shortest = counter;
				shortest_id = i;
			}
			//System.out.println(t_list.get(i) + ": " + counter + " Loop Len , ID" + i);
		}
		//System.out.println("Shortest ID: " + shortest_id + " , len of loop: " + shortest);
		ArrayList<Integer> s = new ArrayList<>();
		s.add(shortest_id);
		s.add(shortest);
		return s;
	}
	//Function determines how many teams have loops
	public Integer numCycles(){
		int num = 0;
		for(int i = 0;i < t_list.size();i++){
			DirectedDFS reachable = new DirectedDFS(D,i);
			for(int v = 0; v < D.V;v++){
				if(reachable.marked(v) && v == i){
					num++;
				}
			}
		}
		//System.out.println(num + " / " + t_list.size() + " , EDGES: " + D.E);
		return num;
	}
	//Print out only direct edges for each team 
	private void printDirectEdges(){
		//Printing out direct edges
		for(int i = 0;i < t_list.size();i++){
			System.out.println(t_list.get(i));
			for(int a = 0;a < D.adj(i).size();a++){
				System.out.println("       " +t_list.get(D.adj(i).get(a)));
			}
		}		
	}
	//Printing out the adjacency matrix for each team
	private void printAdjacencyMatrix(){
		//PRINTING OUT ADJCENCY MATRIX
		for(int i = 0;i < t_list.size();i++){
			DirectedDFS ra = new DirectedDFS(D,i);
			System.out.println(t_list.get(i));
			for(int a = 0;a < ra.order.size();a++){
				System.out.println("        " + t_list.get(ra.order.get(a)));
			}
		}		
	}
	//Prints out the ranking of each team, Score based
	private void printRankings(){
		for(int i = 0;i < result_list.size();i++){
			System.out.println(result_list.get(i).name + " , BeatWins: " + result_list.get(i).beatWins + " , BeatLosses: " + result_list.get(i).beatLosses);
		}
	}
}
//Result Class, each team gets one
class Result{
	String name;
	int beatWins = 0;
	int beatLosses = 0;
	public Result(String n){
		name = n;
	}
}
//This is the graph class
class DiGraph{
	//characteristics of graph
	int V = 0;//Number of Vertices
	int E = 0;//Number of edges
	
	private ArrayList<Integer>[] adj;
	public DiGraph(ArrayList<String> t){
		V = t.size();
		E = 0;
		adj = (ArrayList<Integer>[]) new ArrayList[V];
		for(int i = 0;i < V;i++){
			adj[i]  = new ArrayList<Integer>();
		}
	}
	//A->B
	public void addEdge(int A,int B){
		adj[A].add(B);
		E++;
	}
	//Removes an edge in a graph
	public void removeEdge(int A, int B){
		for(int i = 0;i < adj[A].size();i++){
			if(adj[A].get(i) == B){
				adj[A].remove(i);
				E--;
				break;
			}
		}
	}
	public ArrayList<Integer> adj(int a){
		return adj[a];
	}
	//Reverse if needed, pg 569
}
//This is the Depth First Search Class
class DirectedDFS{
	boolean[] marked;
	boolean first = true;
	//The order
	ArrayList<Integer> order = new ArrayList<>();
	public DirectedDFS(DiGraph d, int s){
		marked = new boolean[d.V];
		dfs(d,s);
	}
	private void dfs(DiGraph d, int v){
		if(first == false){
			marked[v] = true;
			order.add(v);
		}
		else{
			first = false;
		}
		for(int w : d.adj(v)){
			if(!marked[w]){
				dfs(d,w);
			}
		}
	}
	public boolean marked(int v){
		return marked[v];
	}
}
//Figure out whether there is a cycle in the graph
class DirectedCycle{
	boolean marked[];
	int[] edgeTo;
	Stack<Integer> cycle;
	boolean[] onStack;
	public DirectedCycle(DiGraph G){
		onStack = new boolean[G.V];
		edgeTo = new int[G.V];
		marked = new boolean[G.V];
		for(int i = 0;i < G.V;i++){
			if(!marked[i]){
				dfs(G,i);
			}
		}
	}
	private void dfs(DiGraph G, int v){
		onStack[v] = true;
		marked[v] = true;
		for(int w : G.adj(v)){
			if(this.hasCycle()){
				return;
			}
			else if(!marked[w]){
				edgeTo[w] = v;
				dfs(G,w);
			}
			else if(onStack[w]){
				cycle = new Stack<Integer>();
				for(int x = v;x != w;x = edgeTo[x]){
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
		onStack[v] = false;
	}
	public boolean hasCycle(){
		return cycle != null;
	}
	public Iterable<Integer> cycle(){
		return cycle;
	}
}
//Depth-First Search Ordering
class DepthFirstOrder{
	boolean[] marked;
	Queue<Integer> pre;
	Queue<Integer> post;
	Stack<Integer> reversepost;
	public DepthFirstOrder(DiGraph G){
		pre = new LinkedList<Integer>();
		post = new LinkedList<Integer>();
		reversepost = new Stack<Integer>();
		marked = new boolean[G.V];
		for(int v = 0;v < G.V;v++){
			if(!marked[v]){
				dfs(G,v);
			}
		}
	}
	private void dfs(DiGraph G, int v){
		pre.add(v);
		marked[v] = true;
		for(int w : G.adj(v)){
			if(!marked[w]){
				dfs(G,w);
			}
		}
		post.add(v);
		reversepost.push(v);
	}
	public Iterable<Integer> preorder(){
		return pre;
	}
	public Iterable<Integer> postorder(){
		return post;
	}
	public Iterable<Integer> reversePostorder(){
		return reversepost;
	}
}
//Topological Sort
class Topological{
	Iterable<Integer> order = new ArrayList<>();
	public Topological(DiGraph G){
		DirectedCycle cyclefinder = new DirectedCycle(G);
		if(!cyclefinder.hasCycle()){
			DepthFirstOrder dfs = new DepthFirstOrder(G);
			order = dfs.reversePostorder();
		}
	}
	public Iterable<Integer> order(){
		return order;
	}
	public boolean hasOrder(){
		return order != null;
	}
	
}
