package com.etsmtl.ca.log530.snake.component;

import com.etsmtl.ca.log530.snake.SnakeGameActivity;
import com.etsmtl.ca.log530.snake.SnakeApplication;
import com.etsmtl.ca.log530.snake.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gabar on 2017-07-26.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(SnakeGameActivity target);

}
