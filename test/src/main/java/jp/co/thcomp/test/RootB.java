package jp.co.thcomp.test;

import java.util.ArrayList;
import java.util.HashMap;

import jp.co.thcomp.reincarnation.ReincarnationHelper;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

public class RootB extends RootA {
    public ArrayList<Byte> publicListA = null;
    private ArrayList<Byte> privateListA = null;
    public ArrayList<Character> publicListB = null;
    private ArrayList<Character> privateListB = null;
    public ArrayList<Short> publicListC = null;
    private ArrayList<Short> privateListC = null;
    public ArrayList<Integer> publicListD = null;
    private ArrayList<Integer> privateListD = null;
    public ArrayList<Long> publicListE = null;
    private ArrayList<Long> privateListE = null;
    public ArrayList<Float> publicListF = null;
    private ArrayList<Float> privateListF = null;
    public ArrayList<Double> publicListG = null;
    private ArrayList<Double> privateListG = null;
    public ArrayList<String> publicListH = null;
    private ArrayList<String> privateListH = null;
    public ArrayList<SubA> publicListI = null;
    private ArrayList<SubA> privateListI = null;

    public HashMap<Byte, Byte> publicMapA = null;
    private HashMap<Byte, Byte> privateMapA = null;
    public HashMap<Character, Character> publicMapB = null;
    private HashMap<Character, Character> privateMapB = null;
    public HashMap<Short, Short> publicMapC = null;
    private HashMap<Short, Short> privateMapC = null;
    public HashMap<Integer, Integer> publicMapD = null;
    private HashMap<Integer, Integer> privateMapD = null;
    public HashMap<Long, Long> publicMapE = null;
    private HashMap<Long, Long> privateMapE = null;
    public HashMap<Float, Float> publicMapF = null;
    private HashMap<Float, Float> privateMapF = null;
    public HashMap<Double, Double> publicMapG = null;
    private HashMap<Double, Double> privateMapG = null;
    public HashMap<String, String> publicMapH = null;
    private HashMap<String, String> privateMapH = null;
    public HashMap<SubA, SubC> publicMapI = null;
    private HashMap<SubA, SubC> privateMapI = null;

    public void initialize() {
        super.initialize();

        publicListA = new ArrayList<Byte>();
        privateListA = new ArrayList<Byte>();
        publicListB = new ArrayList<Character>();
        privateListB = new ArrayList<Character>();
        publicListC = new ArrayList<Short>();
        privateListC = new ArrayList<Short>();
        publicListD = new ArrayList<Integer>();
        privateListD = new ArrayList<Integer>();
        publicListE = new ArrayList<Long>();
        privateListE = new ArrayList<Long>();
        publicListF = new ArrayList<Float>();
        privateListF = new ArrayList<Float>();
        publicListG = new ArrayList<Double>();
        privateListG = new ArrayList<Double>();
        publicListH = new ArrayList<String>();
        privateListH = new ArrayList<String>();
        publicListI = new ArrayList<SubA>();
        privateListI = new ArrayList<SubA>();

        publicMapA = new HashMap<Byte, Byte>();
        privateMapA = new HashMap<Byte, Byte>();
        publicMapB = new HashMap<Character, Character>();
        privateMapB = new HashMap<Character, Character>();
        publicMapC = new HashMap<Short, Short>();
        privateMapC = new HashMap<Short, Short>();
        publicMapD = new HashMap<Integer, Integer>();
        privateMapD = new HashMap<Integer, Integer>();
        publicMapE = new HashMap<Long, Long>();
        privateMapE = new HashMap<Long, Long>();
        publicMapF = new HashMap<Float, Float>();
        privateMapF = new HashMap<Float, Float>();
        publicMapG = new HashMap<Double, Double>();
        privateMapG = new HashMap<Double, Double>();
        publicMapH = new HashMap<String, String>();
        privateMapH = new HashMap<String, String>();
        publicMapI = new HashMap<SubA, SubC>();
        privateMapI = new HashMap<SubA, SubC>();

        for (byte i = 0; i < 10; i++) {
            publicListA.add(i);
            privateListA.add(i);
            publicListB.add((char) i);
            privateListB.add((char) i);
            publicListC.add((short) i);
            privateListC.add((short) i);
            publicListD.add((int) i);
            privateListD.add((int) i);
            publicListE.add((long) i);
            privateListE.add((long) i);
            publicListF.add((float) i);
            privateListF.add((float) i);
            publicListG.add((double) i);
            privateListG.add((double) i);
            publicListH.add(String.valueOf(i));
            privateListH.add(String.valueOf(i));
        }
        for (int i = 0; i < 10; i++) {
            publicMapA.put((byte) i, (byte) (i + 100));
            privateMapA.put((byte) i, (byte) (i + 100));
            publicMapB.put((char) i, (char) (i + 100));
            privateMapB.put((char) i, (char) (i + 100));
            publicMapC.put((short) i, (short) (i + 100));
            privateMapC.put((short) i, (short) (i + 100));
            publicMapD.put((int) i, (int) (i + 100));
            privateMapD.put((int) i, (int) (i + 100));
            publicMapE.put((long) i, (long) (i + 100));
            privateMapE.put((long) i, (long) (i + 100));
            publicMapF.put((float) i, (float) (i + 100));
            privateMapF.put((float) i, (float) (i + 100));
            publicMapG.put((double) i, (double) (i + 100));
            privateMapG.put((double) i, (double) (i + 100));
            publicMapH.put(String.valueOf(i), String.valueOf(i + 100));
            privateMapH.put(String.valueOf(i), String.valueOf(i + 100));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RootB rootB = (RootB) o;

        if (publicListA != null ? !publicListA.equals(rootB.publicListA) : rootB.publicListA != null)
            return false;
        if (privateListA != null ? !privateListA.equals(rootB.privateListA) : rootB.privateListA != null)
            return false;
        if (publicListB != null ? !publicListB.equals(rootB.publicListB) : rootB.publicListB != null)
            return false;
        if (privateListB != null ? !privateListB.equals(rootB.privateListB) : rootB.privateListB != null)
            return false;
        if (publicListC != null ? !publicListC.equals(rootB.publicListC) : rootB.publicListC != null)
            return false;
        if (privateListC != null ? !privateListC.equals(rootB.privateListC) : rootB.privateListC != null)
            return false;
        if (publicListD != null ? !publicListD.equals(rootB.publicListD) : rootB.publicListD != null)
            return false;
        if (privateListD != null ? !privateListD.equals(rootB.privateListD) : rootB.privateListD != null)
            return false;
        if (publicListE != null ? !publicListE.equals(rootB.publicListE) : rootB.publicListE != null)
            return false;
        if (privateListE != null ? !privateListE.equals(rootB.privateListE) : rootB.privateListE != null)
            return false;
        if (publicListF != null ? !publicListF.equals(rootB.publicListF) : rootB.publicListF != null)
            return false;
        if (privateListF != null ? !privateListF.equals(rootB.privateListF) : rootB.privateListF != null)
            return false;
        if (publicListG != null ? !publicListG.equals(rootB.publicListG) : rootB.publicListG != null)
            return false;
        if (privateListG != null ? !privateListG.equals(rootB.privateListG) : rootB.privateListG != null)
            return false;
        if (publicListH != null ? !publicListH.equals(rootB.publicListH) : rootB.publicListH != null)
            return false;
        if (privateListH != null ? !privateListH.equals(rootB.privateListH) : rootB.privateListH != null)
            return false;
        if (publicListI != null ? !publicListI.equals(rootB.publicListI) : rootB.publicListI != null)
            return false;
        if (privateListI != null ? !privateListI.equals(rootB.privateListI) : rootB.privateListI != null)
            return false;
        if (publicMapA != null ? !publicMapA.equals(rootB.publicMapA) : rootB.publicMapA != null)
            return false;
        if (privateMapA != null ? !privateMapA.equals(rootB.privateMapA) : rootB.privateMapA != null)
            return false;
        if (publicMapB != null ? !publicMapB.equals(rootB.publicMapB) : rootB.publicMapB != null)
            return false;
        if (privateMapB != null ? !privateMapB.equals(rootB.privateMapB) : rootB.privateMapB != null)
            return false;
        if (publicMapC != null ? !publicMapC.equals(rootB.publicMapC) : rootB.publicMapC != null)
            return false;
        if (privateMapC != null ? !privateMapC.equals(rootB.privateMapC) : rootB.privateMapC != null)
            return false;
        if (publicMapD != null ? !publicMapD.equals(rootB.publicMapD) : rootB.publicMapD != null)
            return false;
        if (privateMapD != null ? !privateMapD.equals(rootB.privateMapD) : rootB.privateMapD != null)
            return false;
        if (publicMapE != null ? !publicMapE.equals(rootB.publicMapE) : rootB.publicMapE != null)
            return false;
        if (privateMapE != null ? !privateMapE.equals(rootB.privateMapE) : rootB.privateMapE != null)
            return false;
        if (publicMapF != null ? !publicMapF.equals(rootB.publicMapF) : rootB.publicMapF != null)
            return false;
        if (privateMapF != null ? !privateMapF.equals(rootB.privateMapF) : rootB.privateMapF != null)
            return false;
        if (publicMapG != null ? !publicMapG.equals(rootB.publicMapG) : rootB.publicMapG != null)
            return false;
        if (privateMapG != null ? !privateMapG.equals(rootB.privateMapG) : rootB.privateMapG != null)
            return false;
        if (publicMapH != null ? !publicMapH.equals(rootB.publicMapH) : rootB.publicMapH != null)
            return false;
        if (privateMapH != null ? !privateMapH.equals(rootB.privateMapH) : rootB.privateMapH != null)
            return false;
        if (publicMapI != null ? !publicMapI.equals(rootB.publicMapI) : rootB.publicMapI != null)
            return false;
        return privateMapI != null ? privateMapI.equals(rootB.privateMapI) : rootB.privateMapI == null;

    }

    public boolean equalsOnlyPublic(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equalsOnlyPublic(o)) return false;

        RootB rootB = (RootB) o;

        if (publicListA != null ? !publicListA.equals(rootB.publicListA) : rootB.publicListA != null)
            return false;
        if (publicListB != null ? !publicListB.equals(rootB.publicListB) : rootB.publicListB != null)
            return false;
        if (publicListC != null ? !publicListC.equals(rootB.publicListC) : rootB.publicListC != null)
            return false;
        if (publicListD != null ? !publicListD.equals(rootB.publicListD) : rootB.publicListD != null)
            return false;
        if (publicListE != null ? !publicListE.equals(rootB.publicListE) : rootB.publicListE != null)
            return false;
        if (publicListF != null ? !publicListF.equals(rootB.publicListF) : rootB.publicListF != null)
            return false;
        if (publicListG != null ? !publicListG.equals(rootB.publicListG) : rootB.publicListG != null)
            return false;
        if (publicListH != null ? !publicListH.equals(rootB.publicListH) : rootB.publicListH != null)
            return false;
        if (publicListI != null ? !publicListI.equals(rootB.publicListI) : rootB.publicListI != null)
            return false;
        if (publicMapA != null ? !publicMapA.equals(rootB.publicMapA) : rootB.publicMapA != null)
            return false;
        if (publicMapB != null ? !publicMapB.equals(rootB.publicMapB) : rootB.publicMapB != null)
            return false;
        if (publicMapC != null ? !publicMapC.equals(rootB.publicMapC) : rootB.publicMapC != null)
            return false;
        if (publicMapD != null ? !publicMapD.equals(rootB.publicMapD) : rootB.publicMapD != null)
            return false;
        if (publicMapE != null ? !publicMapE.equals(rootB.publicMapE) : rootB.publicMapE != null)
            return false;
        if (publicMapF != null ? !publicMapF.equals(rootB.publicMapF) : rootB.publicMapF != null)
            return false;
        if (publicMapG != null ? !publicMapG.equals(rootB.publicMapG) : rootB.publicMapG != null)
            return false;
        if (publicMapH != null ? !publicMapH.equals(rootB.publicMapH) : rootB.publicMapH != null)
            return false;
        if (publicMapI != null ? !publicMapI.equals(rootB.publicMapI) : rootB.publicMapI != null)
            return false;
        return true;
    }
}
