package com.etsmtl.ca.log530.snake.ui.input.listener;

/**
 * Created by gabar on 2017-07-25.
 */
public enum SwipeDirection {
    up,
    down,
    left,
    right;

    /**
     * Returns a direction given an angle.
     * Directions are defined as follows:
     * <p>
     * Up: [45, 135]
     * Right: [0,45] and [315, 360]
     * Down: [225, 315]
     * Left: [135, 225]
     *
     * @param angle an angle from 0 to 360 - e
     * @return the direction of an angle
     */
    public static SwipeDirection get(double angle) {
        if (inRange(angle, 45, 135)) {
            return SwipeDirection.up;
        } else if (inRange(angle, 0, 45) || inRange(angle, 315, 360)) {
            return SwipeDirection.right;
        } else if (inRange(angle, 225, 315)) {
            return SwipeDirection.down;
        } else {
            return SwipeDirection.left;
        }

    }

    /**
     * @param angle an angle
     * @param init  the initial bound
     * @param end   the final bound
     * @return returns true if the given angle is in the interval [init, end).
     */
    private static boolean inRange(double angle, float init, float end) {
        return (angle >= init) && (angle < end);
    }
}
