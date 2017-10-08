package io.jocmer.exp.polymorphism;

/**
 *
 * @author joc
 */
public class SingleImplementation implements SingleImplInterface {

    public static SingleImplInterface impl() {
        return new SingleImplementation();
    }

    @Override
    public int produceIntValue() {
        return 666;
    }

}
