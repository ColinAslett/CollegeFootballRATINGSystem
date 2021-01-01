
public class Data {
    String[] fullData;
    int t1_index , t2_index;
    int p1_index , p2_index;
    String team1 , team2;
    int point1 , point2;
    String winner = "" , loser = "";
    boolean team1winner = false;
    boolean delete = false;
    public Data(String[] arr,int team1_index,int point1_index,int team2_index,int point2_index){
        fullData = arr;
        t1_index = team1_index;
        t2_index = team2_index;
        p1_index = point1_index;
        p2_index = point2_index;
        if(fullData[p1_index].equals("") || fullData[p2_index].equals("")){
            delete = true;
        }
        else{
            //correctly finding the data
            team1 = fullData[team1_index];
            team2 = fullData[team2_index];
            point1 = Integer.parseInt(fullData[p1_index]);
            point2 = Integer.parseInt(fullData[p2_index]);
            //finding winner and loser
            if(point1 > point2 ){
                winner = team1;
                loser = team2;
                team1winner = true;
            }
            else if(point1 < point2){
                winner = team2;
                loser = team1;
            }
        }
    }
}
