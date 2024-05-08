package codeTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class FightingRobot {

    static int N; // 맵 크기
    static int[][] map; // 맵 정보
    static int level = 2; // 레벨
    static List<int[]> bugs = new ArrayList<>(); // 벌레 위치
    static int exp = 0; // 경험치
    static int time = 0;
    static int[] monsterLocation;

    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("./input/fightingRobot.txt"));
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        map = new int[N][N];
        monsterLocation = new int[]{2, 2}; // 몬스터 위치

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int k = sc.nextInt();
                map[i][j] = k;
                if(k == 9) {
                    monsterLocation = new int[]{i, j};
                    map[i][j] = 0;
                }else if(level > k && k != 0) {
                    bugs.add(new int[]{i, j});
                }

            }
        }

        while (!bugs.isEmpty()) {
            nearByBugs(monsterLocation, bugs); // 가장 가까운 위치의 벌레 찾기
            findBugs(); // 죽일수 있는 몬스터 찾기
        }

        System.out.println(time);

    }

    private static void findBugs() {

        bugs = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j] < level && map[i][j] != 0) {
                    bugs.add(new int[]{i, j});
                }
            }
        }
    }

    public static void nearByBugs(int[] location, List<int[]> bugs) {
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        int min = Integer.MAX_VALUE;
        int[] finalPoint = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        List<int[]> equalDist = new ArrayList<>();

        for(int[] bug : bugs) {
            Queue<Node> q = new LinkedList<>();
            q.offer(new Node(location, 0));
            boolean visit[][] = new boolean[N][N];
            visit[location[0]][location[1]] = true;
            while (!q.isEmpty()) {
                Node poll = q.poll();
                if(poll.x == bug[0] && poll.y == bug[1]) {
                    if(min > poll.dist) {
                        min = poll.dist;
                        equalDist.add(new int[]{poll.x, poll.y});
                    }else if(min == poll.dist) {
                        equalDist.add(new int[]{poll.x, poll.y});
                    }
                }

                for(int i = 0; i < 4; i++) {
                    int nx = poll.x + dx[i];
                    int ny = poll.y + dy[i];
                    if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue; // 영역 밖일때
                    if(visit[nx][ny]) continue; // 이미 방문한 곳일때
                    if(map[nx][ny] > level) continue; // 나보다 큰 몬스터가 있을때
//                    System.out.println("[" + nx + ", " + ny  + "]");
                    visit[nx][ny] = true;
                    q.offer(new Node(new int[]{nx, ny}, poll.dist + 1));

                }

            }
        }

        if(equalDist.size() > 1) {
            for(int[] point : equalDist) {
                if(finalPoint[0] > point[0]) {
                    finalPoint = point; // 가장 위에
                }else if(finalPoint[1] > point[1]) {
                    finalPoint = point; // 가장 왼쪽에
                }
            }
        }else {
            finalPoint = equalDist.get(0);
        }

        time = time + min;
        map[finalPoint[0]][finalPoint[1]] = 0;
        monsterLocation = new int[]{finalPoint[0], finalPoint[1]};
        exp++;
        if(level == exp) {
            exp = 0;
            level++;
        }
    }

    public static class Node {
        int x;
        int y;
        int dist;

        public Node(int[] point, int dist) {
            this.x = point[0];
            this.y = point[1];
            this.dist = dist;
        }

    }

}
