public class Team {
    String name,eliminated_phase;
    int points, goals_scored, goals_lost, place, wins, loses, draws;

    Team(String n){
        name = n;
        points = 0;
        goals_lost = 0;
        goals_scored = 0;
        wins = loses = draws = place = 0;
    }

    void print(){
        System.out.println("Name: " + name);
        System.out.println("Place: " + place);
        System.out.println("Goals scored: " + goals_scored);
        System.out.println("Goals lost: " + goals_lost);
        System.out.println("wins: " + wins);
        System.out.println("loses: " + loses);
        System.out.println("draws: " + draws);
    }
}
