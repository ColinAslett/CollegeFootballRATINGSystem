import java.util.Arrays;

public class Data {
	String homeT,awayT;
	int homeP,awayP;
	String winner, loser;
	String DM_W,week;
	int P5_dif = 0;
	
	String[] P5 = {"Oregon","Oregon State","Washington","Washington State","Arizona State","Arizona",
			"USC","Utah","UCLA","Stanford","California","Colorado","Clemson","Boston College","North Carolina","FSU","Louisville","NC State","Notre Dame",
			"Syracuse","Wake Forest","Duke","Georgia Tech","Miami","Pittsburgh","Virginia","Virginia Tech",
			"Florida","Georgia","Kentucky","Missouri","South Carolina","Tennessee","Vanderbilt","Alabama",
			"Arkansas","Auburn","LSU","Ole Miss","Mississippi State","Texas A&M",
			"Indiana","Maryland","Michigan","Michigan State","Ohio State","Penn State","Rutgers","Illinois",
			"Iowa","Nebraska","Minnesota","Wisconsin","Northwestern","Purdue",
			"Baylor","Iowa State","Kansas","Kansas State","Oklahoma","Oklahoma State","TCU","Texas",
			"Texas Tech","West Virginia"};
	public Data(String ht,String hp,String at,String ap,String w) {
		week = w;
		if(Arrays.stream(P5).anyMatch(ht::equals)) {
			P5_dif += 1;
		}
		if(Arrays.stream(P5).anyMatch(at::equals)) {
			P5_dif -= 1;
		}
		homeT = ht;
		awayT = at;
		homeP = Integer.parseInt(hp);
		awayP = Integer.parseInt(ap);
		//Interpret Winner/Loser
		if(homeP > awayP) {
			winner = homeT;
			loser = awayT;
			DM_W = "HOME";
		}
		else {
			winner = awayT;
			loser = homeT;
			DM_W = "AWAY";
		}
	}
}
