package jp.co.thcomp.test;

import jp.co.thcomp.reincarnation.ReincarnationHelper;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

@ReincarnationHelper.TargetClass
public class SubA {
    public SubC publicSub1;
    private SubC privateSub1;

    public void initialize(){
        publicSub1.initialize();
        privateSub1.initialize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubA sub = (SubA) o;

        if (publicSub1 != null ? !publicSub1.equals(sub.publicSub1) : sub.publicSub1 != null)
            return false;
        return privateSub1 != null ? privateSub1.equals(sub.privateSub1) : sub.privateSub1 == null;
    }

    public boolean equalsOnlyPublic(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubA sub = (SubA) o;

        if (publicSub1 != null ? !publicSub1.equals(sub.publicSub1) : sub.publicSub1 != null)
            return false;
        return true;
    }
}
