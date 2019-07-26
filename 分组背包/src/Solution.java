import java.util.Scanner;

/**
 * @author wangzhe
 * @date 2019/7/25 18:14
 * <p>
 * 有 N 组物品和一个容量是 V 的背包。
 * <p>
 * 每组物品有若干个，同一组内的物品最多只能选一个。
 * 每件物品的体积是 vij，价值是 wij，其中 i 是组号，j 是组内编号。
 * 求解将哪些物品装入背包，可使物品总体积不超过背包容量，且总价值最大。
 * 输出最大价值。
 * <p>
 * 输入格式：
 * 第一行有两个整数 N，V，用空格隔开，分别表示物品组数和背包容量。
 * <p>
 * 接下来有 N 组数据：
 * 每组数据第一行有一个整数 Si，表示第 i 个物品组的物品数量；
 * 每组数据接下来有 Si 行，每行有两个整数 vij,wij，用空格隔开，分别表示第 i 个物品组的第 j 个物品的体积和价值；
 * 输出格式：
 * 输出一个整数，表示最大价值。
 * <p>
 * 数据范围
 * 0 < N, V ≤ 100
 * 0 < Si ≤ 100
 * 0 < vij, wij ≤ 100
 * <p>
 * 输入样例
 * 3 5
 * 2
 * 1 2
 * 2 4
 * 1
 * 3 4
 * 1
 * 4 5
 * 输出样例：
 * 8
 */
public class Solution {

    /**
     * 为01背包的一个变种，在旋转的时候多一层判断，有 s 种选法。
     * 多重背包也可以看成是分组背包的一个特例。
     *  for(int i = 0; i < N; i++){
     *      for(int j = m; j >= v[i]; j--){
     *          f[j] = max{f[j], f[j-v[0]] + w[0], ... , f[j-v[s-1]] + w[s-1]};
     *      }
     *  }
     */

    //时间复杂度：O (N * V * S)
    public static void main(String[] args) {
        // 读入数据的代码
        Scanner reader = new Scanner(System.in);
        // 物品的数量为N
        int N = reader.nextInt();
        // 背包的容量为V
        int V = reader.nextInt();

        int[] dp = new int[V + 1];

        for (int i = 1; i <= N; i++) {
            // 分行读入s组数据的容量和价值
            int si = reader.nextInt();
            int[] v = new int[si + 1];
            int[] w = new int[si + 1];
            for (int s = 0; s < si; s++) {
                v[s] = reader.nextInt();
                w[s] = reader.nextInt();
            }
            for (int j = V; j >= 0; j--) {
                for (int k = 0; k < si; k++) {
                    if (j >= v[k]) {    // 要保证当前容量大于 v[k] 否则会出现数组越界
                        dp[j] = Math.max(dp[j], dp[j - v[k]] + w[k]);
                    }
                }
            }
        }
        System.out.println(dp[V]);
    }
}
