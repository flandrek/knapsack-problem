import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author wangzhe
 * @date 2019/7/25 15:34
 * <p>
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
 * 0 < N ≤ 1000
 * 0 < V ≤ 2000
 * 0 < vi,wi,si ≤ 2000
 * <p>
 * 提示：
 * 本题考查多重背包的二进制优化方法。
 * <p>
 * 输入样例
 * 4 5
 * 1 2 3
 * 2 4 1
 * 3 4 3
 * 4 5 2
 * 输出样例：
 * 10
 */
public class Solution1 {

    /**
     * 二进制优化，考虑把多重背包问题转换成 01背包问题。
     * 考虑用二进制表示一个数
     * 例：
     * 7
     * 可以用 1，2，4 表示
     * 0 = 0
     * 1 = 1
     * 2 = 2
     * 3 = 1 + 2
     * 4 = 4
     * 5 = 1 + 4
     * 6 = 2 + 4
     * 7 = 1 + 2 + 4
     * <p>
     * 对于数 s
     * s - 1 - 2 - 4 ....
     * 0 ~ s 可以由一系列 2 的整次幂组成
     * 所有这些幂的选或者不选的状态，就可以把所有 0 ~ s 的情况表示出来
     * <p>
     * 这样就不用把每个物品分成 s 份，而是分成了 log(s) 份
     * 时间复杂度从 O(n) 变为 O(log(n))
     */

    // 时间复杂度：O ( N *  log(S) * V )
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int V = in.nextInt();
        // 存放拆分成二进制组合后的体积
        List<Integer> vol = new ArrayList<>();
        // 存放拆分成二进制组合后的价值
        List<Integer> worth = new ArrayList<>();

        for(int i = 0; i < N; i++){
            int vi = in.nextInt();
            int wi = in.nextInt();
            int si = in.nextInt();

            // 拆分成二进制进行优化
            for(int j = 1; j < si; j *= 2){
                si -= j;
                vol.add(vi*j);
                worth.add(wi*j);
            }
            // 不为0说明还有剩余的，也要加进去
            if(si > 0){
                vol.add(vi*si);
                worth.add(wi*si);
            }
        }

        // 和01背包相同，注意要用拆分后的容量来判断
        int[] dp = new int[V+1];
        for(int i = 0; i < vol.size(); i++){
            for(int j = V; j >= vol.get(i); j--){
                dp[j] = Math.max(dp[j], dp[j-vol.get(i)] + worth.get(i));
            }
        }
        System.out.println(dp[V]);
    }

}
