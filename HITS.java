import java.util.ArrayList;
import java.util.Collections;

public class HITS {
	//Algorithm Link: https://en.wikipedia.org/wiki/HITS_algorithm
	ArrayList<HITS_TEAM> h_list = new ArrayList<>();
	//Constants
	int STEP_LIMIT = 1000;//1000 Seems good, 10000 doesn't seem to change much
	public HITS(ArrayList<Data> game_List, ArrayList<Team> team_List) {
		//Generating a new Team List
		for(int i = 0;i < team_List.size();i++) {
			h_list.add(new HITS_TEAM(team_List.get(i).name));
		}
		//Generating Opponent List
		for(int i = 0;i < game_List.size();i++) {
			String w = game_List.get(i).winner;
			String l = game_List.get(i).loser;
			for(int a = 0;a < h_list.size();a++) {
				if(h_list.get(a).name.equals(w)) {
					for(int z = 0;z < h_list.size();z++) {
						if(h_list.get(z).name.equals(l)) {
							h_list.get(a).opp_list.add(h_list.get(z));
							break;
						}
					}
					h_list.get(a).wl_list.add(1);
				}
				else if(h_list.get(a).name.equals(l)) {
					for(int z = 0;z < h_list.size();z++) {
						if(h_list.get(z).name.equals(w)) {
							h_list.get(a).opp_list.add(h_list.get(z));
							break;
						}
					}
					h_list.get(a).wl_list.add(0);
				}
			}
		}
		//Actual Algorithm
		for(int i = 0;i < STEP_LIMIT;i++) {
			double norm = 0.0;
			for(int a = 0;a < h_list.size();a++) {
				h_list.get(a).auth = 0;
				for(int z = 0;z < h_list.get(a).wl_list.size();z++) {
					if(h_list.get(a).wl_list.get(z) == 1) {
						h_list.get(a).auth += h_list.get(a).opp_list.get(z).hub;
					}
				}
				norm += Math.pow(h_list.get(a).auth, 2);
			}
			norm = Math.sqrt(norm);
			for(int a = 0;a < h_list.size();a++) {
				h_list.get(a).auth = h_list.get(a).auth/norm;
			}
			norm = 0;
			for(int a = 0;a < h_list.size();a++) {
				h_list.get(a).hub = 0;
				for(int z = 0;z < h_list.get(a).wl_list.size();z++) {
					if(h_list.get(a).wl_list.get(z) == 0) {
						h_list.get(a).hub += h_list.get(a).opp_list.get(z).auth;
					}
				}
				norm += Math.pow(h_list.get(a).hub, 2);
			}	
			norm = Math.sqrt(norm);
			for(int a = 0;a < h_list.size();a++) {
				h_list.get(a).hub = h_list.get(a).hub/norm;
			}
		}
		
		//Printout
		for(int i = 0;i < h_list.size();i++) {			
			for(int a = 0;a < h_list.size();a++) {
				if(h_list.get(i).auth > h_list.get(a).auth) {
					Collections.swap(h_list,i,a);
				}
			}
		}
		for(int i = 0;i < h_list.size();i++) {			
			System.out.println("TEAM NAME,TEAM AUTH,TEAM HUB");
			System.out.println(h_list.get(i).name + " , " + h_list.get(i).auth*1000 + " , " + h_list.get(i).hub*1000);
		}
	}

}
class HITS_TEAM{
	String name;
	double auth = 1.0;
	double hub = 1.0;
	ArrayList<HITS_TEAM> opp_list = new ArrayList<>();
	ArrayList<Integer> wl_list = new ArrayList<>();
	public HITS_TEAM(String n) {
		name = n;
	}
}
