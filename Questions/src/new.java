import java.io.*;
import java.util.*;

public class Main {
    static class DSU {
        int[] parent, size;
        DSU(int n) {
            parent = new int[n+1];
            size = new int[n+1];
            for(int i=1;i<=n;i++){parent[i]=i; size[i]=1;}
        }
        int find(int x){
            while(parent[x]!=x){x=parent[x]=parent[parent[x]];}
            return x;
        }
        boolean union(int a,int b){
            a=find(a); b=find(b);
            if(a==b) return false;
            if(size[a]<size[b]){int t=a;a=b;b=t;}
            parent[b]=a;
            size[a]+=size[b];
            return true;
        }
    }

    static class Edge {
        int u,v;
        long w;
        Edge(int u,int v,long w){
            this.u=u; this.v=v; this.w=w;
        }
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1<<16];
        private int ptr=0,len=0;
        FastScanner(InputStream is){in=is;}
        private int read() throws IOException{
            if(ptr>=len){
                len=in.read(buffer); ptr=0;
                if(len<=0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException{
            int c;
            while((c=read())<=32);
            long sgn=1;
            if(c=='-'){sgn=-1; c=read();}
            long x=c-'0';
            while((c=read())>32) x=x*10+(c-'0');
            return x*sgn;
        }
        int nextInt() throws IOException {return (int)nextLong();}
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();
        int t = fs.nextInt();

        while(t-- > 0){
            int n = fs.nextInt();
            long[] a = new long[n+1];
            long[] c = new long[n+1];

            for(int i=1;i<=n;i++) a[i] = fs.nextLong();
            for(int i=1;i<=n;i++) c[i] = fs.nextLong();

            int[] p = new int[n];
            for(int i=0;i<n;i++) p[i] = fs.nextInt();

            ArrayList<Edge> edges = new ArrayList<>();
            for(int i=1;i<n;i++){
                long w = Math.min(c[i], c[i+1]);
                edges.add(new Edge(i, i+1, w));
            }

            PriorityQueue<Edge> pq = new PriorityQueue<>((x,y)->Long.compare(x.w,y.w));
            pq.addAll(edges);

            DSU dsu = new DSU(n);
            long total = 0;

            while(!pq.isEmpty()){
                Edge e = pq.poll();
                if(dsu.union(e.u, e.v)) total += e.w;
            }

            out.append(total).append(" ");

            pq = new PriorityQueue<>((x,y)->Long.compare(x.w,y.w));
            for(int i=1;i<n;i++){
                long w = Math.min(c[i], c[i+1]);
                pq.add(new Edge(i, i+1, w));
            }
            dsu = new DSU(n);
            total = 0;

            while(!pq.isEmpty()){
                Edge e = pq.peek();
                if(dsu.find(e.u)==dsu.find(e.v)) { pq.poll(); continue; }
                total += e.w;
                dsu.union(e.u, e.v);
                pq.poll();
            }

            long[] currentCost = c.clone();
            for(int step=0;step<n;step++){
                int idx = p[step];
                currentCost[idx] = 0;

                if(idx>1){
                    pq.add(new Edge(idx-1, idx, Math.min(currentCost[idx-1], currentCost[idx])));
                }
                if(idx<n){
                    pq.add(new Edge(idx, idx+1, Math.min(currentCost[idx], currentCost[idx+1])));
                }

                while(!pq.isEmpty()){
                    Edge e = pq.peek();
                    long w = Math.min(currentCost[e.u], currentCost[e.v]);
                    if(dsu.find(e.u) == dsu.find(e.v)) {
                        pq.poll();
                        continue;
                    }
                    if(w != e.w){
                        pq.poll();
                        pq.add(new Edge(e.u, e.v, w));
                        continue;
                    }
                    dsu.union(e.u, e.v);
                    total += w;
                    pq.poll();
                }

                out.append(total).append(" ");
            }

            out.append("\n");
        }

        System.out.print(out);
    }
}
