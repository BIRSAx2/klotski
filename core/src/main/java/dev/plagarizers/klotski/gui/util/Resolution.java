package dev.plagarizers.klotski.gui.util;

public class Resolution {
  private int width;
  private int height;

  public Resolution(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  private int gcd(int a, int b) {
    while(b != 0) {
      int r = b;
      b = a % b;
      a = r;
    }
    return a;
  }

  public String getAspectRatio() {
    int gcd = gcd(width, height);
    return width / gcd + ":" + height / gcd;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String toString() {
    StringBuilder s = new StringBuilder(width + "x" + height);
    String aspectRatio = getAspectRatio();
    while (s.length() + aspectRatio.length() <= 30) {
      s.append(" ");
    }
    return s.append(aspectRatio).toString();
  }
}
