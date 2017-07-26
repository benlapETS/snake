package com.etsmtl.ca.log530.snake.factory;

import com.etsmtl.ca.log530.snake.model.AndroidBuilder;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import javax.inject.Singleton;

import spypunk.snake.exception.SnakeException;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeFactoryImpl extends AndroidSnakeFactory {

    private static final String NAME_KEY = "name";

    private static final String VERSION_KEY = "version";

    private static final String URL_KEY = "url";

    private static final String SNAKE_PROPERTIES = "/snake.properties";

    //TODO get properties from somewhere
    private final String name = "Snake";

    private final String version = "2.0";

    private final URI uri = URI.create("");

    public AndroidSnakeFactoryImpl() {
        /*try (InputStream inputStream = AndroidSnakeFactoryImpl.class.getResource(SNAKE_PROPERTIES).openStream()) {
            final Properties properties = new Properties();

            properties.load(inputStream);

            name = properties.getProperty(NAME_KEY);
            version = properties.getProperty(VERSION_KEY);
            uri = URI.create(properties.getProperty(URL_KEY));
        } catch (final IOException e) {
            //TODO handle logging
            throw new SnakeException(e);
        }*/
    }

    @Override
    public AndroidSnakeImpl createSnake() {
        return AndroidBuilder.instance().setName(name).setVersion(version).setProjectURI(uri).build();
    }
}
