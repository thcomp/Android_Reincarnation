package jp.co.thcomp.test;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

public class RootD extends RootC {
    private SubA privateSubANull;
    private SubA publicSubANull;
    private SubC privateSubCNull;
    private SubC publicSubCNull;

    public boolean equalsOnlyPublic(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equalsOnlyPublic(o)) return false;

        RootD rootD = (RootD) o;

        if (privateSubANull != rootD.privateSubANull) return false;
        if (publicSubANull != rootD.publicSubANull) return false;
        if (privateSubCNull != rootD.privateSubCNull) return false;
        return publicSubCNull == rootD.publicSubCNull;
    }
}
