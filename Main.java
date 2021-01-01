import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
	Algo A;
	PageRank P;
	ArrayList<Data> Data_List = new ArrayList<>();
	public Main() throws Exception{
		readData("C:\\Users\\colin\\SwimProject\\Graph_CFB\\src\\data.txt");
		//A = new Algo(Data_List);
		P = new PageRank(Data_List);
	}
	public static void main(String[] args)  throws Exception{
		Main m = new Main();
	}
	//Reads a txt file
	private void readData(String file) throws Exception{
		BufferedReader sc = new BufferedReader(new FileReader(file));
        String temp = sc.readLine();
        int count = 0;
        while(temp != null){
            if(count != 0){
                String[] arrStr = temp.split(",",0);
                if(file.equals("C:\\Users\\colin\\Documents\\NetBeansProjects\\BetterCollegeFootballRankings\\src\\2000Reg.txt") || 
                        file.equals("C:\\Users\\colin\\Documents\\NetBeansProjects\\BetterCollegeFootballRankings\\src\\2001Reg.txt") || file.equals("C:\\Users\\colin\\Documents\\NetBeansProjects\\BetterCollegeFootballRankings\\src\\2000Post.txt")){
                    Data d = new Data(arrStr,12,14,18,20);
                    Data_List.add(d);
                }
                else if(file.equals("C:\\Users\\colin\\Documents\\NetBeansProjects\\BetterCollegeFootballRankings\\src\\2009Post.txt")){
                    Data d = new Data(arrStr,12,14,23,25);
                    if(d.delete == false){
                        Data_List.add(d);    
                    }
                }
                else{
                    Data d = new Data(arrStr,12,14,21,23);
                    if(d.delete == false){
                        Data_List.add(d);
                    }
                }
            }
            //System.out.println(Data_List.size());
            count++;
            temp = sc.readLine();
        }    
	}
}
