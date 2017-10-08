package io.jocmer.exp.collections;

import java.util.HashSet;

/**
 *
 * @author merand
 */
public class LongBloomFilter {
    
    final long bitset;
    final HashSet<Long> backedCache = new HashSet<>();
    
    public LongBloomFilter(long... initialValues) {
        long tmpBitSet = 0x0;
        
        for (int i = 0; i < initialValues.length; i++) {
            long initialValue = initialValues[i];
            tmpBitSet |= hash(initialValue);
            backedCache.add(initialValue);
        }
        
        bitset = tmpBitSet;
    }
    
    private long hash(long value) {
        return 0x1 << value % 64;
    }
    
    public final boolean contains(long value) {
        long hash = this.hash(value);
        return (bitset & hash) == hash && backedCache.contains(value);
    }
    
}
