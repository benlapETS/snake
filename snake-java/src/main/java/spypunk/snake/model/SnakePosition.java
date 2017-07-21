/**
 * 
 */
package spypunk.snake.model;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;

import spypunk.snake.constants.SnakeConstants;

/**
 * @author bened
 *
 */
public class SnakePosition implements Position<Point>{
  private Deque<Point> snakeParts;
  private Point lastPartRemoved;

  /**
   * 
   */
  public SnakePosition() {
    snakeParts = new ArrayDeque<Point>();
    final int x = SnakeConstants.WIDTH / 2;
    snakeParts.add(new Point(x, 2));
    snakeParts.add(new Point(x, 1));
    snakeParts.add(new Point(x, 0));
  }

  public Deque<Point> getParts() {
    return snakeParts;
  }

  public void updateTo(Point newLocation) {
    snakeParts.addFirst(newLocation);
    lastPartRemoved = snakeParts.removeLast();
  }

  public Point getHeadLocation() {
    return snakeParts.peek();
  }

  public void expand() {
    snakeParts.add(lastPartRemoved);
  }

  public boolean overlaps(Point p) {
    return snakeParts.contains(p);
  }

}
