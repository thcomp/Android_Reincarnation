package jp.co.thcomp.test;

import jp.co.thcomp.reincarnation.ReincarnationHelper;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

@ReincarnationHelper.TargetClass
public class SubC {
    public String publicA;
    private String privateA;

    public void initialize(){
        publicA = "10";
        privateA = "10";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubC subC = (SubC) o;

        if (publicA != null ? !publicA.equals(subC.publicA) : subC.publicA != null)
            return false;
        return privateA != null ? privateA.equals(subC.privateA) : subC.privateA == null;

    }

    public boolean equalsOnlyPublic(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubC subC = (SubC) o;

        if (publicA != null ? !publicA.equals(subC.publicA) : subC.publicA != null)
            return false;
        return true;
    }
}
