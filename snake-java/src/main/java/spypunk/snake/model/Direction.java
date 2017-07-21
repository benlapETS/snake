package spypunk.snake.model;

/**
 * Created by gabar on 2017-07-20.
 */

public interface Direction<T,D extends Direction<T,D>> {
    public D apply(D d);
    public T apply(T other);
}
