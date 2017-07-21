package spypunk.snake.model;

import java.awt.Point;

/**
 * A representation of a food item.
 * @param <T> the type used for the food's location
 */
public interface Food<T> {
    public T getLocation();

    public void setLocation(final T location);

    public Type getType();

    public void setType(final Type type);

    public boolean isAt(T otherLocation);

    public int getPoints();
}
