package spypunk.snake.model;

import java.awt.Point;
import java.util.Deque;

/**
 * Created by gabar on 2017-07-20.
 */

public interface Position<T> {
    public Deque<T> getParts();

    public void updateTo(T newLocation);

    public T getHeadLocation();

    public void expand();

    public boolean overlaps(T p);
}
