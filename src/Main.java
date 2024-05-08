import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static int[] N = new int[3];
    static int[] answer = new int[3];
    static boolean[] visit = new boolean[3];

    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("./input/main.txt"));
        Scanner sc = new Scanner(System.in);

        for(int i = 0; i < 3; i++) {
            N[i] = sc.nextInt();
        }

        bfs2(0);
    }

//    private static void bfs1(int index) {
//        if(index == 3) {
//            System.out.println(answer[0] + ", " + answer[1] + ", " + answer[2]);
//        }else {
//            for(int i = 0; i < 3; i++) {
//                answer[index] = N[i];
//                bfs1(index + 1);
//            }
//        }
//    }

    private static void bfs2(int index) {
        if(index == 3) {
            System.out.println(answer[0] + ", " + answer[1] + ", " + answer[2]);
        }else {
            for(int i = 0; i < 3; i++) {
                if(!visit[i]) {
                    visit[i] = true;
                    answer[index] = N[i];
                    bfs2(index + 1);
                    visit[i] = false;
                }
            }
        }
    }
}
