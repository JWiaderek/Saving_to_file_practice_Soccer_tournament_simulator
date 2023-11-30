import java.util.ArrayList;

public class Mundial {
    public static final Mundial INSTANCE = new Mundial();

    public static ArrayList<Team> teams;
    public static ArrayList<Match> matches;

    private Mundial(){}

    public static Mundial getInstance(){
        return INSTANCE;
    }

    public static ArrayList<Team> getTeams(){
        return teams;
    }

    public static ArrayList<Match> getMatches(){
        return matches;
    }

    public static void setTeams(ArrayList<Team> t){
        teams = t;
    }

    public static void setMatches(ArrayList<Match> m){
        matches = m;
    }
}
