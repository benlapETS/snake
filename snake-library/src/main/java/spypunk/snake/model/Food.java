package spypunk.snake.model;

/**
 * A representation of a food item.
 * @param <T> the type used for the food's location
 */
public interface Food<T> {
    T getLocation();

    void setLocation(final T location);

    Type getType();

    void setType(final Type type);

    boolean isAt(T otherLocation);

    int getPoints();
}
