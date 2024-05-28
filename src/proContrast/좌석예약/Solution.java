package proContrast.좌석예약;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    private final static int CMD_INIT       = 0;
    private final static int CMD_RESERVE    = 1;
    private final static int CMD_CANCEL     = 2;

    private static UserSolution usersolution = new UserSolution();

    public static final class Result
    {
        int id;
        int num;

        Result() {
            id = 0;
            num = 0;
        }
    }

    private static boolean run (BufferedReader br) throws Exception {
        Result res;
        int cmd, param1, param2;
        int ans1, ans2;

        boolean okay = false;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int Q = Integer.parseInt(st.nextToken());
        for (int i = 0; i < Q; ++i) {
            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());
            switch (cmd) {
                case CMD_INIT:
                    param1 = Integer.parseInt(st.nextToken());
                    usersolution.init(param1);
                    okay = true;
                    break;

                case CMD_RESERVE:
                    param1 = Integer.parseInt(st.nextToken());
                    param2 = Integer.parseInt(st.nextToken());
                    ans1 = Integer.parseInt(st.nextToken());
                    ans2 = Integer.parseInt(st.nextToken());
                    res = usersolution.reserveSeats(param1, param2);
                    if(res.id != ans1 || res.num != ans2)
                        okay = false;
                    break;

                case CMD_CANCEL:
                    param1 = Integer.parseInt(st.nextToken());
                    ans1 = Integer.parseInt(st.nextToken());
                    ans2 = Integer.parseInt(st.nextToken());
                    res = usersolution.cancelReservation(param1);
                    if (res.id != ans1 || res.num != ans2)
                        okay = false;
                    break;

                default:
                    okay = false;
                    break;
            }
        }

        return okay;
    }

    public static void main(String[] args) throws Exception{

        System.setIn(new FileInputStream("input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(line.nextToken());
        int MARK = Integer.parseInt(line.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}
