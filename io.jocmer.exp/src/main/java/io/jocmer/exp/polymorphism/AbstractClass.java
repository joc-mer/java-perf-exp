package io.jocmer.exp.polymorphism;

/**
 *
 * @author joc
 */
public abstract class AbstractClass {

    public abstract int produceIntValue();
    
    public static AbstractClass notFinalOverrideAsSuperClass() {return new NotFinalOverride();}
    
    public static AbstractClass finalOverrideAsSuperClass() {return new FinalOverride();}
}
