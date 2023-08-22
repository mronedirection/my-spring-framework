package demo.generic;

/**
 * @author RenHao
 * @create 2023-08-14 21:01
 */

public class GenericFactoryImpl<N,T> implements GenericIFactory<T, N> {
    @Override
    public T nextObject() {
        return null;
    }

    @Override
    public N nextNumber() {
        return null;
    }
}

