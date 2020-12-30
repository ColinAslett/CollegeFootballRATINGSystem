import java.util.ArrayList;

public class PageRank {
	ArrayList<Data> data_list = new ArrayList<>();
	ArrayList<String> t_list = new ArrayList<>();
	ArrayList<Team> res_list = new ArrayList<>();
	DiGraph D;
	//Constants for PageRank
	double DAMP = .9;
	public PageRank(ArrayList<Data> data_List) {
		data_list = data_List;
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
		//determining number of outlinks each team has
		int out_links = 0;
		for(int i = 0;i < D.V;i++){
			out_links = 0;
			for(int a = 0;a < D.V;a++){
				if(a != i){
					if(D.adj(a).contains(i)){
						out_links++;
					}
				}
			}
			Team t = new Team(t_list.get(i),out_links);
			res_list.add(t);
		}
		//Algorithm
		int t = 0;
		double e = .01;
		double PR_0 = 0.0;
		double PR_1 = 0.0;
		while(true){
			//End Condition
			if(t == 0){
				PR_0 = PR(t);
				PR_1 = PR(t+1);
			}
			else{
				PR_0 = PR_1;
				PR_1 = PR(t+1);
			}
			if(Math.abs(PR_1 - PR_0) <= e){
				//System.out.println(Math.abs(PR_1 - PR_0));
				break;
			}
			//System.out.println(Math.abs(PR_1 - PR_0));
			t++;
			if(t == 100){
				break;
			}
		}
		//Print Results
		for(int i = 0;i < res_list.size();i++){
			System.out.println(res_list.get(i).name + "," + res_list.get(i).score);
		}
	}
	public double PR(int t){
		if(t == 0){
			for(int i = 0;i < res_list.size();i++){
				res_list.get(i).score = (1/D.V);
				res_list.get(i).new_score = (1/D.V);
			}
			for(int i = 0;i < res_list.size();i++){
				res_list.get(i).score = res_list.get(i).new_score;
			}
			return 1.0;
		}
		else{
			double sum = 0.0;
			double f = (1.0-DAMP)/D.V;
			for(int i = 0;i < D.V;i++){
				double x = 0.0;
				for(int a = 0;a < D.adj(i).size();a++){
					if(res_list.get(D.adj(i).get(a)).links_outward > 0){
						x += (res_list.get(D.adj(i).get(a)).score / res_list.get(D.adj(i).get(a)).links_outward);
					}
				}
				//System.out.println(x);
				double s = (f + (DAMP*x));
				res_list.get(i).new_score = s;
				sum += s;
			}
			for(int i = 0;i < res_list.size();i++){
				res_list.get(i).score = res_list.get(i).new_score;
			}
			return sum;
		}
	}
}
class Team{
	String name = "";
	int links_outward = 0;
	double score = 0.0;//t
	double new_score = 0.0;//t+1
	public Team(String n, int l_o){
		name = n;
		links_outward = l_o;
	}
}
