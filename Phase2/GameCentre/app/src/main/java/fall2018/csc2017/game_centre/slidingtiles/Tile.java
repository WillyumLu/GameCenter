package fall2018.csc2017.game_centre.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    protected int background;

    /**
     * The unique id.
     */
    protected int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the id of the background pic
     * @param background the id of background
     */
    public void setBackground(int background) {
        this.background = background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {return id;}

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the Id of the image
     */
    Tile(int backgroundId) {
        id = backgroundId + 1;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
