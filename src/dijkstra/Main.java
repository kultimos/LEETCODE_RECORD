package src.dijkstra;

import java.util.*;

public class Main{
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //共有n台电脑
        int l = sc.nextInt(); //共有l条感染路线
        int array[][] = new int[l][3];
        for(int i=0;i<l;i++) {
            array[i][0] = sc.nextInt();
            array[i][1] = sc.nextInt();
            array[i][2] = sc.nextInt();
        }
        int src = sc.nextInt();
        //构建一个邻接表来记录信息
        Map<Integer,List<int[]>> map = new HashMap();
        for(int i=0;i<l;i++) {
            int from = array[i][0];
            int to = array[i][1];
            int cost = array[i][2];
            map.putIfAbsent(from, new ArrayList());
            List tempList = map.get(from);
            tempList.add(new int[]{to,cost});
            map.put(from, tempList);
        }

        int dist[] = new int[n]; // dist数组用于记录src点到各点之间的距离
        Arrays.fill(dist, -1); // dist数组初始化为-1
        //创建一个优先队列, 用来保证优先出队的是消耗时间更短的线路;
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b) -> dist[a]-dist[b]);
        boolean visit[] = new boolean[n]; //用于监控队列中包含哪些元素;

        //从src开始
        dist[src] = 0;
        queue.add(src);
        visit[src] = true;

        //优先队列只要有元素,就表示还有电脑未被感染
        while(queue.size() > 0) {
            int now = queue.poll();
            visit[now] = false;
            if(map.containsKey(now)) {
                List<int[]> list = map.get(now);
                for(int[] u: list) {
                    int to = u[0];
                    int cost = u[1];
                    if(dist[to] == -1) {
                        dist[to] = cost + dist[now];
                    } else if(dist[to] > cost + dist[now]) {
                        dist[to] = cost + dist[now];
                    }
                    if(!visit[to]) {
                        queue.add(to);
                        visit[to] = true;
                    }
                }
            }
        }
        boolean flag = false;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<n;i++) {
            if(dist[i] == -1) {
                flag = true;
            } else {
                if(dist[i]<min) {
                    min = dist[i];
                }
            }
        }

        if(flag) {
            System.out.println(-1);
        } else {
            System.out.println(min);
        }

    }
}
