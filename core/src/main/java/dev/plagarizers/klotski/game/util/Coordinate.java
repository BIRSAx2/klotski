package dev.plagarizers.klotski.game.util;


import com.google.gson.Gson;

/**
 * Represents a coordinate with x and y values.
 */
public class Coordinate implements Cloneable {
    private int x;
    private int y;

    /**
     * Creates a Coordinate instance with the specified x and y values.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a Coordinate instance with the specified x and y values.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the created Coordinate instance
     */
    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    /**
     * Creates a Coordinate object from its JSON representation.
     *
     * @param json the JSON string representing the Coordinate object
     * @return the created Coordinate object
     */
    public static Coordinate fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Coordinate.class);
    }

    /**
     * Returns the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x the x coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y the y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Adds the given x and y values to the coordinate and returns a new Coordinate object.
     *
     * @param x The x value to add to the coordinate.
     * @param y The y value to add to the coordinate.
     * @return A new Coordinate object with the updated x and y values.
     */
    public Coordinate add(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    /**
     * Adds the given Coordinate's x and y values to the coordinate and returns a new Coordinate object.
     *
     * @param other The Coordinate to add to the coordinate.
     * @return A new Coordinate object with the updated x and y values.
     */
    public Coordinate add(Coordinate other) {
        return new Coordinate(this.x + other.x, this.y + other.y);
    }

    /**
     * Moves the coordinate in the specified direction and returns the new coordinate.
     *
     * @param direction the direction to move the coordinate
     * @return the new coordinate after moving in the specified direction
     */
    public Coordinate move(Direction direction) {
        switch (direction) {
            case UP:
                return this.add(-1, 0);
            case RIGHT:
                return this.add(0, 1);
            case LEFT:
                return this.add(0, -1);
            case DOWN:
                return this.add(1, 0);
        }
        return null;
    }

    /**
     * Checks if this Coordinate object is equal to the given object.
     *
     * @param o the object to compare to this Coordinate object
     * @return true if the object is equal to this Coordinate object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate c = (Coordinate) o;
        return x == c.x && y == c.y;
    }

    /**
     * Returns the hash code of this Coordinate object.
     *
     * @return the hash code of this Coordinate object
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    /**
     * Returns a string representation of this Coordinate object.
     *
     * @return a string representation of this Coordinate object
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Creates a deep copy of this Coordinate object.
     *
     * @return a cloned instance of the Coordinate object
     */
    @Override
    public Coordinate clone() {
        return new Coordinate(x, y);
    }

    /**
     * Converts this Coordinate object to its JSON representation.
     *
     * @return the JSON string representing this Coordinate object
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
