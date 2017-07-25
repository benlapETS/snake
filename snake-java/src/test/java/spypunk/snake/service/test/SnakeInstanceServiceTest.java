/**
 * 
 */

package spypunk.snake.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Optional;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.factory.SnakeFactory;
import spypunk.snake.factory.SnakeFactoryImpl;
import spypunk.snake.model.SnakeDirection;
import spypunk.snake.model.Snake;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.State;
import spypunk.snake.model.Type;
import spypunk.snake.model.Direction;
import spypunk.snake.service.SnakeInstanceServiceImpl;

/**
 * @author bened
 *
 */
public class SnakeInstanceServiceTest {

  private Snake snake;
  private SnakeInstanceServiceImpl service;

  @Before
  public void Setup() {
    SnakeFactory factory = new SnakeFactoryImpl();
    snake = factory.createSnake();
    service = new SnakeInstanceServiceImpl();
    service.create(snake);
  }
  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  @Test
  public void testInitialSpeed() {
    assertEquals(SnakeConstants.DEFAULT_SPEED,
        snake.getSnakeInstance().getSpeed());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  @Test
  public void testInitialState() {
    assertEquals(State.RUNNING, snake.getSnakeInstance().getState());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  @Test
  public void testInitialDirection() {
    assertEquals(SnakeDirection.DOWN, snake.getSnakeInstance().getSnakeDirection());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  @Test
  public void testInitialSnakeSize() {
    assertEquals(3, snake.getSnakeInstance().getSnakeParts().size());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  @Test
  public void testInitialSnakePosition() {
    assertEquals(new Point(SnakeConstants.WIDTH / 2, 2),
        snake.getSnakeInstance().getSnakeParts().get(0));
    assertEquals(new Point(SnakeConstants.WIDTH / 2, 1),
        snake.getSnakeInstance().getSnakeParts().get(1));
    assertEquals(new Point(SnakeConstants.WIDTH / 2, 0),
        snake.getSnakeInstance().getSnakeParts().get(2));
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  @Test
  public void testInitialStatistics() {
    Map<Type,Integer> stats = snake.getSnakeInstance().getStatistics();
    assertEquals(0, stats
        .get(Type.NORMAL).intValue());
    assertEquals(0, stats
        .get(Type.BONUS).intValue());
  }

  //Test not valid anymore since we can't access x,y location
  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#create(spypunk.snake.model.Snake)}.
   */
  /*@Test
  public void testInitialFoodLocation() {
    assertTrue(snake.getSnakeInstance().getFood().getLocation().getX() >= 0);
    assertTrue(snake.getSnakeInstance().getFood().getLocation().getY() >= 0);
    assertTrue(snake.getSnakeInstance().getFood().getLocation()
        .getX() < SnakeConstants.WIDTH);
    assertTrue(snake.getSnakeInstance().getFood().getLocation()
        .getY() < SnakeConstants.HEIGHT);
    assertFalse(snake.getSnakeInstance().getSnakeParts()
        .contains(snake.getSnakeInstance().getFood().getLocation()));
  }*/

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#update(SnakeInstance<Point>)}.
   */
  @Test
  public void testUpdateFrame() {
    int initialMovementFrame = snake.getSnakeInstance()
        .getCurrentMovementFrame();
    int initialFrameSinceLastFood = snake.getSnakeInstance()
        .getFramesSinceLastFood();
    service.update(snake.getSnakeInstance());
    assertEquals(initialMovementFrame + 1,
        snake.getSnakeInstance().getCurrentMovementFrame());
    assertEquals(initialFrameSinceLastFood + 1,
        snake.getSnakeInstance().getFramesSinceLastFood());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#update(SnakeInstance<Point>)}.
   */
  @Test
  public void testNotTimeToHandleMovement() {
    service.update(snake.getSnakeInstance());
    assertFalse(snake.getSnakeInstance().getCurrentMovementFrame() > snake
        .getSnakeInstance().getSpeed());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#update(SnakeInstance<Point>)}.
   */
  @Test
  public void testTimeToHandleMovement() {
    for (int i = 0; i < SnakeConstants.DEFAULT_SPEED; i++) {
      service.update(snake.getSnakeInstance());
    }
    assertTrue(snake.getSnakeInstance()
        .getCurrentMovementFrame() + 1 > snake.getSnakeInstance().getSpeed());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#update(SnakeInstance<Point>)}.
   */
  @Test
  public void testDirectionNotChanged() {
    service.update(snake.getSnakeInstance());
    assertEquals(snake.getSnakeInstance().getSnakeDirection(), SnakeDirection.DOWN);
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#pause(SnakeInstance<Point>)}.
   */
  @Test
  public void testPause() {
    service.pause(snake.getSnakeInstance());
    assertEquals(State.PAUSED, snake.getSnakeInstance().getState());
  }

  /**
   * Test method for
   * {@link spypunk.snake.service.SnakeInstanceServiceImpl#updateDirection(SnakeInstance, Direction )}.
   */
  @Test
  public void testUpdateDirection() {
    service.updateDirection(snake.getSnakeInstance(), SnakeDirection.LEFT);
    for (int i = 0; i < SnakeConstants.DEFAULT_SPEED + 1; i++) {
      service.update(snake.getSnakeInstance());
    }
    assertEquals(SnakeDirection.LEFT, snake.getSnakeInstance().getSnakeDirection());
    assertEquals(Optional.empty(),
        snake.getSnakeInstance().getNewSnakeDirection());
  }

}
