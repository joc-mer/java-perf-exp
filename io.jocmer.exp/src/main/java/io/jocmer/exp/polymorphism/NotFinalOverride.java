package io.jocmer.exp.polymorphism;

/**
 *
 * @author joc
 */
public class NotFinalOverride extends AbstractClass {

    @Override
    public int produceIntValue() {
        return 666;
    }
}
