package com.etsmtl.ca.log530.snake.module;

import android.app.Application;

import com.etsmtl.ca.log530.snake.controller.gameloop.AndroidSnakeControllerGameLoopImpl;
import com.etsmtl.ca.log530.snake.factory.AndroidSnakeFactory;
import com.etsmtl.ca.log530.snake.factory.AndroidSnakeFactoryImpl;
import com.etsmtl.ca.log530.snake.service.AndroidSnakeInstanceService;
import com.etsmtl.ca.log530.snake.service.AndroidSnakeInstanceServiceImpl;
import com.etsmtl.ca.log530.snake.ui.controller.AndroidSnakeController;
import com.etsmtl.ca.log530.snake.ui.controller.AndroidSnakeControllerImpl;
import com.etsmtl.ca.log530.snake.ui.controller.event.AndroidSnakeControllerSnakeEventHandler;
import com.etsmtl.ca.log530.snake.ui.controller.event.AndroidSnakeControllerSnakeEventHandlerImpl;
import com.etsmtl.ca.log530.snake.ui.factory.AndroidControllerCommandFactory;
import com.etsmtl.ca.log530.snake.ui.factory.AndroidControllerCommandFactoryImpl;
import com.etsmtl.ca.log530.snake.ui.input.AndroidSnakeControllerInputHandler;
import com.etsmtl.ca.log530.snake.ui.input.AndroidSnakeControllerInputHandlerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;
import spypunk.snake.ui.controller.input.SnakeController;

/**
 * Created by gabar on 2017-07-25.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }
    @Provides
    @Singleton
    public AndroidSnakeControllerInputHandler provideSnakeControllerInputHandler(AndroidControllerCommandFactory controllerCommandFactory){
        return new AndroidSnakeControllerInputHandlerImpl(controllerCommandFactory);
    }
    @Provides
    @Singleton
    public AndroidSnakeController provideSnakeController(AndroidSnakeInstanceService snakeInstanceService,
                                                         AndroidSnakeFactory snakeFactory,
                                                         AndroidSnakeControllerInputHandler snakeControllerInputHandler,
                                                         AndroidSnakeControllerSnakeEventHandler snakeControllerSnakeEventHandler,
                                                         SnakeControllerGameLoop snakeControllerGameLoop){
        return new AndroidSnakeControllerImpl(snakeInstanceService,snakeFactory,snakeControllerInputHandler,snakeControllerSnakeEventHandler,snakeControllerGameLoop);
    }
    @Provides
    @Singleton
    public AndroidSnakeInstanceService provideSnakeInstanceService(){
        return new AndroidSnakeInstanceServiceImpl();
    }
    @Provides
    @Singleton
    public AndroidSnakeControllerSnakeEventHandler provideSnakeControllerSnakeEventHandler(AndroidControllerCommandFactory controllerCommandFactory){
        return new AndroidSnakeControllerSnakeEventHandlerImpl(controllerCommandFactory);
    }

    @Provides
    @Singleton
    public AndroidControllerCommandFactory provideControllerCommandFactory(AndroidSnakeInstanceService snakeInstanceService){
        return new AndroidControllerCommandFactoryImpl(snakeInstanceService);
    }
    @Provides
    @Singleton
    public AndroidSnakeFactory provideSnakeFactory(){
        return new AndroidSnakeFactoryImpl();
    }

    @Provides
    @Singleton
    public SnakeControllerGameLoop provideSnakeControllerGameLoop(){
        return new AndroidSnakeControllerGameLoopImpl();
    }
}
