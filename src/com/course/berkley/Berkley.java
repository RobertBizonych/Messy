package com.course.berkley;

import java.util.ArrayList;
import java.util.Arrays;

public class Berkley {
    private int[] c;
    private int[] temp;
    private int[] bytes;
    private int[] sequence;
    private int N;
    private int l;
    private int m;
    private ArrayList<int[]> iterations;

    public Berkley(int[] sequence) {
        N = sequence.length;
        c = new int[N];
        temp = new int[N];
        bytes = new int[N];
        this.sequence = sequence;
        iterations = new ArrayList<int[]>();
        bytes[0] = 1;
        c[0] = 1;
        l = 0;
        m = -1;
    }

    public void encode() {
        for (int n = 0; n < N; n++) {
            int d = 0;
            for (int i = 0; i <= l; i++) {
                d ^= c[i] * sequence[n - i];
            }
            if (d % 2 != 0) {
                System.arraycopy(c, 0, temp, 0, N);
                int N_M = n - m;
                for (int j = 0; j < N - N_M; j++) {
                    c[N_M + j] ^= bytes[j];

                }
                if (l <= n / 2) {
                    l = n + 1 - l;
                    m = n;
                    System.arraycopy(temp, 0, bytes, 0, N);
                }
            }
            iterations.add(c);

        }
    }

    @Override
    public String toString() {
        return "Бітове поле для функції зворотнього зв’язку: " + Arrays.toString(c);
    }
}
