package com.etsmtl.ca.log530.snake.factory;

import com.etsmtl.ca.log530.snake.model.AndroidBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import javax.inject.Singleton;

import spypunk.snake.exception.SnakeException;
import spypunk.snake.factory.SnakeFactory;
import spypunk.snake.model.Snake;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeFactoryImpl implements SnakeFactory {

    private static final String NAME_KEY = "name".intern();

    private static final String VERSION_KEY = "version".intern();

    private static final String URL_KEY = "url".intern();

    private static final String SNAKE_PROPERTIES = "/snake.properties".intern();

    private final String name;

    private final String version;

    private final URI uri;

    public AndroidSnakeFactoryImpl() {
        try (InputStream inputStream = AndroidSnakeFactoryImpl.class.getResource(SNAKE_PROPERTIES).openStream()) {
            final Properties properties = new Properties();

            properties.load(inputStream);

            name = properties.getProperty(NAME_KEY);
            version = properties.getProperty(VERSION_KEY);
            uri = URI.create(properties.getProperty(URL_KEY));
        } catch (final IOException e) {
            //TODO handle logging
            throw new SnakeException(e);
        }
    }

    @Override
    public Snake createSnake() {
        return AndroidBuilder.instance().setName(name).setVersion(version).setProjectURI(uri).build();
    }
}
