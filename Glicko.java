import java.util.ArrayList;

public class Glicko {
	ArrayList<Data> dl;
	ArrayList<Team> tl;
	
	ArrayList<T> t_list = new ArrayList<>();
	public Glicko(ArrayList<Data> game_List, ArrayList<Team> team_List) {
		dl = game_List;
		tl = team_List;
		setupData();
	}
	
	private void setupData(){
		for(int i = 0;i < dl.size();i++) {
			if(t_list.size() == 0){
				T t1 = new T(dl.get(i).winner,1500,350);
				T t2 = new T(dl.get(i).loser,1500,350);
				t1.result(t2,1);
				t2.result(t1,0);
				t_list.add(t1);
				t_list.add(t2);
			}
			else {
				T t1 = new T(dl.get(i).winner,1500,350);
				T t2 = new T(dl.get(i).loser,1500,350);
				boolean one_found = false,two_found = false;
				for(int a = 0;a < t_list.size();a++) {
					if(t_list.get(a).name.equals(t1.name)) {
						t1 = t_list.get(a);
						one_found = true;
					}
					if(t_list.get(a).name.equals(t2.name)) {
						t2 = t_list.get(a);
						two_found = true;
					}
				}
				t1.result(t2, 1);
				t2.result(t1, 0);
				if(one_found == false) {
					t_list.add(t1);
				}
				if(two_found == false) {
					t_list.add(t2);
				}
			}
		}
	}
}
class T{
	double system_constant = .5;//between .3 and 1.2
	String name;
	//Strength of Schedule Stuff
	ArrayList<T> opp_list = new ArrayList<>();
	double SOS = 0;
	//old metrics
	double rating = 1500;
	double RD = 350;
	double vol = .06;//
	//new metrics
	double n_r;
	double n_RD;
	//d
	double BB = 0.0;
	public T(String n, double r, double rd) {
		name = n;
		rating = r;
		RD = rd;
		n_r = (rating-1500)/173.7178;
		n_RD = RD/173.7178;
	}
	
	public void result(T t2,int res) {
		//Step 3
		//computing the estimtated variance of the players ratings based only on game outcomes
		double g_omega = 1 / (Math.sqrt(1 + (3 * Math.pow(t2.n_RD,2))/Math.pow(Math.PI,2)));
		//E ^ X
		double E = 1 / (1 + Math.exp((g_omega*-1)*(n_r - t2.n_r)));
		double v = Math.pow((Math.pow(g_omega, 2) * E * (1-E)),-1);
		//Step 4, calculating the delta value
		double delta = v*(g_omega * (res - E));
		//Step 5, iteration process step 1 and 2 to find new RD
		double a = Math.log(Math.pow(vol,2));
		double A = a;
		double B;
		double K;
		if(Math.pow(delta, 2) > (Math.pow(n_RD, 2)+v)){
			B = Math.log(Math.pow(delta, 2) - Math.pow(n_RD, 2) - v);
		}
		else{
			Iteration(delta,v,a,1.0);
			B = BB;
		}
		//Step 3 and 4 of the Iterative process
		double e = .0000001;//5 zeroes but try messing around with this
		double C;
		int div_a = 1;
		while(Math.abs((B-A)) > e){
			C = A + (A-B)*(main_func(A,delta,v,a)/div_a)/(main_func(B,delta,v,a)-(main_func(A,delta,v,a)/div_a));
			if((main_func(C,delta,v,a)*main_func(B,delta,v,a)) < 0){
				A = B;
			}
			else{
				div_a = div_a*2;// or just 2, never bigger than that???
			}
			B = C;
			if(Math.abs(B-A) <= e){
				break;
			}
		}
		vol = Math.exp(A/2);
		//Step 6 overall
		double RD_apo = Math.sqrt(Math.pow(n_RD,2)+Math.pow(Math.exp(A/2),2));
		//Step 7 overall
		n_RD = 1 / Math.sqrt(1/Math.pow(RD_apo, 2) + 1/v);
		n_r = n_r + Math.pow(n_RD, 2)*(g_omega * (res - E));
		rating = 173.7178*n_r + 1500;
		RD = 173.7178*n_RD;		
	}
	private void Iteration(double delta,double v, double a, double K){
		if(main_func(a-(K*system_constant),delta,v,a) < 0){
			Iteration(delta,v,a,K+1.0);
		}
		else{
			BB = (a - (K*system_constant));
		}
	}
	private double main_func(double x, double delta, double v, double a){
		double ret = 0;
		//first num and denom
		double f_num = Math.pow(Math.E, x) * (Math.pow(delta, 2) - Math.pow(n_RD, 2) - v - Math.pow(Math.E, x));
		double f_denom = 2 * Math.pow((Math.pow(n_RD, 2) + v + Math.pow(Math.E, x)),2);
		double f_frac = f_num / f_denom;
		//second fraction
		double s_frac = (x - a) / Math.pow(system_constant, 2);
		//subtract the fraction
		//System.out.println(f_frac - s_frac);
		ret = f_frac - s_frac;
		return ret;
	}
}
