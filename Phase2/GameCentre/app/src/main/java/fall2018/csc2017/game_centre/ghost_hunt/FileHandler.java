package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;

/**
 * Model
 *
 * Handler for file IO in Ghost Hunt. Made into singleton.
 */
class FileHandler {

    /**
     * Sole instance of file handler.
     */
    private static final FileHandler INSTANCE = new FileHandler();

    /**
     * Logging tag.
     */
    private final String LOG_TAG = "GhostHuntFileHandler";

    /**
     * Temporary saving file.
     */
    static final String TEMP_FILENAME = "ghost_hunt_temp.ser";

    /**
     * Permanent saving file.
     */
    static final String SAVE_FILENAME = "ghost_hunt_save.ser";

    /**
     * Mapping from user name to corresponding board manager.
     */
    private Map<String, BoardManager> boardManagerMap;

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Private constructor for singleton.
     */
    private FileHandler() {}

    /**
     * Return the singleton instance.
     * @return sole instance of file handler
     */
    static FileHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Getter for board manager.
     * @return board manager
     */
    BoardManager getBoardManager() {
        return this.boardManager;
    }

    /**
     * Load data from file.
     * @param fileName file name
     */
    void loadFrom(Context context, String fileName) {
        // TODO: load from file
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManagerMap = (HashMap<String, BoardManager>) input.readObject();
                boardManager = boardManagerMap.get(CurrentStatus.getCurrentUser().getUsername());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, "File contained unexpected data type: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Calling on null reference: " + e.toString());
        }
    }

    void saveTo(Context context, String fileName, BoardManager boardManager) {
        // TODO: save to file
        try {
            if (boardManagerMap == null) {
                boardManagerMap = new HashMap<>();
            }
            boardManagerMap.put(CurrentStatus.getCurrentUser().getUsername(), boardManager);
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(boardManagerMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }
}
