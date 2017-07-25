package com.etsmtl.ca.log530.snake.model;

import java.net.URI;

import spypunk.snake.model.Snake;
import spypunk.snake.model.SnakeInstance;

/**
 * Created by gabar on 2017-07-24.
 */

public class AndroidSnakeImpl implements Snake<AndroidSnakeInstanceImpl> {

    private String name;

    private String version;

    private URI projectURI;

    private AndroidSnakeInstanceImpl snakeInstance;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(final String version) {
        this.version = version;
    }

    @Override
    public URI getProjectURI() {
        return projectURI;
    }

    @Override
    public void setProjectURI(final URI projectURI) {
        this.projectURI = projectURI;
    }

    @Override
    public AndroidSnakeInstanceImpl getSnakeInstance() {
        return snakeInstance;
    }

    @Override
    public void setSnakeInstance(final AndroidSnakeInstanceImpl snakeInstance) {
        this.snakeInstance = snakeInstance;
    }
}