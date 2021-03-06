package com.etsmtl.ca.log530.snake.model;

import java.net.URI;

/**
 * Created by gabar on 2017-07-24.
 */

public final class AndroidBuilder {

    private final AndroidSnakeImpl snake = new AndroidSnakeImpl();

    private AndroidBuilder() {
    }

    public static AndroidBuilder instance() {
        return new AndroidBuilder();
    }

    public AndroidBuilder setName(final String name) {
        snake.setName(name);
        return this;
    }

    public AndroidBuilder setVersion(final String version) {
        snake.setVersion(version);
        return this;
    }

    public AndroidBuilder setProjectURI(final URI projectURI) {
        snake.setProjectURI(projectURI);
        return this;
    }

    public AndroidSnakeImpl build() {
        return snake;
    }

}
