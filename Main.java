import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	ArrayList<Data> Game_List = new ArrayList<>();
	ArrayList<Team> Team_List = new ArrayList<>();
	ELO elo;
	Glicko glicko;
	BeatGraph beatG;
	PageRank pageR;
	HITS hits;
	public Main() throws Exception{
		read_csv("/Users/colinaslett/Desktop/CFB_2019_REG_DATA.csv");
		create_team_list();
		System.out.println("VICTOR,P5_DIFF,H_W_M_A_W,H_L_M_A_L,H_ELO_M_A_ELO,WEEK");
		elo = new ELO(Game_List,Team_List);
		//glicko = new Glicko(Game_List,Team_List);
		//beatG = new BeatGraph(Game_List,Team_List);
		//pageR = new PageRank(Game_List,Team_List);
		//hits = new HITS(Game_List,Team_List);
		//printELO();
		//printGlicko();
		//printBeatGraph();
		//printPageRank();
	}
	public static void main(String[] args) throws Exception{
		Main m = new Main();
	}
	
	private void read_csv(String F_Name) throws Exception{
		BufferedReader sc = new BufferedReader(new FileReader(F_Name));
		String temp = sc.readLine();
		int count = 0;
		while(temp != null) {
			if(count != 0) {
				String[] temp_array = temp.split(",",0);
				//HT = 12, HP = 14, AT = 21, AP = 23
				//Week Filter, this is useful only when doing rating system performance
				if(Integer.parseInt(temp_array[2]) >= 1) {
					Game_List.add(new Data(temp_array[12],temp_array[14],temp_array[21],temp_array[23],temp_array[2]));
				}
			}
			count++;
			temp = sc.readLine();
		}
	}
	
	private void create_team_list() {
		boolean homeT = false, awayT = false;
		for(int i = 0;i < Game_List.size();i++) {
			homeT = false;
			awayT = false;
			for(int a = 0;a < Team_List.size();a++) {
				if(Team_List.get(a).name.equals(Game_List.get(i).homeT)) {
					homeT = true;
				}
				if(Team_List.get(a).name.equals(Game_List.get(i).awayT)) {
					awayT = true;
				}
			}
			if(homeT == false) {
				Team_List.add(new Team(Game_List.get(i).homeT));
			}
			if(awayT == false) {
				Team_List.add(new Team(Game_List.get(i).awayT));
			}
		}
	}
	//Printing out the ELO Rating System
	private void printELO() {
		//Sort it, highest->lowest
		for(int i = 0;i < Team_List.size();i++) {
			for(int a = 0;a < Team_List.size();a++) {
				//if(Team_List.get(i).ELO > Team_List.get(a).ELO) {
					//Collections.swap(Team_List, i, a);
				//}
				//Sorted Alphabetically
				if((int)Team_List.get(i).name.charAt(0) < (int)Team_List.get(a).name.charAt(0)) {
					Collections.swap(Team_List, i, a);
				}
			}
		}
		for(int i = 0;i < Team_List.size();i++) {
			//System.out.println(Team_List.get(i).name + "," + Team_List.get(i).ELO);
			BigDecimal bd = new BigDecimal(Team_List.get(i).ELO).setScale(2,RoundingMode.HALF_UP);
			double r = bd.doubleValue();
			System.out.println(Team_List.get(i).name);
			System.out.println(r);
		}
	}
	//Prinotout of Glicko rating system
	private void printGlicko() {
		System.out.println("NAME,RATING,RATING DEVIATION");
		for(int i = 0;i < glicko.t_list.size();i++) {
			for(int a = 0;a < glicko.t_list.size();a++) {
				if(glicko.t_list.get(i).rating > glicko.t_list.get(a).rating) {
					Collections.swap(glicko.t_list, i, a);
				}
			}
		}
		for(int i = 0;i < glicko.t_list.size();i++){
			BigDecimal bd = new BigDecimal(glicko.t_list.get(i).rating).setScale(2,RoundingMode.HALF_UP);
			BigDecimal bd2 = new BigDecimal(glicko.t_list.get(i).RD).setScale(2,RoundingMode.HALF_UP);
			double avg = bd.doubleValue();
			double rd = bd2.doubleValue();
			//System.out.println(avg + " , " + rd);
			System.out.println(glicko.t_list.get(i).name);
			//System.out.println(glicko.t_list.get(i).name + "," + glicko.t_list.get(i).rating + "," + glicko.t_list.get(i).RD);
		}
	}
	//Printout of the BeatGraph Rating System
	
	private void printBeatGraph() {
		System.out.println("NAME,Score,Wins,Losses");
		for(int i = 0;i < beatG.result_list.size();i++){
			for(int a = 0;a < beatG.result_list.size();a++) {
				if((beatG.result_list.get(i).beatWins-beatG.result_list.get(i).beatLosses) > 
					(beatG.result_list.get(a).beatWins-beatG.result_list.get(a).beatLosses)) {
					Collections.swap(beatG.result_list, i, a);
				}
			}
		}
		for(int i = 0;i < beatG.result_list.size();i++) {
			//System.out.println(beatG.result_list.get(i).name);
			System.out.println(beatG.result_list.get(i).beatWins + "-" + beatG.result_list.get(i).beatLosses);
			//System.out.println(beatG.result_list.get(i).name + "," + (beatG.result_list.get(i).beatWins-beatG.result_list.get(i).beatLosses)
					//+ "," + beatG.result_list.get(i).beatWins + "," + beatG.result_list.get(i).beatLosses);
		}
	}
	//Printout of the PageRank Rating System
	private void printPageRank() {
		System.out.println("Name,Score");
		for(int i = 0;i < pageR.res_list.size();i++) {
			for(int a = 0;a < pageR.res_list.size();a++) {
				if(pageR.res_list.get(i).score > pageR.res_list.get(a).score) {
					Collections.swap(pageR.res_list, i, a);
				}
			}
		}
		for(int i = 0;i < pageR.res_list.size();i++) {
			//System.out.println(pageR.res_list.get(i).name);
			BigDecimal bd = new BigDecimal(pageR.res_list.get(i).score*10000).setScale(2,RoundingMode.HALF_UP);
			double r = bd.doubleValue();
			System.out.println(r);
			//System.out.println(pageR.res_list.get(i).name + "," + pageR.res_list.get(i).score*10000);
		}
	}
}
