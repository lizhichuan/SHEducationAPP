package com.shaohua.sheducation.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.vo.BaseVO;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.app.SHApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

public class MainActivity extends TabActivity {
    public TabHost tabHost;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SHApplication.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        tabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, PlanActivity.class);
        spec = tabHost.newTabSpec(getResources().getString(R.string.plan)).
                setIndicator(getResources().getString(R.string.plan)).setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, ClassmateActivity.class);
        spec = tabHost.newTabSpec(getResources().getString(R.string.classmate)).
                setIndicator(getResources().getString(R.string.classmate)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, CourseActivity.class);
        spec = tabHost.newTabSpec(getResources().getString(R.string.course)).
                setIndicator(getResources().getString(R.string.course)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MeActivity.class);
        spec = tabHost.newTabSpec(getResources().getString(R.string.me)).
                setIndicator(getResources().getString(R.string.me)).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        radioGroup = (RadioGroup) this.findViewById(R.id.rdg_home);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rdbt_plan:
                        tabHost.setCurrentTabByTag(getResources().getString(R.string.plan));
                        break;
                    case R.id.rdbt_classmate:
                        tabHost.setCurrentTabByTag(getResources().getString(R.string.classmate));
                        break;
                    case R.id.rdbt_course:
                        tabHost.setCurrentTabByTag(getResources().getString(R.string.course));
                        break;
                    case R.id.rdbt_me:

                        tabHost.setCurrentTabByTag(getResources().getString(R.string.me));


                        break;
                    default:
                        tabHost.setCurrentTabByTag(getResources().getString(R.string.plan));
                        break;
                }
            }
        });
        radioGroup.check(R.id.rdbt_plan);
//状态栏透明和间距处理
//        CoolStatusBar ultimateBar = new CoolStatusBar(this);
//        ultimateBar.setColorBar(getResources().getColor(R.color.common_backgroud), 0);
    }

    @Subscribe
    public void onEventMainThread(BaseVO temp) {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    private long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                CoolPublicMethod.Toast(MainActivity.this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                SHApplication.getInstance().AppExit();

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
