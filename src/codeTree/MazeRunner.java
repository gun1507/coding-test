package codeTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeRunner {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("./input/input.txt"));
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 미로 크기
        int M = sc.nextInt(); // 참가자 수
        int K = sc.nextInt(); // 게임 시간

        int[][] map = new int[N][N]; // 미로 맵

        // map
        for(int r = 0; r < N; r++) {
            for(int c = 0; c < N; c++ ) {
                map[r][c] = sc.nextInt();
            }
        }
        // person
        for(int i = 0; i < M; i++) {
            map[sc.nextInt() - 1][sc.nextInt() - 1] = -1;
        }
        // exit
        map[sc.nextInt() - 1][sc.nextInt() - 1] = -2;
    }
}
