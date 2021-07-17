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
			winner = dl.get(i).winner;
			loser = dl.get(i).loser;
			winnerT = null;
			loserT = null;
			for(int a = 0;a < tl.size();a++) {
				if(tl.get(a).name.equals(winner)) {
					winnerT = tl.get(a);
					w_id = a;
				}
				else if(tl.get(a).name.equals(loser)) {
					loserT = tl.get(a);
					l_id = a;
				}
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
		}
	}
}
