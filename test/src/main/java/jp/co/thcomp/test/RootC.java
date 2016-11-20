package jp.co.thcomp.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jp.co.thcomp.reincarnation.ReincarnationHelper;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

@ReincarnationHelper.TargetClass
public class RootC extends RootB {
    public byte[] publicArrayA = new byte[10];
    public char[] publicArrayB = new char[10];
    public short[] publicArrayC = new short[10];
    public int[] publicArrayD = new int[10];
    public long[] publicArrayE = new long[10];
    public float[] publicArrayF = new float[10];
    public double[] publicArrayG = new double[10];
    public String[] publicArrayH = new String[10];
    public SubA[] publicArrayI = new SubA[10];
    public byte[] publicArrayANull = null;
    public char[] publicArrayBNull = null;
    public short[] publicArrayCNull = null;
    public int[] publicArrayDNull = null;
    public long[] publicArrayENull = null;
    public float[] publicArrayFNull = null;
    public double[] publicArrayGNull = null;
    public String[] publicArrayHNull = null;
    public SubA[] publicArrayINull = null;

    public void initialize() {
        super.initialize();

        for (byte i = 0; i < 10; i++) {
            publicArrayA[i] = i;
            publicArrayB[i] = (char) i;
            publicArrayC[i] = i;
            publicArrayD[i] = i;
            publicArrayE[i] = i;
            publicArrayF[i] = i;
            publicArrayG[i] = i;
            publicArrayH[i] = String.valueOf(i);
            publicArrayI[i] = new SubA();
            publicArrayI[i].initialize();
        }
    }

    public boolean equalsOnlyPublic(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equalsOnlyPublic(o)) return false;

        RootC rootC = (RootC) o;

        if (!Arrays.equals(publicArrayA, rootC.publicArrayA)) return false;
        if (!Arrays.equals(publicArrayB, rootC.publicArrayB)) return false;
        if (!Arrays.equals(publicArrayC, rootC.publicArrayC)) return false;
        if (!Arrays.equals(publicArrayD, rootC.publicArrayD)) return false;
        if (!Arrays.equals(publicArrayE, rootC.publicArrayE)) return false;
        if (!Arrays.equals(publicArrayF, rootC.publicArrayF)) return false;
        if (!Arrays.equals(publicArrayG, rootC.publicArrayG)) return false;
        if (!Arrays.equals(publicArrayH, rootC.publicArrayH)) return false;
        if (!Arrays.equals(publicArrayI, rootC.publicArrayI)) return false;
        if (publicArrayANull != rootC.publicArrayANull) return false;
        if (publicArrayBNull != rootC.publicArrayBNull) return false;
        if (publicArrayCNull != rootC.publicArrayCNull) return false;
        if (publicArrayDNull != rootC.publicArrayDNull) return false;
        if (publicArrayENull != rootC.publicArrayENull) return false;
        if (publicArrayFNull != rootC.publicArrayFNull) return false;
        if (publicArrayGNull != rootC.publicArrayGNull) return false;
        if (publicArrayHNull != rootC.publicArrayHNull) return false;
        return publicArrayINull == rootC.publicArrayINull;

    }
}
