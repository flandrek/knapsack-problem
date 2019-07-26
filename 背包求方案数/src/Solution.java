import java.util.Arrays;
import java.util.Scanner;

/**
 * @author wangzhe
 * @date 2019/7/26 16:17
 * <p>
 * 有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。
 * 第 i 件物品的体积是 vi，价值是 wi。
 * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
 * 输出 最优选法的方案数。注意答案可能很大，请输出答案模 109+7 的结果。
 * <p>
 * 输入格式：
 * 第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。
 * 接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。
 * 输出格式：
 * 输出一个整数，表示 方案数 模 109+7 的结果。
 * <p>
 * 数据范围
 * 0 < N, V ≤ 1000
 * 0< vi, wi ≤ 1000
 * <p>
 * 输入样例
 * 4 5
 * 1 2
 * 2 4
 * 3 4
 * 4 6
 * 输出样例：
 * 2
 */
public class Solution {

    /**
     * 和 01背包相同，在遍历的过程中，将所有体积的最佳方案数记录下来，最后返回。
     */
    //时间复杂度：O (N * V)
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int V = input.nextInt();

        int[] v = new int[N + 1];
        int[] w = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            v[i] = input.nextInt();
            w[i] = input.nextInt();
        }
        input.close();

        int[] dp = new int[V + 1];
        // 当背包体积为1~V的时候，最佳方案的总数
        int[] count = new int[V + 1];

        // 初始化，此时的 count代表 i=0，即没有物品可以选择时
        // 什么都不选，也是一种方案
        Arrays.fill(count, 1);

        final int MOD = 1000000007;

        for (int i = 1; i <= N; i++) {
            for (int j = V; j >= v[i]; j--) {
                // 用来记录当前的最佳方案价值
                int t = Math.max(dp[j], dp[j - v[i]] + w[i]);
                int s = 0;  // 用来记录最佳方案数
                // 最佳方案和不选当前物品相同
                if (t == dp[j]) s += count[j];
                // 最佳方案和选当前物品相同
                if (t == dp[j - v[i]] + w[i]) s += count[j - v[i]];
                s %= MOD;
                dp[j] = t;
                count[j] = s;
            }
        }
        System.out.println(count[V]);
    }
}
