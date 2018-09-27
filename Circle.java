public class Circle {
  public double radius;

  public Circle(){
    radius = 1;
  }

  public Circle(double newRadius){
    radius = newRadius;
  }

  public double getArea(){
    return Math.PI*Math.pow(radius, 2);
  }

  public double getRadius(){
    return radius;
  }

  public void setRadius(double r){
    radius = r;
  }

  public static double getPerimeter(Circle c) {
    return 2 * c.getRadius() * Math.PI;
  }

  public String toString(){
    return "Circle(r: " + radius + " a: " + getArea() + ")";
  }
}
