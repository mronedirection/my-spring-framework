package demo.pattern.factory.entity;

/**
 * @author RenHao
 * @create 2023-08-14 22:15
 */
public class DellMouse implements Mouse {
    @Override
    public void sayHi() {
        System.out.println("我是戴尔鼠标");
    }
}
