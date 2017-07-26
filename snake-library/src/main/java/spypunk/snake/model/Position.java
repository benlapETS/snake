package spypunk.snake.model;

import java.util.Deque;

/**
 * Created by gabar on 2017-07-20.
 */

public interface Position<T> {
    Deque<T> getParts();

    void updateTo(T newLocation);

    T getHeadLocation();

    void expand();

    boolean overlaps(T p);
}
