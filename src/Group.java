import java.util.ArrayList;

public class Group {
    String name;
    ArrayList<Team> teams;
    Team firstPlace;
    Team secondPlace;

    Group(String n){
        teams = new ArrayList<>();
        name = n;
        firstPlace = null;
        secondPlace = null;
    }
    
    void calculateWinners() {
        boolean changed;
        do {
            changed = false;
            for (int i = 0; i < teams.size() - 1; i++) {
                Team current = teams.get(i);
                Team next = teams.get(i + 1);
                if (current.points > next.points) {
                    teams.set(i, next);
                    teams.set(i + 1, current);
                    changed = true;
                } else if (current.points == next.points) {
                    if(current.goals_scored - current.goals_lost > next.goals_scored - next.goals_lost){
                        teams.set(i, next);
                        teams.set(i+1, current);
                        changed = true;
                    } else if (current.goals_scored - current.goals_lost == next.goals_scored - next.goals_lost) {
                        if(current.goals_scored > next.goals_scored){
                            teams.set(i, next);
                            teams.set(i+1, current);
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);
        firstPlace = teams.get(teams.size() - 1);
        secondPlace = teams.get(teams.size() - 2);
    }
}

