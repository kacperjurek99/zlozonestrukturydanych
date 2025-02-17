package generators;

public class GfsrGeneratorPar5 {
    private int p = 521;
    private int q1 = 86;
    private int q2 = 197;
    private int q3 = 447;
    private int w = 32;

    private long[] state = new long[p];
    private int stateI;


    public void initGfsr5(long s) {
        long[] x = new long[p];
        s &= 0xffffffffL;
        for (int i = 0; i < p; i++) {
            x[i] = s >> 31;
            s = 1664525L * s + 1L;
            s &= 0xffffffffL;
        }
        for (int k = 0, i = 0; i < p; i++) {
            state[i] = 0L;
            for (int j = 0; j < w; j++) {
                state[i] <<= 1;
                state[i] |= x[k];
                x[k] ^= x[(k + q1) % p]^x[(k + q2) % p]^x[(k + q2) % p];
                k++;
                if (k == p) k = 0;
            }
        }
        stateI = 0;
    }


    public long gfsr5() {
        int i;
        int p0, p1,p2,p3;
        if (stateI >= p) {
            stateI = 0;
            p0 = 0;
            p1 = q1;
            p2 = q2;
            p3 = q3;
            for (i = 0; i < (p - q3); i++) {
                state[p0] ^= state[p1]^state[p2]^state[p3];
                p0 += 1;
                p1 += 1;
                p2 += 1;
                p3 += 1;
            }
            p3 = 0;
            for (; i < p-q2; i++) {
                state[p0] ^= state[p1]^state[p2]^state[p3];
                p0 += 1;
                p1 += 1;
                p2 += 1;
                p3 += 1;
            }
            p2 = 0;
            for (; i < p-q1; i++) {
                state[p0] ^= state[p1]^state[p2]^state[p3];
                p0 += 1;
                p1 += 1;
                p2 += 1;
                p3 += 1;
            }
            p1 = 0;
            for (; i < p; i++) {
                state[p0] ^= state[p1]^state[p2]^state[p3];
                p0 += 1;
                p1 += 1;
                p2 += 1;
                p3 += 1;
            }
        }
        return state[stateI++];
    }


    public int gfsr531(int s) {
        return (int) (gfsr5() >> (32-s));
    }
}
