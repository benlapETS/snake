package spypunk.snake.model;

import java.net.URI;

/**
 * Created by gabar on 2017-07-24.
 */
public final class Builder {

    private final Snake snake = new SnakeImpl();

    private Builder() {
    }

    public static Builder instance() {
        return new Builder();
    }

    public Builder setName(final String name) {
        snake.setName(name);
        return this;
    }

    public Builder setVersion(final String version) {
        snake.setVersion(version);
        return this;
    }

    public Builder setProjectURI(final URI projectURI) {
        snake.setProjectURI(projectURI);
        return this;
    }

    public Snake build() {
        return snake;
    }

}
