package src.dijkstra;

import java.util.*;

public class Test2 {
    //该问题尝试用最短路径方式解决,发现超时,看题解应该是需要用动态规划来实现;
    public static void main(String[] args) {
        int[][] arr = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        findCheapestPrice(3, arr,0, 2 ,1);
    }


    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // n个城市
        // flights的0,1,2分别表示 from to price;
        // src表示出发地
        // dst表示目的地
        // k表示经历的站点数量

        int dist[] = new int[n]; //创建一个数组用来记录源点到其的距离;
        Arrays.fill(dist, Integer.MAX_VALUE);
        HashMap<Integer, ArrayList<int []>> map = new HashMap();// 构建一个图;
        for(int[]u :flights) {
            int from = u[0];
            int to = u[1];
            int price = u[2];
            map.putIfAbsent(from, new ArrayList<>());
            map.get(from).add(new int[]{to, price});
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a,b) -> dist[a] - dist[b]);
        boolean visited[] = new boolean[n];

        //基础准备完毕,开始检查
        visited[src] = true;
        dist[src] = 0;
        priorityQueue.add(src);
        while(priorityQueue.size() > 0) {
            Integer poll = priorityQueue.poll();
            visited[poll] = false; //从队列中取出的点
            if(map.containsKey(poll)) {
                ArrayList<int[]> ints = map.get(poll);
                for(int []u: ints) {
                    int thdDst = u[0]; //当前点到此点的距离
                    int cost = u[1];  //当前点到此点的时间消耗
                    int nowCost = dist[poll] + cost; // src点到此点 = src点到当前点 + 当前点到此点
                    if(nowCost < dist[thdDst]) {
                        dist[thdDst] = nowCost;
                    }
                    if(!visited[thdDst]) {
                        visited[thdDst] = true;
                        priorityQueue.add(thdDst);
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dist[i]);
        }
// 如果存在某个点无法到达，则源点到该点的耗时为Integer.MAX_VALUE
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
        return ans;
    }
}
