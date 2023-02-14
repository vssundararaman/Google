import java.util.TreeMap;

/**
 * 715. Range Module
 * A Range Module is a module that tracks ranges of numbers. Design a data structure to track the ranges represented as half-open intervals and query about them.
 * <p>
 * A half-open interval [left, right) denotes all the real numbers x where left <= x < right.
 * <p>
 * Implement the RangeModule class:
 * <p>
 * RangeModule() Initializes the object of the data structure.
 * void addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval. Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval [left, right) that are not already tracked.
 * boolean queryRange(int left, int right) Returns true if every real number in the interval [left, right) is currently being tracked, and false otherwise.
 * void removeRange(int left, int right) Stops tracking every real number currently being tracked in the half-open interval [left, right).
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input
 * ["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"]
 * [[], [10, 20], [14, 16], [10, 14], [13, 15], [16, 17]]
 * Output
 * [null, null, null, true, false, true]
 * <p>
 * Explanation
 * RangeModule rangeModule = new RangeModule();
 * rangeModule.addRange(10, 20);
 * rangeModule.removeRange(14, 16);
 * rangeModule.queryRange(10, 14); // return True,(Every number in [10, 14) is being tracked)
 * rangeModule.queryRange(13, 15); // return False,(Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked)
 * rangeModule.queryRange(16, 17); // return True, (The number 16 in [16, 17) is still being tracked, despite the remove operation)
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= left < right <= 109
 * At most 104 calls will be made to addRange, queryRange, and removeRange.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        RangeModuleInner rangeModuleInner = new RangeModuleInner();
        rangeModuleInner.addRange(10, 20);
        System.out.println(rangeModuleInner.queryRange(10, 20));
        rangeModuleInner.removeRange(14, 16);
        System.out.println(rangeModuleInner.queryRange(14, 16));
        System.out.println(rangeModuleInner.queryRange(10, 14));
        System.out.println(rangeModuleInner.queryRange(13, 15));
        System.out.println(rangeModuleInner.queryRange(16, 17));

        System.out.println("***********************************");

    }


}

/*
  Intuition
  Save all existing ranges in TreeMap
  Add
  Find potential overlap at two sides (start and end)
  merge two sides
  everything in between can be removed
  Query
  Check whether query range can be 100% covered by an existing range
  Remove
  Find potential overlap at two sides (start and end)
  If any, retain non overlap part of overlapping range at two sides
  remove all ranges within overlap range
  Approach
  Complexity
  Time complexity: logn?
  Space complexity: logn?
 */

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
class RangeModuleInner {

    TreeMap<Integer, Integer> m = new TreeMap<>();

    RangeModuleInner() {

    }

    public void addRange(int left, int right) {
        // left : start, right: end
        // find overlap ranges, calc merge range, clear overlapped ranges, insert merge

        var L = m.floorEntry(left); // left possible overlap entry
        var R = m.floorEntry(right); // right possible overlap entry

        if (L != null && L.getValue() >= left) left = L.getKey(); // update overlap start
        if (R != null && R.getValue() > right) right = R.getKey(); // update overlap end

        m.subMap(left, right).clear(); // clear all overlapped entries
        m.put(left, right); // save final merged Query
    }

    public boolean queryRange(int left, int right) {

        var L = m.floorEntry(left);
        return L != null && L.getValue() >= right; // If there exist a range: start <+ left, end >= right
    }

    public void removeRange(int left, int right) {

        var L = m.floorEntry(left); //Left possible overlap entry
        var R = m.floorEntry(right); // right possible overlap entry

        if (L != null && L.getValue() > left) m.put(L.getKey(), left); // After removal, if anything left
        if (R != null && R.getValue() > right) m.put(right, R.getValue()); // After removal, if anything right

        //removal
        m.subMap(left, right).clear();
    }
}
