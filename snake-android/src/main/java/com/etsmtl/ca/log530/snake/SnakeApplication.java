package com.etsmtl.ca.log530.snake;

import android.app.Application;

import com.etsmtl.ca.log530.snake.component.ApplicationComponent;
import com.etsmtl.ca.log530.snake.component.DaggerApplicationComponent;
import com.etsmtl.ca.log530.snake.module.ApplicationModule;


/**
 * The main app.
 * Created by gabar on 2017-07-26.
 */

public class SnakeApplication extends Application {
    private ApplicationComponent applicationComponent;

    protected ApplicationComponent initDagger(SnakeApplication application) {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(application))
                .build();
    }


    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initDagger(this);
    }
}
