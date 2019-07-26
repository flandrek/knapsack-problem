import java.util.Scanner;

/**
 * @author wangzhe
 * @date 2019/7/24 15:46
 *
 * 有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。
 * 第 i 件物品的体积是 vi，价值是 wi。
 * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
 * 输出最大价值。
 * <p>
 * 输入格式:
 * 第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。
 * 接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。
 * 输出格式:
 * <p>
 * 输出一个整数，表示最大价值。
 * 数据范围
 * 0 < N, V ≤ 1000
 * 0< vi, wi ≤ 1000
 * <p>
 * 输入样例:
 * 4 5
 * 1 2
 * 2 4
 * 3 4
 * 4 5
 * 输出样例：
 * 8
 */
public class Solution {

    /**
     *  定义一个二阶矩阵dp[N+1][V+1],
     *    这里之所以要N+1和V+1，是因为第0行表示只能选择第0个物品的时候，即没有物品的时候
     *    第0列表示背包的体积为0的时候，即不能装任何东西的时候
     *
     *    dp[i][j]表示在 只能选择前i个物品，背包容量为j的情况下，背包中物品的最大价值
     *    对于dp[i][j]有两种情况：
     *       1. 不选择当前的第i件物品/第i件物品比背包容量要大，则dp[i][j] = dp[i-1][j]
     *       2. 选择当前的第i件物品（潜在要求第i件物品体积小于等于背包总容量），则能装入的物品最大价值为：
     *           当前物品的价值 加上 背包剩余容量在只能选前i-1件物品的情况下的最大价值
     *            dp[i][j] = dp[i-1][j-v[i]] + w[i]
     *    dp[i][j]在两种情况中选择比较大的情况作为当前的最优解；
     *       即：
     *        if(j >= v[i]):
     *            dp[i][j] = max(dp[i-1][j], dp[i-1][j-v[i]] + w[i])
     *        else:
     *            dp[i][j] = dp[i-1][j]
     */

    // 时间复杂度: O (N * V)
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

        // 正式工作的代码
        int[][] dp = new int[N + 1][V + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (j >= v[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - v[i]] + w[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println(dp[N][V]);
    }

    /**
     *  优化空间成一维数组
     *  注意，这里第二层循环的时候，还是小到大循环的话，那么
     *      dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-v[i]] + w[i])
     *      实际上变成了
     *      dp[i][j] = Math.max(dp[i][j], dp[i][j-v[i]] + w[i]);
     *
     *      因为i-1的值已经在前面被更新过了，覆盖了
     *      为了避免这个问题，所以要逆序更新，即先更新第i个，然后更新第i-1个，从而保证第i-1个不被覆盖
     *
     *      如果不逆序的话，输出结果为10，dp数组实际为：
     *         0 0 0 0 0 0
     *         0 2 4 6 8 10
     *         0 2 4 6 8 10
     *         0 2 4 6 8 10
     *         0 2 4 6 8 10
     */
    // 时间复杂度 O（n^2）
    public static void main1(String[] args) {
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
            for (int j = V; j >= v[i]; j--) { //此处加入了 j >= v[i] 判断，因此下面不用再进行判断
                dp[j] = Math.max(dp[j], dp[j - v[i]] + w[i]);
            }
        }
        System.out.println(dp[V]);
    }
}
