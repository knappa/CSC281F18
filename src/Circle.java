public class Circle {
  final private double radius;

  public Circle(){
    radius = 1;
  }

  public Circle(double radius){
    this.radius = radius;
  }

  public double getArea(){
    return Math.PI*Math.pow(radius, 2);
  }

  public double getRadius(){
    return radius;
  }

  public boolean equals(Circle other) {

    if(other == null) {
      return false;
    } else {
      return this.radius == other.radius;
    }

  }
  
  // public void setRadius(double r){
  //   radius = r;
  // }

  // public void setRadiusToFive() {
  //   this.radius = 5;
  // }
  
  public double getPerimeter() {
    return 2 * this.radius * Math.PI;
  }

  public String toString(){
    return "Circle(r: " + radius + " a: " + getArea() + ")";
  }
}
