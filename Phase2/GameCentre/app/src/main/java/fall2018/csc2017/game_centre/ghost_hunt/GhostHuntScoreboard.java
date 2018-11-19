package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.Game;
import fall2018.csc2017.game_centre.ScoreBoard;
import fall2018.csc2017.game_centre.User;

public class GhostHuntScoreboard extends ScoreBoard {

    /**
     * calculate score of game
     *
     * @param array an ArrayList of length 2, index 0 is levels, index 1 is time in seconds
     */
    public int calculateScore(ArrayList<Integer> array) {
        float level = array.get(0);
        float time_in_sec = array.get(1);
        double dbl_score = 1 / ((level * 100.0) + time_in_sec);
        return (int) Math.round(dbl_score * 100000);
    }

    /**
     * Updates currentUser's high score, if needed.
     *
     * @param score the new score from end of game
     */
    public void update(Integer score, HashMap<String, User> users) {
        User currentUser = CurrentStatus.getCurrentUser();
        if (score > currentUser.getScore(Game.GhostHunt)) {
            for (User user : users.values()){
                if (currentUser.getUsername().equals(user.getUsername())){
                    user.setScore(Game.GhostHunt, score);
                }
            }
        }
    }

    /**
     * Formats each user's information into an ArrayList:
     * [int score, String username]
     */
    void formatUsers(HashMap<String, User> users, ArrayList<ArrayList> leaderBoard) {
        ArrayList<ArrayList> scoreKeyArray = new ArrayList<>();
        Game slidingTile = Game.GhostHunt;
        for (User entry : users.values()) {
            ArrayList<Object> scoreKeyPair = new ArrayList<>();
            scoreKeyPair.add(entry.getScore(slidingTile));
            scoreKeyPair.add(entry.getUsername());
            scoreKeyArray.add(scoreKeyPair);
        }
        customSort(scoreKeyArray, leaderBoard);
    }

    /**
     * sorts this nested list
     * by the first element in each sub list.
     *
     * @param nestedList a nested ArrayList
     */
    private void customSort(ArrayList<ArrayList> nestedList, ArrayList<ArrayList> leaderBoard) {
        int size = nestedList.size();
        for (ArrayList pair : nestedList) {
            if (leaderBoard.contains(pair)) {
                continue;
            }
            if (leaderBoard.isEmpty()) {
                leaderBoard.add(pair);
            } else if ((int) pair.get(0) < (int) leaderBoard.get(leaderBoard.size() - 1).get(0)) {
                leaderBoard.add(pair);
            } else {
                for (int i = 0; i < size; i++) {
                    if ((int) pair.get(0) >= (int) leaderBoard.get(i).get(0)) {
                        leaderBoard.add(i, pair);
                        break;
                    }
                }
            }
        }
    }
}