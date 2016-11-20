package jp.co.thcomp.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    }

    private void startTestA(){
        Bundle outState = new Bundle();
        RootA rootA = new RootA();
        rootA.initialize();
        ReincarnationHelper.save(rootA, outState);

        RootA rootANew = new RootA();
        ReincarnationHelper.restore(rootANew, outState);

        if(rootA.equals(rootANew)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestA_All(){
        Bundle outState = new Bundle();
        RootA rootA = new RootA();
        rootA.initialize();
        ReincarnationHelper.saveAll(rootA, outState);

        RootA rootANew = new RootA();
        ReincarnationHelper.restoreAll(rootANew, outState);

        if(rootA.equals(rootANew)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestB(){
        Bundle outState = new Bundle();
        RootB rootB = new RootB();
        rootB.initialize();
        ReincarnationHelper.save(rootB, outState);

        RootB rootBNew = new RootB();
        ReincarnationHelper.restore(rootBNew, outState);

        if(rootB.equals(rootBNew)){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NG", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTestB_All(){
        Bundle outState = new Bundle();
        RootB rootB = new RootB();
        rootB.initialize();
        ReincarnationHelper.saveAll(rootB, outState);

        RootB rootBNew = new RootB();
        ReincarnationHelper.restoreAll(rootBNew, outState);

        if(rootB.equals(rootBNew)){
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
                case R.id.btnStartTestA_All:
                    startTestA_All();
                    break;
                case R.id.btnStartTestB_All:
                    startTestB_All();
                    break;
            }
        }
    };
}
