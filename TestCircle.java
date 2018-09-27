public class TestCircle {
  public static void main(String[]  args){

    //Create and print first circle
    Circle circle1 = new Circle();
    System.out.println("The area of the circle of radius " + circle1.radius + " is " + circle1.getArea());

    //Create and print second circle
    Circle circle2 = new Circle(125.0);
    System.out.println("Circle 2 can be described as " + printCircle(circle2));

    //Create and print third circle
    Circle circle3 = new Circle(200.0);
    System.out.println(circle3);
  }

  public static String printCircle(Circle c){
    String str = "r: " + c.radius + " a: " + c.getArea();
    return str;
  }
}
