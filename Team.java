
public class Team {
	String name = "";
	double ELO,GLICKO,PAGE_RANK,BEAT_GRAPH;
	public Team(String t) {
		name = t;
		init_ELO();
	}
	private void init_ELO() {
		//Currently all teams start at 1500, but maybe we add a small
		//bias to P5 teams, have them start at 1750
		ELO = 1500;
	}
}
