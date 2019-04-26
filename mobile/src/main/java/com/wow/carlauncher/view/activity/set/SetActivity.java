package com.wow.carlauncher.view.activity.set;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wow.carlauncher.R;
import com.wow.carlauncher.common.util.SharedPreUtil;
import com.wow.carlauncher.common.view.SetView;
import com.wow.carlauncher.ex.manage.toast.ToastManage;
import com.wow.carlauncher.view.activity.set.event.SEventRefreshAmapPlugin;
import com.wow.carlauncher.view.activity.set.view.SFkView;
import com.wow.carlauncher.view.activity.set.view.SHomeView;
import com.wow.carlauncher.view.activity.set.view.SItemView;
import com.wow.carlauncher.view.activity.set.view.SLoadAppView;
import com.wow.carlauncher.view.activity.set.view.SObdView;
import com.wow.carlauncher.view.activity.set.view.SPopupView;
import com.wow.carlauncher.view.activity.set.view.SSystemView;
import com.wow.carlauncher.view.activity.set.view.SThemeView;
import com.wow.carlauncher.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.wow.carlauncher.common.CommonData.APP_WIDGET_AMAP_PLUGIN;
import static com.wow.carlauncher.common.CommonData.REQUEST_SELECT_AMAP_PLUGIN;

public class SetActivity extends BaseActivity {
    @Override
    public void init() {
        setContent(R.layout.activity_set);
    }

    @ViewInject(R.id.sg_theme)
    private SetView sg_theme;

    @ViewInject(R.id.ll_set)
    private FrameLayout ll_set;

    @ViewInject(R.id.ll_user)
    private LinearLayout ll_user;


    @Override
    public void initView() {
        setTitle("设置");
        clickEvent(sg_theme);
        ll_user.setOnClickListener(v -> ToastManage.self().show("还没开发呢"));
    }

    @Event(value = {R.id.sg_item, R.id.sg_theme, R.id.sg_home, R.id.sg_obd, R.id.sg_fk, R.id.sg_load_app, R.id.sg_popup, R.id.sg_system_set})
    private void clickEvent(View view) {
        ViewGroup setView = null;
        switch (view.getId()) {
            case R.id.sg_theme: {
                setView = new SThemeView(this);
                break;
            }
            case R.id.sg_home: {
                setView = new SHomeView(this);
                break;
            }
            case R.id.sg_item: {
                setView = new SItemView(this);
                break;
            }
            case R.id.sg_obd: {
                setView = new SObdView(this);
                break;
            }
            case R.id.sg_fk: {
                setView = new SFkView(this);
                break;
            }
            case R.id.sg_load_app: {
                setView = new SLoadAppView(this);
                break;
            }
            case R.id.sg_popup: {
                setView = new SPopupView(this);
                break;
            }
            case R.id.sg_system_set: {
                setView = new SSystemView(this);
                break;
            }
        }
        if (setView != null) {
            ll_set.removeAllViews();
            ll_set.addView(setView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_AMAP_PLUGIN: {
                    int id = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
                    if (id != -1) {
                        SharedPreUtil.saveInteger(APP_WIDGET_AMAP_PLUGIN, id);
                        EventBus.getDefault().post(new SEventRefreshAmapPlugin());
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }
}
