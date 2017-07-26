package spypunk.snake.model;

import java.net.URI;

/**
 * Created by gabar on 2017-07-24.
 */

public abstract class Snake<I extends SnakeInstance> {
    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getVersion();

    public abstract void setVersion(String version);

    public abstract URI getProjectURI();

    public abstract void setProjectURI(URI projectURI);

    public abstract I getSnakeInstance();

    public abstract void setSnakeInstance(I snakeInstance);
}
