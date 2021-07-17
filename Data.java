
public class Data {
	String homeT,awayT;
	int homeP,awayP;
	String winner, loser;
	public Data(String ht,String hp,String at,String ap) {
		homeT = ht;
		awayT = at;
		homeP = Integer.parseInt(hp);
		awayP = Integer.parseInt(ap);
		//Interpret Winner/Loser
		if(homeP > awayP) {
			winner = homeT;
			loser = awayT;
		}
		else {
			winner = awayT;
			loser = homeT;
		}
	}
}
