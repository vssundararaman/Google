import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 818. Race Car
 * Your car starts at position 0 and speed +1 on an infinite number line. Your car can go into negative positions. Your car drives automatically according to a sequence of instructions 'A' (accelerate) and 'R' (reverse):
 * <p>
 * When you get an instruction 'A', your car does the following:
 * position += speed
 * speed *= 2
 * When you get an instruction 'R', your car does the following:
 * If your speed is positive then speed = -1
 * otherwise speed = 1
 * Your position stays the same.
 * For example, after commands "AAR", your car goes to positions 0 --> 1 --> 3 --> 3, and your speed goes to 1 --> 2 --> 4 --> -1.
 * <p>
 * Given a target position target, return the length of the shortest sequence of instructions to get there.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: target = 3
 * Output: 2
 * Explanation:
 * The shortest instruction sequence is "AA".
 * Your position goes from 0 --> 1 --> 3.
 * Example 2:
 * <p>
 * Input: target = 6
 * Output: 5
 * Explanation:
 * The shortest instruction sequence is "AAARA".
 * Your position goes from 0 --> 1 --> 3 --> 7 --> 7 --> 6.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= target <= 104
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println(raceCar(3));
        System.out.println(raceCar(6));
        System.out.println(raceCar(8));

        System.out.println(raceCarDP(3));
        System.out.println(raceCarDP(6));
        System.out.println(raceCarDP(8));
    }

    static int raceCar(int target) {
        List<Integer[]> queue = new ArrayList<>();
        HashSet<Integer[]> visited = new HashSet<>();
        int moves = 0;
        return raceCar(target, queue, visited, moves);
    }

    /**
     * Method returns the target position
     *
     * @param target target
     * @param queue queue
     * @param visited visited
     * @param moves moves
     * @return target position
     */
    private static int raceCar(int target, List<Integer[]> queue, HashSet<Integer[]> visited, int moves) {
        queue.add(new Integer[]{0, 0, 1});

        while (queue.size() > 0) {

            moves = queue.get(0)[0];
            int position = queue.get(0)[1];
            int speed = queue.get(0)[2];

            // removing the current value from Queue
            queue.remove(0);

            if (position == target) {
                return moves;
            } else if (visited.contains(new Integer[]{position, speed})) {
                continue;
            } else {
                visited.add(new Integer[]{position, speed});
                queue.add(new Integer[]{moves + 1, position + speed, speed * 2});

                if ((((position + speed) > target) && (speed > 0)) || (((position + speed) < target) && (speed < 0))) {
                    queue.add(new Integer[]{moves + 1, position, speed > 0 ? -1 : 1});
                }
            }

        }

        return moves;
    }

    static int raceCarDP(int target) {
        int[] dp = new int[target + 1];

        Arrays.fill(dp, 1, dp.length, -1);
        return raceCarDP(target, dp);
    }

    private static int raceCarDP(int target, int[] dp) {

        if (dp[target] >= 0) return dp[target];

        dp[target] = Integer.MAX_VALUE;

        int x = 1;
        int j = 1;

        for (; j < target; j = (1 << ++x) - 1) {

            for (int q = 0, p = 0; p < j; p = (1 << ++q) - 1) {
                dp[target] = Math.min(dp[target], x + 1 + 1 + q + raceCarDP(target - (j - p), dp));
            }
        }

        dp[target] = Math.min(dp[target], x + (target == j ? 0 : 1 + raceCarDP(j - target, dp)));

        return dp[target];
    }
}