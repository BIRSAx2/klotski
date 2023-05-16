package dev.plagarizers.klotski.model.util;

public class Coordinate implements Cloneable {
  private int x;
  private int y;

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Coordinate of(int x, int y) {
    return new Coordinate(x, y);
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }


  public Coordinate add(int x, int y) {
    return new Coordinate(this.x + x, this.y + y);
  }

  public Coordinate add(Coordinate c) {
    return new Coordinate(this.x + c.x, this.y + c.y);
  }


  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Coordinate)) {
      return false;
    }
    Coordinate c = (Coordinate) o;
    return x == c.x && y == c.y;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + x;
    result = 31 * result + y;
    return result;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public Coordinate clone() {
    return new Coordinate(x, y);
  }
}
