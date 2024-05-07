package src.dijkstra;
import java.util.*;

public class Test1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); //局域网内电脑的个数

        // 邻接表
        HashMap<Integer, ArrayList<int[]>> graph = new HashMap<>();

        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(); // 出发点
            int v = sc.nextInt(); // 目标点
            int w = sc.nextInt(); // 出发点到达目标点的耗时

            graph.putIfAbsent(u, new ArrayList<>());
            graph.get(u).add(new int[] {v, w});
        }

        // 记录源点到其他剩余的最短耗时
        int[] dist = new int[n + 1];
        // 初始时，假设源点不可达其他剩余点，即源点到达其他点的耗时无限大
        Arrays.fill(dist, Integer.MAX_VALUE);

        // 源点
        int src = sc.nextInt();
        // 源点到达源点的耗时为0
        dist[src] = 0;

        // 优先队列needCheck中记录的其实是：已被探索过的路径的终点（路径指的是源点->终点）
        // 优先队列优先级规则是，路径终点的权重（即源点->终点的耗时）越小越优先
        PriorityQueue<Integer> needCheck = new PriorityQueue<>((a, b) -> dist[a] - dist[b]);
        // 初始被探索过的路径只有源点本身
        needCheck.add(src);

        // 记录对应点是否在needCheck中
        boolean[] visited = new boolean[n + 1];
        visited[src] = true;

        while (needCheck.size() > 0) {
            // 取出最优路径的终点（耗时最少的路径）作为新的起点
            int cur = needCheck.poll();
            visited[cur] = false;

            // 如果cur有可达的其他点
            if (graph.containsKey(cur)) {
                for (int[] next : graph.get(cur)) {
                    // v是可达的其他点
                    // w是cur->v的耗时
                    int v = next[0], w = next[1];

                    // 那么如果从源点到cur点的耗时是dist[cur]，那么源点到v点的耗时就是dist[cur] + w
                    int newDist = dist[cur] + w;
                    // 而源点到v的耗时之前是dist[v]，因此如果newDist < dist[v]，则找到更少耗时的路径
                    if (dist[v] > newDist) {
                        // 更新源点到v的路径，即更新v点权重
                        dist[v] = newDist;
                        // 如果v点不在needCheck中，则加入，因为v作为终点的路径需要加入到下一次最优路径的评比中
                        if (!visited[v]) {
                            visited[v] = true;
                            needCheck.add(v);
                        }
                    }
                }
            }
        }

        // dist记录的是源点到达其他各点的最短耗时，我们取出其中最大的就是源点走完所有点的最短耗时
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, dist[i]);
        }

        // 如果存在某个点无法到达，则源点到该点的耗时为Integer.MAX_VALUE
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}