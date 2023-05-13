package dev.plagarizers.klotski.util;

public class Pair<T,E> {
  private T first;
  private E second;

  public Pair(T first, E second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() {
    return first;
  }

  public E getSecond() {
    return second;
  }

  public void setFirst(T first) {
    this.first = first;
  }

  public void setSecond(E second) {
    this.second = second;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Pair)) {
      return false;
    }
    Pair p = (Pair) o;
    return first.equals(p.first) && second.equals(p.second);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + first.hashCode();
    result = 31 * result + second.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "(" + first + ", " + second + ")";
  }

}
