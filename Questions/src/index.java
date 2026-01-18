import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 998244353;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int t = fs.nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = fs.nextInt(), m = fs.nextInt();
            long[] a = new long[n + 1];
            long maxValue = 0;
            for (int i = 1; i <= n; i++) {
                a[i] = fs.nextLong();
                if (a[i] > maxValue) maxValue = a[i];
            }

            ArrayList<Integer>[] adj = new ArrayList[n + 1];
            HashSet<Integer>[] edgeSet = new HashSet[n + 1];
            for (int i = 1; i <= n; i++) {
                adj[i] = new ArrayList<>();
                edgeSet[i] = new HashSet<>();
            }

            int[] uList = new int[m];
            int[] vList = new int[m];
            for (int i = 0; i < m; i++) {
                int u = fs.nextInt();
                int v = fs.nextInt();
                adj[u].add(v);
                edgeSet[u].add(v);
                uList[i] = u;
                vList[i] = v;
            }

            HashMap<Long, ArrayList<Integer>> valueMap = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                valueMap.computeIfAbsent(a[i], k -> new ArrayList<>()).add(i);
            }

            long ans = 0;
            ArrayDeque<long[]> dq = new ArrayDeque<>();

            for (int i = 0; i < m; i++) {
                int startU = uList[i], startV = vList[i];
                ans++;
                dq.clear();
                dq.add(new long[]{startU, startV});
                while (!dq.isEmpty()) {
                    long[] p = dq.poll();
                    int prev = (int)p[0], curr = (int)p[1];
                    long target = a[prev] + a[curr];
                    if (target > maxValue) continue;
                    ArrayList<Integer> list = valueMap.get(target);
                    if (list == null) continue;
                    for (int nxt : list) {
                        if (edgeSet[curr].contains(nxt)) {
                            ans++;
                            if (ans >= MOD) ans -= MOD;
                            dq.add(new long[]{curr, nxt});
                        }
                    }
                }
            }

            sb.append(ans % MOD).append('\n');
        }
        System.out.print(sb);
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { in = is; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c; 
            while ((c = read()) <= ' ') ;
            long sgn = 1;
            if (c == '-') { sgn = -1; c = read(); }
            long x = c - '0';
            while ((c = read()) > ' ') x = x * 10 + (c - '0');
            return x * sgn;
        }

        int nextInt() throws IOException { return (int)nextLong(); }
    }
}
