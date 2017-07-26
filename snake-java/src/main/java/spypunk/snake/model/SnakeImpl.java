/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.model;

import java.net.URI;

public class SnakeImpl extends Snake<SnakeInstanceImpl> {

    private String name;

    private String version;

    private URI projectURI;

    private SnakeInstanceImpl snakeInstance;

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
    public SnakeInstanceImpl getSnakeInstance() {
        return snakeInstance;
    }

    @Override
    public void setSnakeInstance(final SnakeInstanceImpl snakeInstance) {
        this.snakeInstance = snakeInstance;
    }
}
