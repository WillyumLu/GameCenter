package fall2018.csc2017.game_centre.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.Game;
import fall2018.csc2017.game_centre.R;
import fall2018.csc2017.game_centre.ScoreBoard;
import fall2018.csc2017.game_centre.User;
import fall2018.csc2017.game_centre.UserFileHandler;

/**
 * View class, excluded from unit test.
 * Scoreboard of Sudoku.
 */
public class SudokuScoreBoardActivity extends AppCompatActivity {

    /**
     * The file handler for user file io
     */
    private UserFileHandler userFileHandler = UserFileHandler.getInstance();

    /**
     * The file handler for score file io.
     */
    private SudokuScoreBoardFileHandler scoreFileHandler = SudokuScoreBoardFileHandler.getInstance();

    /**
     * ScoreBoard class for game.
     */
    private ScoreBoard scoreBoard = new SudokuScoreBoardController();

    /**
     * Users, loaded from SAVE_ALL_USERS type-casted into a Hash map
     */
    private Map<String, User> users = userFileHandler.getUsers();

    /**
     * on create method
     * @param savedInstanceState from superclass.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        scoreFileHandler.loadFromFile(SudokuScoreBoardActivity.this);
        if (!((getIntent().getExtras()) == null)) {
            int hintLeft = (int) getIntent().getExtras().get("hintCounter");
            int wrong = (int) getIntent().getExtras().get("wrongCounter");
            int time = (int) getIntent().getExtras().get("totalTime");
            ArrayList updateParam =
                    new ArrayList<>(Arrays.asList(hintLeft, wrong, time));
            update(updateParam);
        }
        scoreBoard.formatUsers(Game.Sudoku, users, scoreFileHandler.leaderBoard); // sorts user information and prepares them for display
        scoreFileHandler.saveToFile(this);
        addTextView();
    }

    /**
     * Generate appropriate text for TextView Display
     *
     * @param index index of position in leader board
     * @return String in the format of "username": "points"
     */
    private String generateText(int index){
        return String.format(Locale.CANADA, "%s : %s points",
                scoreFileHandler.leaderBoard.get(index).get(1), scoreFileHandler.leaderBoard.get(index).get(0));
    }

    /**
     * Add TextView for top five players and current player.
     */
    private void addTextView() {
        TextView tvFirst = findViewById(R.id.first);
        String firstDisplay = (scoreFileHandler.leaderBoard.size() > 0)? generateText(0) : "No data recorded.";
        tvFirst.setText(firstDisplay);
        TextView tvSecond = findViewById(R.id.second);
        String secondDisplay = (scoreFileHandler.leaderBoard.size() > 1)? generateText(1) : "No data recorded.";
        tvSecond.setText(secondDisplay);
        TextView tvThird = findViewById(R.id.third);
        String thirdDisplay = (scoreFileHandler.leaderBoard.size() > 2)? generateText(2) : "No data recorded.";
        tvThird.setText(thirdDisplay);
        TextView tvFourth = findViewById(R.id.fourth);
        String fourthDisplay = (scoreFileHandler.leaderBoard.size() > 3)? generateText(3) : "No data recorded.";
        tvFourth.setText(fourthDisplay);
        TextView tvFifth = findViewById(R.id.fifth);
        String fifthDisplay = (scoreFileHandler.leaderBoard.size() > 4)? generateText(4) : "No data recorded.";
        tvFifth.setText(fifthDisplay);
        TextView tvCurrentPlayer = findViewById(R.id.player_rank);
        String currentDisplay = generateCurrentText();
        tvCurrentPlayer.setText(currentDisplay);
    }

    /**
     * Generate the current player's rank and points
     * @return the current player's rank and points
     */
    private String generateCurrentText(){
        String currentDisplay = ("No data recorded, yet.");
        User currentUser = CurrentStatus.getCurrentUser();
        for (int index = 0; index < scoreFileHandler.leaderBoard.size(); index++) {
            if (scoreFileHandler.leaderBoard.get(index).get(1).equals(currentUser.getUsername())) {
                currentDisplay = String.format(Locale.CANADA, "%s : %s points, rank %d",
                        scoreFileHandler.leaderBoard.get(index).get(1),
                        scoreFileHandler.leaderBoard.get(index).get(0), index + 1);
                break;
            }
        }
        return currentDisplay;
    }

    /**
     * Update the scoreFileHandler.leaderBoard.
     * @param scoreParameter the array list of information needed for score.
     */
    public void update(ArrayList<Integer> scoreParameter) {
        int newScore = scoreBoard.calculateScore(scoreParameter);
        scoreBoard.update(newScore, Game.Sudoku);
        scoreBoard.writeScore(newScore, scoreFileHandler.leaderBoard);
        scoreFileHandler.saveToFile(this);
        scoreFileHandler.loadFromFile(this);
        userFileHandler.saveToFile(SudokuScoreBoardActivity.this);
        userFileHandler.loadFromFile(SudokuScoreBoardActivity.this);
        addTextView();
    }
}