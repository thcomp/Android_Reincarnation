package jp.co.thcomp.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.thcomp.reincarnation.ReincarnationHelper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartTestA).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btnStartTestA_All).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btnStartTestB).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btnStartTestB_All).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btnStartTestC).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btnStartTestUri).setOnClickListener(mBtnClickListener);
    }

    private void startTestA(){
        Bundle outState = new Bundle();
        RootAWrapper rootA = new RootAWrapper();
        rootA.rootA = new RootA();
        rootA.rootA.initialize();
        rootA.rootA.context = this;
        ReincarnationHelper.save(rootA, outState);

        RootAWrapper rootANew = new RootAWrapper();
        ReincarnationHelper.restore(rootANew, outState);

        if(rootA.rootA.equalsOnlyPublic(rootANew.rootA)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestA_All(){
        Bundle outState = new Bundle();
        RootAWrapper rootA = new RootAWrapper();
        rootA.rootA = new RootA();
        rootA.rootA.initialize();
        ReincarnationHelper.saveAll(rootA, outState);

        RootAWrapper rootANew = new RootAWrapper();
        ReincarnationHelper.restoreAll(rootANew, outState);

        if(rootA.rootA.equals(rootANew.rootA)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestB(){
        Bundle outState = new Bundle();
        RootBWrapper rootB = new RootBWrapper();
        rootB.rootB = new RootB();
        rootB.rootB.initialize();
        ReincarnationHelper.save(rootB, outState);

        RootBWrapper rootBNew = new RootBWrapper();
        ReincarnationHelper.restore(rootBNew, outState);

        if(rootB.rootB.equalsOnlyPublic(rootBNew.rootB)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestB_All(){
        Bundle outState = new Bundle();
        RootBWrapper rootB = new RootBWrapper();
        rootB.rootB = new RootB();
        rootB.rootB.initialize();
        ReincarnationHelper.saveAll(rootB, outState);

        RootBWrapper rootBNew = new RootBWrapper();
        ReincarnationHelper.restoreAll(rootBNew, outState);

        if(rootB.rootB.equals(rootBNew.rootB)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestC(){
        Bundle outState = new Bundle();
        RootCWrapper rootC = new RootCWrapper();
        rootC.rootC = new RootC();
        rootC.rootC.initialize();
        ReincarnationHelper.save(rootC, outState);

        RootCWrapper rootCNew = new RootCWrapper();
        ReincarnationHelper.restore(rootCNew, outState);

        if(rootC.rootC.equalsOnlyPublic(rootCNew.rootC)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestUri(){
        Bundle outState = new Bundle();
        UriWrapper uriWrapper = new UriWrapper();
        uriWrapper.uri = Uri.parse("http://www.google.co.jp/test/test2/a.cgi?param1=SSSS&param2=33333");
        uriWrapper.uriArray = new Uri[5];
        uriWrapper.uriList = new ArrayList<Uri>();
        uriWrapper.uriMap = new HashMap<Uri, Uri>();
        for(int i=0, size=uriWrapper.uriArray.length; i<size; i++){
            uriWrapper.uriArray[i] = Uri.parse("http://www.google.co.jp/test/array_" + i + "/a.cgi?param1=SSSS&param2=33333");
            uriWrapper.uriList.add(Uri.parse("http://www.google.co.jp/test/list_" + i + "/a.cgi?param1=SSSS&param2=33333"));
            uriWrapper.uriMap.put(
                    Uri.parse("http://www.google.co.jp/test/MapKey_" + i + "/a.cgi?param1=SSSS&param2=33333"),
                    Uri.parse("http://www.google.co.jp/test/MapValue_" + i + "/a.cgi?param1=SSSS&param2=33333")
            );
        }
        ReincarnationHelper.save(uriWrapper, outState);

        UriWrapper uriWrapperNew = new UriWrapper();
        ReincarnationHelper.restore(uriWrapperNew, outState);

        if(uriWrapper.equals(uriWrapperNew)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id){
                case R.id.btnStartTestA:
                    startTestA();
                    break;
                case R.id.btnStartTestB:
                    startTestB();
                    break;
                case R.id.btnStartTestC:
                    startTestC();
                    break;
                case R.id.btnStartTestA_All:
                    startTestA_All();
                    break;
                case R.id.btnStartTestB_All:
                    startTestB_All();
                    break;
                case R.id.btnStartTestUri:
                    startTestUri();
                    break;
            }
        }
    };

    private static class RootAWrapper {
        @ReincarnationHelper.TargetField
        public RootA rootA;
    }

    private static class RootBWrapper {
        @ReincarnationHelper.TargetField
        public RootB rootB;
    }

    private static class RootCWrapper {
        @ReincarnationHelper.TargetField
        public RootC rootC;
    }

    private static class UriWrapper {
        @ReincarnationHelper.TargetField
        public Uri uri;
        @ReincarnationHelper.TargetField
        public Uri[] uriArray;
        @ReincarnationHelper.TargetField
        public List<Uri> uriList;
        @ReincarnationHelper.TargetField
        public Map<Uri, Uri> uriMap;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UriWrapper that = (UriWrapper) o;

            if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(uriArray, that.uriArray)) return false;
            if (uriList != null ? !uriList.equals(that.uriList) : that.uriList != null)
                return false;
            return uriMap != null ? uriMap.equals(that.uriMap) : that.uriMap == null;

        }
    }
}
