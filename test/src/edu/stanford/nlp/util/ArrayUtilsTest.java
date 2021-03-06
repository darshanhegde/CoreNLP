package edu.stanford.nlp.util;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Christopher Manning 
 * @author John Bauer
 */
public class ArrayUtilsTest extends TestCase {

  int[] sampleGaps = {1, 5, 6, 10, 17, 22, 29, 33, 100, 1000, 10000, 9999999};
  int[] sampleBadGaps = {1, 6, 5, 10, 17};

  public void testEqualContentsInt() {
    assertTrue(ArrayUtils.equalContents(sampleGaps, sampleGaps));
    assertTrue(ArrayUtils.equalContents(sampleBadGaps, sampleBadGaps));
    assertFalse(ArrayUtils.equalContents(sampleGaps, sampleBadGaps));
  }

  public void testGaps() {
    byte[] encoded = ArrayUtils.gapEncode(sampleGaps);
    int[] decoded = ArrayUtils.gapDecode(encoded);
    assertTrue(ArrayUtils.equalContents(decoded, sampleGaps));

    try {
      ArrayUtils.gapEncode(sampleBadGaps);
      throw new RuntimeException("Expected an IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // yay, we passed
    }
  }

  public void testDelta() {
    byte[] encoded = ArrayUtils.deltaEncode(sampleGaps);
    int[] decoded = ArrayUtils.deltaDecode(encoded);
    assertTrue(ArrayUtils.equalContents(decoded, sampleGaps));

    try {
      ArrayUtils.deltaEncode(sampleBadGaps);
      throw new RuntimeException("Expected an IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // yay, we passed
    }
  }

  public void testRemoveAt() {
    String[] strings = new String[]{"a", "b", "c"};
    strings = (String[]) ArrayUtils.removeAt(strings, 2);
    int i = 0;
    for (String string : strings) {
      if (i == 0) {
        assertEquals("a", string);
      } else if (i == 1) {
        assertEquals("b", string);
      } else {
        fail("Array is too big!");
      }
      i++;
    }
  }

  public void testAsSet() {
    String[] items = {"larry", "moe", "curly"};
    Set<String> set = new HashSet<String>();
    for (String item: items) {
      set.add(item);
    }
    assertEquals(set, ArrayUtils.asSet(items));
  }
  
  
  public void testgetSubListIndex() {
    String[] t1 = {"this", "is", "test"};
    String[] t2 = {"well","this","is","not","this","is","test","also"};
    Assert.assertEquals(4,(ArrayUtils.getSubListIndex(t1, t2).get(0).intValue()));
    String[] t3 = {"cough","increased"};
    String[] t4 = {"i","dont","really","cough"};
    Assert.assertEquals(0, ArrayUtils.getSubListIndex(t3, t4).size());
    String[] t5 = {"cough","increased"};
    String[] t6 = {"cough","aggravated"};
    Assert.assertEquals(0, ArrayUtils.getSubListIndex(t5, t6).size());
    String[] t7 = {"cough","increased"};
    String[] t8 = {"cough","aggravated","cough","increased","and","cough", "increased","and","cough","and","increased"};
    Assert.assertEquals(2, ArrayUtils.getSubListIndex(t7, t8).get(0).intValue());
    Assert.assertEquals(5, ArrayUtils.getSubListIndex(t7, t8).get(1).intValue());
  }

}
