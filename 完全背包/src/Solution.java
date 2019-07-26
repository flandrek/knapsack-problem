import java.util.Scanner;

/**
 * @author wangzhe
 * @date 2019/7/24 16:20
 *
 * 有 N 种物品和一个容量是 V 的背包，每种物品都有无限件可用。
 * 第 i 种物品的体积是 vi，价值是 wi。
 * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
 * 输出最大价值。
 * <p>
 * 输入格式:
 * 第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。
 * 接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 种物品的体积和价值。
 * <p>
 * 输出格式:
 * 输出一个整数，表示最大价值。
 * <p>
 * 数据范围
 * 0 < N, V ≤ 1000
 * 0 < vi, wi ≤ 1000
 * 输入样例:
 * 4 5
 * 1 2
 * 2 4
 * 3 4
 * 4 5
 * 输出样例：
 * 10
 */
public class Solution {

    /**
     * f[i] 表示总体积是 i 的情况下，最大价值是多少
     * result = max{f[0,1...,m]}
     * <p>
     *  for(int i = 0; i <= n; i++){
     *      1: 时间复杂度 O（n^2）
     *      for(int j = v[i]; j <= V; j++)
     *          f[j] = Math.max(f[j], f[j-v[i]] + w[i]);
     *   从小到大枚举时， f[j] 用的是 f[j-v[i]] 的状态，是被算过了
     *   f[j-v[i]] 表示考虑前 i 个物品（包含第i个物品），体积是 j-v[i] 的情况，
     *      可能已经包含了若干个当前第 i 个物品了
     *      也就是说 i 之后的循环 i+1,i+2.. 还是会选择第i个物品
     *      从小到大考虑了第 i 个物品的比较，从大到小没有考虑
     *
     *  1 等价与 2
     *      2: 时间复杂度 O（n^3）
     *      for(int j = V; j >= v[i]; j--){
     *          for(int k = 0; k * v[i] <= j; k++)
     *              // 所有的 f[j-k*v[i]] 都不包含第二个物品
     *              f[j] = Math.max(f[j], f[j-k*v[i]] + k*w[i]);
     *      }
     *  }
     *
     */
    // 时间复杂度 O (N * V)
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

        for (int i = 1; i <= N; i++) {
            // 接下来有 N 行，每行有两个整数:v[i],w[i]，用空格隔开，分别表示第i件物品的体积和价值
            v[i] = reader.nextInt();
            w[i] = reader.nextInt();
        }
        reader.close();

        int[] dp = new int[V + 1];
        dp[0] = 0;
        for (int i = 1; i <= N; i++) {
            // 第一种写法，时间复杂度 O (N * V)
            for (int j = v[i]; j <= V; j++) {
                dp[j] = Math.max(dp[j], dp[j - v[i]] + w[i]);
            }
            // 第二种写法，转换成了 01 背包问题
            for(int j = V; j >= v[i]; j--){
                for(int k = 0; k * v[i] <= j; k++)
                    // 所有的 f[j-k*v[i]] 都不包含第 i 个物品
                    dp[j] = Math.max(dp[j], dp[j-k*v[i]] + k*w[i]);
            }
        }
        System.out.println(dp[V]);
    }
}
