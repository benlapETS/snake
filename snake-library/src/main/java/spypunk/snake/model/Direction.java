package spypunk.snake.model;

/**
 * Created by gabar on 2017-07-20.
 */

public interface Direction<T,D extends Direction<T,D>> {
    D applyDirection(D d);
    T applyPoint(T other);
}
