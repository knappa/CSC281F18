public class TestCircle {
  public static void main(String[]  args){

    //Create and print first circle
    Circle circle1 = new Circle();
    System.out.println("The area of the circle of radius " + circle1.getRadius() + " is " + circle1.getArea());

    //Create and print second circle
    Circle circle2 = new Circle(125.0);
    System.out.println("Circle 2 can be described as " + printCircle(circle2));
    System.out.println("Circle 2 can be described as " + printCircle(circle2));

    //Create and print third circle
    Circle circle3 = new Circle(200.0);
    System.out.println(circle3);

    System.out.println("circle3's perimeter is: " + circle3.getPerimeter());
  }

  public static String printCircle(Circle c){
    String str = "r: " + c.getRadius() + " a: " + c.getArea();

    //c.radius = 5;
    //c = new Circle(10);
    
    return str;
  }
}
