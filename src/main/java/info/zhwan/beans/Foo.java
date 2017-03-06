package info.zhwan.beans;

public class Foo implements IFoo {

  private String name;

  public Foo() {
  }

  public Foo(String name) {
    this.name = name;
  }

  @Override
  public void sayFoo() {
    System.out.println("i'm foo.");
  }
}
