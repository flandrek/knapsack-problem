import java.util.Scanner;

/**
 * @author wangzhe
 * @date 2019/7/24 17:49
 *
 * 有 N 种物品和一个容量是 V 的背包。
 * 第 i 种物品最多有 si 件，每件体积是 vi，价值是 wi。
 * 求解将哪些物品装入背包，可使物品体积总和不超过背包容量，且价值总和最大。
 * 输出最大价值。
 * <p>
 * 输入格式:
 * 第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。
 * 接下来有 N 行，每行三个整数 vi,wi,si，用空格隔开，分别表示第 i 种物品的体积、价值和数量。
 * <p>
 * 输出格式:
 * 输出一个整数，表示最大价值。
 * <p>
 * 数据范围
 * 0 < N,V ≤ 100
 * 0 < vi,wi,si ≤ 100
 * 输入样例
 * 4 5
 * 1 2 3
 * 2 4 1
 * 3 4 3
 * 4 5 2
 * 输出样例：
 * 10
 */
public class Solution {

    /**
     * 结合 01 背包和完全背包，01 背包只有两种情况，选或者不选，多重背包可以选择 0,1,2...,s ，有 s+1 种情况。
     * f[i] 总体积是 i 的情况下，最大价值是多少
     * <p>
     *  for(int i = 0; i <= N; i++){
     *      for(int j = V; j >= v[i]; j--)
     *      f[j] = max(f[j], f[j-v[i]] + w[i], f[j-2*v[i]] + 2*w[i], .....);
     *      // 共有 s+1 种选法
     *  }
     *
     *  若初始化 f[i] = 0 , res = f[m]
     *  若初始化 f[0] = 0 , f[i] = -INF , res = max{f[0~m]}
     */

    // 时间复杂度 O（N * V * S）
    public static void main(String[] args) {
        // 读入数据的代码
        Scanner reader = new Scanner(System.in);
        // 物品的数量为N
        int N = reader.nextInt();
        // 背包的容量为V
        int V = reader.nextInt();

        // 一个长度为N的数组，第i个元素表示第i个物品的体积；
        int[] v = new int[N + 1];
        // 一个长度为N的数组，第i个元素表示第i个物品的价值；
        int[] w = new int[N + 1];
        // 一个长度为N的数组，第i个元素表示第i个物品的个数；
        int[] s = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            v[i] = reader.nextInt();
            w[i] = reader.nextInt();
            s[i] = reader.nextInt();
        }
        reader.close();

        int[] dp = new int[V + 1];
        dp[0] = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = V; j >= 0; j--) {
                // k * v[i] <= j 进行剪枝优化
                for (int k = 1; k <= s[i] && k * v[i] <= j; k++)
                    dp[j] = Math.max(dp[j], dp[j - k * v[i]] + k * w[i]);
            }
        }
        System.out.println(dp[V]);
    }

}
