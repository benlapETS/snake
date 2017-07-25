package spypunk.snake.model;

import java.net.URI;

/**
 * Created by gabar on 2017-07-24.
 */

public interface Snake<I extends SnakeInstance> {
    String getName();

    void setName(String name);

    String getVersion();

    void setVersion(String version);

    URI getProjectURI();

    void setProjectURI(URI projectURI);

    I getSnakeInstance();

    void setSnakeInstance(I snakeInstance);
}
