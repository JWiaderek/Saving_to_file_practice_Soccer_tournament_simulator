import java.time.LocalDate;
import java.util.Random;

public class Match {
    int id;
    String phase;
    Team team_1, team_2;
    LocalDate date;
    int team_1_goals, team_2_goals;
    Team winner;
    Team loser;

    Match(int i, String p, Team t1, Team t2, LocalDate d){
        id =i;
        phase = p;
        team_1 = t1;
        team_2 = t2;
        date = d;
        team_1_goals = 0;
        team_2_goals = 0;
        winner = loser = null;
    }

    void play(){
        Random rand = new Random();
        team_1_goals = rand.nextInt(4);
        team_2_goals = rand.nextInt(4);

        team_1.goals_scored += team_1_goals;
        team_2.goals_scored += team_2_goals;

        team_1.goals_lost += team_2_goals;
        team_2.goals_lost += team_1_goals;

        if(team_1_goals > team_2_goals){
            winner = team_1;
            team_1.wins += 1;
            team_1.points += 3;

            loser = team_2;
            team_2.loses += 1;
        } else if (team_2_goals > team_1_goals) {
            winner = team_2;

            team_2.points += 3;

            loser = team_1;
            team_1.loses += 1;
        }else {
            if(!phase.substring(0, 3).equals("gru")){
                int i = rand.nextInt(2);
                if (i == 0){
                    winner = team_1;
                    team_1.wins += 1;
                    team_1.points += 3;

                    loser = team_2;
                    team_2.loses += 1;
                }else {
                    winner = team_2;
                    team_2.wins += 1;
                    team_2.points += 3;

                    loser = team_1;
                    team_1.loses += 1;
                }
            }else{
                winner = null;
                team_1.points += 1;
                team_1.draws += 1;

                team_2.points += 1;
                team_2.draws += 1;
            }
        }
    }
}

