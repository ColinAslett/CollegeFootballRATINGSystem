import java.util.ArrayList;

public class Glicko {
	public Glicko(ArrayList<Team> team_List) {
		T t = new T("A",1500,350);
		T t2 = new T("B",1700,300);
		t.result(t2,1);//first argument is the team they played, second argument is whether t won or lost(1/0 respectively)
	}

}
class T{
	double system_constant = .5;//between .3 and 1.2
	String name;
	//old metrics
	double rating = 1500;
	double RD = 350;
	double vol = .06;//
	//new metrics
	double n_r;
	double n_RD;
	//d
	double BB = 0.0;
	public T(String n,double r,double rd){
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
			Iteration(delta,v,a,1);
			B = BB;
		}
		//Step 3 and 4 of the Iterative process
		double e = .000001;
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
		//Step 6 overall
		double RD_apo = Math.sqrt(Math.pow(n_RD,2)+Math.pow(Math.exp(A/2),2));
		//Step 7 overall
		n_RD = 1 / Math.sqrt(1/Math.pow(RD_apo, 2) + 1/v);
		n_r = n_r + Math.pow(n_RD, 2)*(g_omega * (res - E));
		rating = 173.7178*n_r + 1500;
		RD = 173.7178*n_RD;
		System.out.println(rating + " , " + RD);
	}
	private void Iteration(double delta,double v, double a, double K){
		if(main_func(a-(K*system_constant),delta,v,a) < 0){
			Iteration(delta,v,a,K+1);
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
		return ret = f_frac - s_frac;
	}
}
