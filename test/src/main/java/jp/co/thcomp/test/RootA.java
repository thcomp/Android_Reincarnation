package jp.co.thcomp.test;

import jp.co.thcomp.reincarnation.ReincarnationHelper;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

@ReincarnationHelper.TargetClass
public class RootA {
    public byte publicA = 0;
    private byte privateA = 0;
    public char publicB = 0;
    private char privateB = 0;
    public short publicC = 0;
    private short privateC = 0;
    public int publicD = 0;
    private int privateD = 0;
    public long publicE = 0;
    private long privateE = 0;
    public float publicF = 0f;
    private float privateF = 0f;
    public double publicG = 0;
    private double privateG = 0;

    public Byte publicA2 = 0;
    private Byte privateA2 = 0;
    public Character publicB2 = 0;
    private Character privateB2 = 0;
    public Short publicC2 = 0;
    private Short privateC2 = 0;
    public Integer publicD2 = 0;
    private Integer privateD2 = 0;
    public Long publicE2 = Long.valueOf(0);
    private Long privateE2 = Long.valueOf(0);
    public Float publicF2 = 0f;
    private Float privateF2 = 0f;
    public Double publicG2 = Double.valueOf(0);
    private Double privateG2 = Double.valueOf(0);
    public String publicH = "0";
    private String privateH = "0";

    public void initialize() {
        publicA = 10;
        privateA = 10;
        publicB = 10;
        privateB = 10;
        publicC = 10;
        privateC = 10;
        publicD = 10;
        privateD = 10;
        publicE = 10;
        privateE = 10;
        publicF = 10f;
        privateF = 10f;
        publicG = 10;
        privateG = 10;

        publicA2 = 10;
        privateA2 = 10;
        publicB2 = 10;
        privateB2 = 10;
        publicC2 = 10;
        privateC2 = 10;
        publicD2 = 10;
        privateD2 = 10;
        publicE2 = Long.valueOf(10);
        privateE2 = Long.valueOf(10);
        publicF2 = 10f;
        privateF2 = 10f;
        publicG2 = Double.valueOf(10);
        privateG2 = Double.valueOf(10);
        publicH = "10";
        privateH = "10";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RootA rootA = (RootA) o;

        if (publicA != rootA.publicA) return false;
        if (privateA != rootA.privateA) return false;
        if (publicB != rootA.publicB) return false;
        if (privateB != rootA.privateB) return false;
        if (publicC != rootA.publicC) return false;
        if (privateC != rootA.privateC) return false;
        if (publicD != rootA.publicD) return false;
        if (privateD != rootA.privateD) return false;
        if (publicE != rootA.publicE) return false;
        if (privateE != rootA.privateE) return false;
        if (Float.compare(rootA.publicF, publicF) != 0) return false;
        if (Float.compare(rootA.privateF, privateF) != 0) return false;
        if (Double.compare(rootA.publicG, publicG) != 0) return false;
        if (Double.compare(rootA.privateG, privateG) != 0) return false;
        if (publicA2 != null ? !publicA2.equals(rootA.publicA2) : rootA.publicA2 != null)
            return false;
        if (privateA2 != null ? !privateA2.equals(rootA.privateA2) : rootA.privateA2 != null)
            return false;
        if (publicB2 != null ? !publicB2.equals(rootA.publicB2) : rootA.publicB2 != null)
            return false;
        if (privateB2 != null ? !privateB2.equals(rootA.privateB2) : rootA.privateB2 != null)
            return false;
        if (publicC2 != null ? !publicC2.equals(rootA.publicC2) : rootA.publicC2 != null)
            return false;
        if (privateC2 != null ? !privateC2.equals(rootA.privateC2) : rootA.privateC2 != null)
            return false;
        if (publicD2 != null ? !publicD2.equals(rootA.publicD2) : rootA.publicD2 != null)
            return false;
        if (privateD2 != null ? !privateD2.equals(rootA.privateD2) : rootA.privateD2 != null)
            return false;
        if (publicE2 != null ? !publicE2.equals(rootA.publicE2) : rootA.publicE2 != null)
            return false;
        if (privateE2 != null ? !privateE2.equals(rootA.privateE2) : rootA.privateE2 != null)
            return false;
        if (publicF2 != null ? !publicF2.equals(rootA.publicF2) : rootA.publicF2 != null)
            return false;
        if (privateF2 != null ? !privateF2.equals(rootA.privateF2) : rootA.privateF2 != null)
            return false;
        if (publicG2 != null ? !publicG2.equals(rootA.publicG2) : rootA.publicG2 != null)
            return false;
        if (privateG2 != null ? !privateG2.equals(rootA.privateG2) : rootA.privateG2 != null)
            return false;
        if (publicH != null ? !publicH.equals(rootA.publicH) : rootA.publicH != null)
            return false;
        return privateH != null ? privateH.equals(rootA.privateH) : rootA.privateH == null;
    }

    public boolean equalsOnlyPublic(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RootA rootA = (RootA) o;

        if (publicA != rootA.publicA) return false;
        if (publicB != rootA.publicB) return false;
        if (publicC != rootA.publicC) return false;
        if (publicD != rootA.publicD) return false;
        if (publicE != rootA.publicE) return false;
        if (Float.compare(rootA.publicF, publicF) != 0) return false;
        if (Double.compare(rootA.publicG, publicG) != 0) return false;
        if (publicA2 != null ? !publicA2.equals(rootA.publicA2) : rootA.publicA2 != null)
            return false;
        if (publicB2 != null ? !publicB2.equals(rootA.publicB2) : rootA.publicB2 != null)
            return false;
        if (publicC2 != null ? !publicC2.equals(rootA.publicC2) : rootA.publicC2 != null)
            return false;
        if (publicD2 != null ? !publicD2.equals(rootA.publicD2) : rootA.publicD2 != null)
            return false;
        if (publicE2 != null ? !publicE2.equals(rootA.publicE2) : rootA.publicE2 != null)
            return false;
        if (publicF2 != null ? !publicF2.equals(rootA.publicF2) : rootA.publicF2 != null)
            return false;
        if (publicG2 != null ? !publicG2.equals(rootA.publicG2) : rootA.publicG2 != null)
            return false;
        if (publicH != null ? !publicH.equals(rootA.publicH) : rootA.publicH != null)
            return false;
        return true;
    }
}
