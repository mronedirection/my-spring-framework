package demo.generic;

/**
 * @author RenHao
 * @create 2023-08-14 21:00
 */
public interface GenericIFactory<T,N> {
    T nextObject();
    N nextNumber();
}
