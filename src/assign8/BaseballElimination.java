package assign8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class uses the max-flow algorthim to solve the problem. 
 */
public class BaseballElimination {

    private final Map<String, Integer> teamNums;

    private final int[] wins;

    private final int[] remains;

    private final int[] losses;

    private final int[][] competitions;


    /**
     * @param filename
     */
    public BaseballElimination(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("no file name");
        }
        In in = new In(filename);
        int size = in.readInt();
        in.readLine();
        teamNums = new HashMap<>(size);
        wins = new int[size];
        losses = new int[size];
        remains = new int[size];
        competitions = new int[size][size];
        initData(in);
    }

    public int numberOfTeams() {
        return teamNums.size();
    }

    public Iterable<String> teams() {
        return teamNums.keySet();
    }

    public int wins(String team) {
        return wins[getTeamNums(team)];
    }

    public int losses(String team) {
        return losses[getTeamNums(team)];
    }

    public int remaining(String team) {
        return remains[getTeamNums(team)];
    }

    public int against(String team1, String team2) {
        return competitions[getTeamNums(team1)][getTeamNums(team2)];
    }

    public boolean isEliminated(String team) {
        int teamNum = getTeamNums(team);
        return !getCertificateteamNums(teamNum).isEmpty();
    }

    public Iterable<String> certificateOfElimination(String team) {
        int teamNum = getTeamNums(team);
        List<Integer> certificateteamNums = getCertificateteamNums(teamNum);
        if (certificateteamNums.isEmpty()) {
            return null;
        }
        List<String> certificateNames = new ArrayList<>();
        for (Entry<String, Integer> entry : teamNums.entrySet()) {
            if (certificateteamNums.contains(entry.getValue())) {
                certificateNames.add(entry.getKey());
            }
        }
        return certificateNames;
    }

    private void initData(In in) {
        int i = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            if (line == null || line.isEmpty()) {
                throw new IllegalArgumentException("input line is empty");
            }
            line = line.trim();
            String[] data = line.split("\\s+");
            if (data.length < 5) {
                throw new IllegalArgumentException("input line is invalid: " +i+"th line: "+ line);
            }
            teamNums.put(data[0], i);
            wins[i] = Integer.parseInt(data[1]);
            losses[i] = Integer.parseInt(data[2]);
            remains[i] = Integer.parseInt(data[3]);
            for (int j = 4; j < data.length; j++) {
                competitions[i][j-4] = Integer.parseInt(data[j]);
            }
            i++;
        }
    }

    private List<Integer> getCertificateteamNums(int x) {
        List<Integer> certificateteamNums = new ArrayList<>();
        FlowNetwork network = initFlowNet(x, certificateteamNums);
        if (network == null) {
            return certificateteamNums;
        }
        minCutTeams(network, certificateteamNums);
        return certificateteamNums;
    }

    private void minCutTeams(FlowNetwork network, List<Integer> certificateteamNums) {
        int s = network.V()-2, t = network.V()-1;
        FordFulkerson fordFulkerson = new FordFulkerson(network, s, t);
        for (FlowEdge edge : network.adj(s)) {
            int w = edge.other(s);
            if (!fordFulkerson.inCut(w)) {
                continue;
            }
            for (FlowEdge res : network.adj(w)) {
                certificateteamNums.add(res.to());
            }
        }
    }

    private FlowNetwork initFlowNet(int x, List<Integer> certificateteamNums) {
        int n = teamNums.size();
        FlowNetwork network = new FlowNetwork(n + ((n-2)*(n-1) / 2)+2);

        int maxWinNum = wins[x] + remains[x];
        int s = network.V()-2, t = network.V()-1;
        int v = 0;

        // init team vertices
        for (; v < n; v++) {
            if (v == x) {
                continue;
            }
            int capacity = maxWinNum - wins[v];
            if (capacity < 0) {
                certificateteamNums.add(v);
                continue;
            }
            FlowEdge edge = new FlowEdge(v, t, capacity);
            network.addEdge(edge);
        }

        if (!certificateteamNums.isEmpty()) {
            return null;
        }

        // init game vertices
        for (int i = 0; i < competitions.length; i++) {
            if (i == x) {
                continue;
            }
            for (int j = i+1; j < competitions[i].length; j++) {
                if (j == x) {
                    continue;
                }
                FlowEdge backEdge = new FlowEdge(s, v, competitions[i][j]);
                network.addEdge(backEdge);
                FlowEdge forwardEdge1 = new FlowEdge(v, i, Double.POSITIVE_INFINITY);
                network.addEdge(forwardEdge1);
                FlowEdge forwardEdge2 = new FlowEdge(v, j, Double.POSITIVE_INFINITY);
                network.addEdge(forwardEdge2);
                v++;
            }
        }

        return network;
    }

    private int getTeamNums(String team) {
        if (team == null || team.isEmpty()) {
            throw new IllegalArgumentException("empty team name");
        }
        Integer num = teamNums.get(team);
        if (num == null) {
            throw new IllegalArgumentException("invalid team name");
        }
        return num;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("..\\baseball\\teams54.txt");

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
    
}