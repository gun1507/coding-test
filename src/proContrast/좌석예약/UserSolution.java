package proContrast.좌석예약;

import java.util.*;

public class UserSolution {

    private class Sinema implements Comparable<Sinema> {
        int sID, seatNum, emptySize;

        public Sinema(int sID, int seatNum, int emptySize) {
            this.sID = sID;
            this.seatNum = seatNum;
            this.emptySize = emptySize;
        }

        @Override
        public int compareTo(Sinema o) {
            return sID == o.sID ? seatNum - o.seatNum : sID - o.sID;
        }
    }

    private class Reservation {
        int rID, sID, seatNum, size, sum;

        public Reservation(int rID, int sID, int seatNum, int size, int sum) {
            this.rID = rID;
            this.sID = sID;
            this.seatNum = seatNum;
            this.size = size;
            this.sum = sum;
        }
    }

    final int[] ROWS = {-1, 1, 0, 0};
    final int[] COLS = {0, 0, -1, 1};
    final int DIV_MOD = 10;

    int N;
    int[][][] sinemaSeats;
    ArrayList<Map<Integer, Sinema>> emptys;
    HashMap<Integer, Reservation> reservations;
    ArrayList<TreeSet<Sinema>> emptyList;

    void init(int N) {
        this.N = N;

        sinemaSeats = new int[N+1][10][10];
        emptys = new ArrayList<>(N+1);
        reservations = new HashMap<>();
        emptyList = new ArrayList<>(N+1);

        for (int i = 0; i <= N; ++i) {
            emptys.add(new HashMap<>());
            emptyList.add(new TreeSet<>());
            Sinema s = new Sinema(i, 1, 100);
            emptyList.get(i).add(s);
        }
    }

    Solution.Result reserveSeats(int mID, int K) {
        Solution.Result res = new Solution.Result();
        int sID = -1;
        Sinema sinema = null;
        find: for (int i = 1; i <= N; ++i) {
            for(Sinema t : emptyList.get(i)) {
                if(t.emptySize >= K) {
                    sinema = t;
                    emptyList.get(i).remove(t);
                    emptys.get(i).remove(t.seatNum);
                    sID = i;
                    break find;
                }
            }
        }

        if(sID == -1) return res;

        res.id = sID;
        res.num = sinema.seatNum;
        reserve(sID, mID, K, sinema);

        boolean[][] check = new boolean[DIV_MOD][DIV_MOD];

        for (int i = 0; i < DIV_MOD; ++i) {
            for (int j = 0; j < DIV_MOD; ++j) {
                if (sinemaSeats[sID][i][j] != 0 || check[i][j])
                    continue;
                sinema = bfs(sID, i, j, check);
                emptyList.get(sID).add(sinema);
                emptys.get(sID).put(sinema.seatNum, sinema);
            }
        }
        return res;
    }

    Sinema bfs(int sID, int srow, int scol, boolean[][] check) {
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.offer(srow * DIV_MOD + scol);
        boolean[][] visited = new boolean[DIV_MOD][DIV_MOD];
        visited[srow][scol] = true;
        check[srow][scol] = true;
        int cnt = 0, minNum = 100;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int crow = cur / DIV_MOD;
            int ccol = cur % DIV_MOD;

            cnt++;
            // 새로운 좌석 번호 찾기
            minNum = Math.min(minNum, cur + 1);

            for (int i = 0; i < 4; i++) {
                int nrow = crow + ROWS[i];
                int ncol = ccol + COLS[i];

                if (nrow == -1 || nrow == DIV_MOD || ncol == -1 || ncol == DIV_MOD
                        || visited[nrow][ncol] || sinemaSeats[sID][nrow][ncol] != 0)
                    continue;

                visited[nrow][ncol] = true;
                check[nrow][ncol] = true;
                queue.offer(nrow * 10 + ncol);
            }
        }
        return new Sinema(sID, minNum, cnt);
    }

    void reserve(int sID, int mID, int K , Sinema sinema) {
        int srow = (sinema.seatNum - 1) / DIV_MOD;
        int scol = (sinema.seatNum - 1) % DIV_MOD;

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.offer(srow * DIV_MOD + scol);
        boolean[][] visited = new boolean[DIV_MOD][DIV_MOD];
        visited[srow][scol] = true;
        int cnt = 0;
        int sum = 0;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int crow = cur / DIV_MOD;
            int ccol = cur % DIV_MOD;

            cnt++;
            if(cnt > K) break;
            sum+=(cur+1);
            sinemaSeats[sID][crow][ccol] = mID;

            for (int i = 0; i < 4; i++) {
                int nrow = crow + ROWS[i];
                int ncol = ccol + COLS[i];

                if (nrow == -1 || nrow == DIV_MOD || ncol == -1 || ncol == DIV_MOD ||
                        visited[nrow][ncol] || sinemaSeats[sID][nrow][ncol] != 0)
                    continue;
                visited[nrow][ncol] = true;
                queue.offer(nrow * DIV_MOD +  ncol);
            }
        }

        reservations.put(mID, new Reservation(mID, sID, sinema.seatNum, K, sum));
    }

    Solution.Result cancelReservation(int mID) {
        Solution.Result res = new Solution.Result();

        Reservation r = reservations.get(mID);
        res.id = r.sID;
        res.num = r.sum;

        Sinema empty = cancel(r);

        emptyList.get(empty.sID).add(empty);
        emptys.get(empty.sID).put(empty.seatNum, empty);

        return res;
    }

    Sinema cancel(Reservation r) {
        int srow = (r.seatNum - 1) / DIV_MOD;
        int scol = (r.seatNum - 1) % DIV_MOD;

        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.offer(srow * DIV_MOD + scol);
        boolean[][] visited = new boolean[DIV_MOD][DIV_MOD];
        visited[srow][scol] = true;
        int cnt = 0, minNum = 100;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int crow = cur / DIV_MOD;
            int ccol = cur % DIV_MOD;

            if (emptys.get(r.sID).get(crow * DIV_MOD + ccol + 1) != null) {
                Sinema e = emptys.get(r.sID).get(crow * DIV_MOD + ccol + 1);
                emptyList.get(r.sID).remove(e);
                emptys.get(r.sID).remove(e.seatNum);
            }

            cnt++;

            sinemaSeats[r.sID][crow][ccol] = 0;

            minNum = Math.min(minNum, cur + 1);

            for (int i = 0; i < 4; i++) {
                int nrow = crow + ROWS[i];
                int ncol = ccol + COLS[i];
                if (nrow == -1 || nrow == DIV_MOD || ncol == -1 || ncol == DIV_MOD || visited[nrow][ccol])
                    continue;
                if (sinemaSeats[r.sID][nrow][ncol] != 0 && sinemaSeats[r.sID][nrow][ncol] != r.rID)
                    continue;
                visited[nrow][ncol] = true;
                queue.offer(nrow * DIV_MOD + ncol);
            }
        }
        return new Sinema(r.sID, minNum, cnt);
    }
}
