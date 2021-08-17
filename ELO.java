import java.util.ArrayList;

public class ELO {
	ArrayList<Data> dl;
	ArrayList<Team> tl;
	//Global K-Values
	int K_Value = 100;
	public ELO(ArrayList<Data> d,ArrayList<Team> t) {
		dl = d;
		tl = t;
		calculateELO();
	}
	
	private void calculateELO(){
		String winner,loser;
		Team winnerT, loserT;
		int w_id = 0, l_id = 0;
		for(int i = 0;i < dl.size();i++) {
			
			//OutPut for CSV of DataScience
			double h_elo = 0;
			double a_elo = 0;
			int ho = 0,aw = 0;
			
			
			
			winner = dl.get(i).winner;
			loser = dl.get(i).loser;
			winnerT = null;
			loserT = null;
			for(int a = 0;a < tl.size();a++) {
				if(tl.get(a).name.equals(winner)) {
					winnerT = tl.get(a);
					w_id = a;
					//Data Science
					if(winner.equals(dl.get(i).homeT)) {
						h_elo = tl.get(a).ELO;
						ho = a;
					}
					else if(winner.equals(dl.get(i).awayT)) {
						a_elo = tl.get(a).ELO;
						aw = a;
					}
				}
				else if(tl.get(a).name.equals(loser)) {
					loserT = tl.get(a);
					l_id = a;
					//Data Science
					if(loser.equals(dl.get(i).homeT)) {
						h_elo = tl.get(a).ELO;
						ho = a;
					}
					else if(loser.equals(dl.get(i).awayT)) {
						a_elo = tl.get(a).ELO;
						aw = a;
					}
				}
			}
			
			//Data Science Output
			int w_diff = (tl.get(ho).W-tl.get(aw).W);
			int l_diff = (tl.get(ho).L-tl.get(aw).L);
			if(winnerT.ELO != 1500 && loserT.ELO != 1500) {
				System.out.println(dl.get(i).DM_W+","+dl.get(i).P5_dif+","+ w_diff +","+ l_diff +"," +(h_elo-a_elo)+","+
						dl.get(i).week);
			}
			
			
			//ELO Calculation
			//First we Transform rating
			double winnerTransElo = Math.pow(10,winnerT.ELO/400);
			double loserTransElo = Math.pow(10,loserT.ELO/400);
			//Next we get expected score
			double winnerExpected = winnerTransElo/(winnerTransElo+loserTransElo);
			double loserExpected = loserTransElo/(winnerTransElo+loserTransElo);
			//Updated Elo
			double newWinnerElo = winnerT.ELO + (K_Value*(1.0-winnerExpected));
			double newLoserElo = loserT.ELO + (K_Value*(0.0-loserExpected));
			tl.get(w_id).ELO = newWinnerElo;
			tl.get(l_id).ELO = newLoserElo;
			tl.get(w_id).W = tl.get(w_id).W+1;
			tl.get(l_id).L = tl.get(w_id).L+1;
		}
	}
}
