import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Main {
    public static void teamPlacement(ArrayList<Team> arr){
        boolean changed;
        do{
            changed = false;
            for (int i=0; i< arr.size()-1; i++){
                Team current = arr.get(i);
                Team next = arr.get(i+1);
                if(current.points > next.points) {
                    arr.set(i, next);
                    arr.set(i+1, current);
                    changed = true;
                } else if (current.points == next.points) {
                    if(current.goals_scored - current.goals_lost > next.goals_scored - next.goals_lost){
                        arr.set(i, next);
                        arr.set(i+1, current);
                        changed = true;
                    } else if (current.goals_scored - current.goals_lost == next.goals_scored - next.goals_lost) {
                        if(current.goals_scored > next.goals_scored){
                            arr.set(i, next);
                            arr.set(i+1, current);
                            changed = true;
                        }
                    }
                }
            }
        }while(changed);
        Collections.reverse(arr);
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> group_names = new ArrayList<>();
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Match> matches = new ArrayList<>();
        ArrayList<Team> first_phase = new ArrayList<>();
        ArrayList<Team> second_phase = new ArrayList<>();
        ArrayList<Team> third_phase = new ArrayList<>();
        ArrayList<Team> placements = new ArrayList<>();
        boolean groups_calculated = false;
        boolean found = false;
        boolean phase_1_written = false, phase_2_written = false, phase_3_written = false, phase_4_written = false, phase_5_written = false, phase_6_written = false;
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        PrintWriter writer = new PrintWriter("wyniki.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader("mecze.txt"))){
            String line = reader.readLine();
            while(line != null){
                sb.append(line);
                String[] data = sb.toString().split(";");
                int id =Integer.parseInt(data[0]);
                String phase = data[1];
                LocalDate date = LocalDate.parse(data[4], dateTimeFormatter);
                Team t1 = null,t2 = null;
                switch (phase.substring(0, 3)) {
                    case "gru" -> {
                        if (!phase_1_written) {
                            writer.println("---------------");
                            writer.println("faza grupowa");
                            writer.println("---------------");
                            phase_1_written = true;
                        }
                        t1 = new Team(data[2]);
                        t2 = new Team(data[3]);
                        String group_name = data[5];
                        if (group_names.contains(group_name)) {
                            for (Group group : groups) {
                                if (Objects.equals(group.name, group_name)) {
                                    found = false;
                                    for (Team t : group.teams) {
                                        if (Objects.equals(t.name, t1.name)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        group.teams.add(t1);
                                    }

                                    found = false;

                                    for (Team t : group.teams) {
                                        if (Objects.equals(t.name, t2.name)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        group.teams.add(t2);
                                    }
                                }
                            }
                        } else {
                            group_names.add(group_name);
                            Group g = new Group(group_name);
                            g.teams.add(t1);
                            g.teams.add(t2);
                            groups.add(g);
                        }
                    }
                    case "1/8" -> {
                        if (!phase_2_written) {
                            writer.println("---------------");
                            writer.println("1/8 finalu");
                            writer.println("---------------");
                            phase_2_written = true;
                        }
                        if (!groups_calculated) {
                            for (Group g : groups) {
                                g.calculateWinners();
                                for (int x = 0; x < g.teams.size() - 2; x++) {
                                    first_phase.add(g.teams.get(x));
                                }
                            }
                            groups_calculated = true;
                        }
                        String name1 = String.valueOf(data[2].charAt(0));
                        String name2 = String.valueOf(data[3].charAt(0));
                        String place1 = String.valueOf(data[2].charAt(1));
                        String place2 = String.valueOf(data[3].charAt(1));
                        for (Group g : groups) {
                            if (Objects.equals(g.name, name1)) {
                                if (place1.equals("1")) {
                                    t1 = g.firstPlace;
                                    //System.out.println(t1.name);
                                } else {
                                    t1 = g.secondPlace;
                                    //.out.println(t1.name);
                                }
                            } else if (Objects.equals(g.name, name2)) {
                                if (place2.equals("1")) {
                                    t2 = g.firstPlace;
                                    //System.out.println(t2.name);
                                } else {
                                    t2 = g.secondPlace;
                                    //System.out.println(t2.name);
                                }
                            }
                        }
                    }
                    case "O 3" -> {
                        if (!phase_5_written) {
                            writer.println("---------------");
                            writer.println("O 3 miejsce");
                            writer.println("---------------");
                            phase_5_written = true;
                        }
                        int id_1 = Integer.parseInt(data[2].substring(1));
                        int id_2 = Integer.parseInt(data[3].substring(1));
                        for (Match m : matches) {
                            if (m.id == id_1) {
                                t1 = m.loser;
                            } else if (m.id == id_2) {
                                t2 = m.loser;
                            }
                        }
                    }
                    default -> {
                        int id1 = Integer.parseInt(data[2]);
                        int id2 = Integer.parseInt(data[3]);
                        switch (phase.substring(0, 3)) {
                            case "1/4" -> {
                                for (Match m : matches) {
                                    if (m.id == id1) {
                                        t1 = m.winner;
                                        second_phase.add(m.loser);
                                    } else if (m.id == id2) {
                                        t2 = m.winner;
                                        second_phase.add(m.loser);
                                    }
                                }
                                if (!phase_3_written) {
                                    writer.println("---------------");
                                    writer.println("1/4 finalu");
                                    writer.println("---------------");
                                    phase_3_written = true;
                                }
                            }
                            case "Pof" -> {
                                for (Match m : matches) {
                                    if (m.id == id1) {
                                        t1 = m.winner;
                                    } else if (m.id == id2) {
                                        t2 = m.winner;
                                    }
                                }
                                if (!phase_4_written) {
                                    writer.println("---------------");
                                    writer.println("Polfinal");
                                    writer.println("---------------");
                                    phase_4_written = true;
                                }
                            }
                            case "Fin" -> {
                                for (Match m : matches) {
                                    if (m.id == id1) {
                                        t1 = m.winner;
                                    } else if (m.id == id2) {
                                        t2 = m.winner;
                                    }
                                }
                                if (!phase_6_written) {
                                    writer.println("---------------");
                                    writer.println("Final");
                                    writer.println("---------------");
                                    phase_6_written = true;
                                }
                            }
                        }
                    }
                }

                Match match = new Match(id,phase,t1,t2,date);
                matches.add(match);
                match.play();
                if(match.team_1_goals == match.team_2_goals && match.winner != null){
                    if(match.winner == match.team_1){
                        writer.println(match.team_1.name + " - " + match.team_2.name + " " +
                                match.team_1_goals + "(W):" + match.team_2_goals);
                    } else if (match.winner == match.team_2) {
                        writer.println(match.team_1.name + " - " + match.team_2.name + " " +
                                match.team_1_goals + ":(W)" + match.team_2_goals);
                    }
                }else{
                    writer.println(match.team_1.name + " - " + match.team_2.name + " " +
                            match.team_1_goals + ":" + match.team_2_goals);
                }
                sb.setLength(0);
                line = reader.readLine();
            }
            writer.println("---------------");
            writer.println("MISTRZ SWIATA: " + matches.get(matches.size()-1).winner.name);
            writer.close();

            teamPlacement(first_phase);
            teamPlacement(second_phase);
            teamPlacement(third_phase);

            placements.add(matches.get(matches.size()-1).winner);
            placements.add(matches.get(matches.size()-1).loser);
            placements.add(matches.get(matches.size()-2).winner);
            placements.add(matches.get(matches.size()-2).loser);
            placements.addAll(third_phase);
            placements.addAll(second_phase);
            placements.addAll(first_phase);

            for(Team t : placements){
                t.place = placements.indexOf(t)+1;
                //System.out.println(t.name+ " "+ t.place);
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        matches
                .stream()
                .filter(m -> m.id == matches.size()-1)
                .forEach(m -> System.out.println("Mistrz swiata strzelil: "+ m.winner.goals_scored + " bramek"));
        System.out.println("Mecze zakonczone remisem: ");
        matches
                .stream()
                .filter(m -> m.winner == null)
                .forEach(m -> System.out.println(m.team_1.name + " - " + m.team_2.name + "\n"));
        placements
                .stream()
                .filter(p -> Objects.equals(p.name, "Polska"))
                .forEach(Team::print);
    }
}