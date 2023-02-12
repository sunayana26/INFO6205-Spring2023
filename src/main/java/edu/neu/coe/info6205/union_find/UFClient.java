package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class UFClient {
    private UF_HWQUPC uf;
    private int count;

    public UFClient(int n) {
        uf = new UF_HWQUPC(n);
        count = 0;
    }

    public int count() {
        Random random = new Random();
        while (uf.getCount() > 1) {
            int p = random.nextInt(uf.getCount());
            int q = random.nextInt(uf.getCount());
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        for(int n=10; n<=100; n+=10){
            UFClient ufClient = new UFClient(n);
            Instant start = Instant.now();
            int connections = ufClient.count();
            Instant end = Instant.now();
            System.out.println("N value: " + n);
            System.out.println("Number of connections: " + connections);
            System.out.println("Time taken: " + Duration.between(start, end).toMillis() + " milliseconds");
        }
    }
}


