package org.jfree.chart;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeShiftTest {

    private Range myRange;
    private Range positiveShiftedRange;
    private Range negativeShiftedRange;
    private Range noShiftRange;
    private Range largeShiftedRange;

    @Before
    public void setUp() {
        // Initialize your ranges here
        myRange = new Range(-1, 1);
        positiveShiftedRange = Range.shift(myRange, 2);
        negativeShiftedRange = Range.shift(myRange, -2);
        noShiftRange = Range.shift(myRange, 0);
        largeShiftedRange = Range.shift(myRange, 1000000);
    }

    @Test
    public void testPositiveShiftLowerBound() {
        assertEquals("Positive shift lower bound should be 1", 1, positiveShiftedRange.getLowerBound(), .000000001d);
    }

    @Test
    public void testPositiveShiftUpperBound() {
        assertEquals("Positive shift upper bound should be 3", 3, positiveShiftedRange.getUpperBound(), .000000001d);
    }

    @Test
    public void testNegativeShiftLowerBound() {
        assertEquals("Negative shift lower bound should be -3", -3, negativeShiftedRange.getLowerBound(), .000000001d);
    }

    @Test
    public void testNegativeShiftUpperBound() {
        assertEquals("Negative shift upper bound should be -1", -1, negativeShiftedRange.getUpperBound(), .000000001d);
    }

    @Test
    public void testNoShiftLowerBound() {
        assertEquals("No shift lower bound should remain unchanged", -1, noShiftRange.getLowerBound(), .000000001d);
    }

    @Test
    public void testNoShiftUpperBound() {
        assertEquals("No shift upper bound should remain unchanged", 1, noShiftRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testShiftWithLargeDeltaLowerBound() {
        assertEquals("Lower bound after large shift should be correct",
                999999, largeShiftedRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void testShiftWithLargeDeltaUpperBound() {
        assertEquals("Upper bound after large shift should be correct",
                1000001, largeShiftedRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testShiftedLength() {
        double shiftedSize = positiveShiftedRange.getLength();
        assertEquals("Shifted range size should remain constant",
                2.0, shiftedSize, .000000001d);
    }
   
    @Test
    public void testPositiveShiftedCentralValue() {
    	double shiftedCentralValue = positiveShiftedRange.getCentralValue();
        assertEquals("Shifted central value should be 2",
                2.0, shiftedCentralValue, .000000001d);
    }
    
    @Test
    public void testPositiveShiftedIntersect() {
        assertEquals("Shifted Range shouold intersect.",
                true, positiveShiftedRange.intersects(1, 3));
    }  
    
    @Test
    public void testPositiveShiftedNoIntersect() {
        assertEquals("Shifted Range shouold intersect.",
                false, positiveShiftedRange.intersects(4, 6));
    }  
    
    @Test
    public void testRepeatedShifts() {
        Range initialRange = new Range(-100, 100);
        Range shiftedRight = Range.shift(initialRange, 200);
        Range shiftedLeftBack = Range.shift(shiftedRight, -200);
        assertEquals("Shifting right then back should return to original lower bound",
                     initialRange.getLowerBound(), shiftedLeftBack.getLowerBound(), 0.000000001d);
        assertEquals("Shifting right then back should return to original upper bound",
                     initialRange.getUpperBound(), shiftedLeftBack.getUpperBound(), 0.000000001d);
    }
    
    @Test
    public void testShiftToDoubleLimits() {
        Range baseRange = new Range(1e307, 1e307 + 10);
        double shiftValue = 1e307;
        Range shiftedRange = Range.shift(baseRange, shiftValue);

        double expectedLength = 10; // The length should remain unchanged after the shift
        double actualLength = shiftedRange.getLength();

        // Check that the range has not collapsed
        assertTrue("After a large shift, range should not collapse to a point", actualLength > 0);

        // Additionally, check if the length remains as expected
        assertEquals("The length of the range after shifting should remain unchanged",
                     expectedLength, actualLength, 0.00001d);
    }
}