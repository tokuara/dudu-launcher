package com.wow.carlauncher.view.activity.launcher.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wow.carlauncher.R;
import com.wow.carlauncher.common.LogEx;
import com.wow.carlauncher.common.TaskExecutor;
import com.wow.carlauncher.ex.manage.ThemeManage;
import com.wow.carlauncher.ex.manage.ble.BleManage;
import com.wow.carlauncher.ex.plugin.obd.ObdPlugin;
import com.wow.carlauncher.ex.plugin.obd.evnet.PObdEventCarTp;
import com.wow.carlauncher.ex.plugin.obd.evnet.PObdEventConnect;
import com.wow.carlauncher.view.base.BaseThemeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wow.carlauncher.ex.manage.ThemeManage.Theme.BLACK;
import static com.wow.carlauncher.ex.manage.ThemeManage.Theme.WHITE;

/**
 * Created by 10124 on 2018/4/20.
 */

public class LTaiyaView extends BaseThemeView {

    public LTaiyaView(@NonNull Context context) {
        super(context);
    }

    public LTaiyaView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getContent() {
        return R.layout.content_l_taiya;
    }

    @Override
    public void changedTheme(ThemeManage manage) {
        rl_base.setBackgroundResource(manage.getCurrentThemeRes(R.drawable.n_l_item1_bg));
        tv_title.setTextColor(manage.getCurrentThemeColor(R.color.l_title));
        tv_msg.setTextColor(manage.getCurrentThemeColor(R.color.l_msg));
        iv_error.setImageResource(manage.getCurrentThemeRes(R.mipmap.ic_obderror));

        manage.setViewsBackround(this, new int[]{
                R.id.tv_lt,
                R.id.tv_rt,
                R.id.tv_lb,
                R.id.tv_rb
        }, R.drawable.n_cell_bg);


        manage.setTextViewsColor(this, new int[]{
                R.id.tv_lt,
                R.id.tv_rt,
                R.id.tv_lb,
                R.id.tv_rb
        }, R.color.l_text2);

        if (currentTheme == WHITE || currentTheme == BLACK) {
            tv_title.setGravity(Gravity.CENTER);
        } else {
            tv_title.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }

        LogEx.d(this, "changedTheme: ");
    }

    @OnClick(value = {R.id.rl_base})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.rl_base: {
                if (!ObdPlugin.self().isConnect()) {
                    new AlertDialog.Builder(getContext()).setTitle("警告!")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", (dialog2, which2) -> {
                                BleManage.self().getClient().closeBluetooth();
                                TaskExecutor.self().run(() -> BleManage.self().getClient().openBluetooth(), 1000);
                            })
                            .setMessage("是否确认重启蓝牙").show();
                }
                break;
            }
        }
    }

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_lt)
    TextView tv_lt;

    @BindView(R.id.tv_rt)
    TextView tv_rt;

    @BindView(R.id.tv_lb)
    TextView tv_lb;

    @BindView(R.id.tv_rb)
    TextView tv_rb;

    @BindView(R.id.rl_base)
    View rl_base;

    @BindView(R.id.iv_img)
    ImageView iv_img;

    @BindView(R.id.iv_error)
    ImageView iv_error;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @BindView(R.id.ll_ty)
    View ll_ty;

    @BindView(R.id.ll_msg)
    View ll_msg;
    private boolean connect = false;

    @Override
    protected void initView() {
        LogEx.d(this, "initView: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final PObdEventConnect event) {
        boolean show = false;
        if (event.isConnected()) {
            if (ObdPlugin.self().supportTp()) {
                show = true;
            } else {
                tv_msg.setText(R.string.obd_not_tp);
            }
        } else {
            tv_msg.setText(R.string.obd_not_connect);
        }
        connect = show;
        ll_ty.setVisibility(show ? VISIBLE : GONE);
        ll_msg.setVisibility(show ? GONE : VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final PObdEventCarTp event) {
        if (!connect) {
            connect = true;
            onEvent(new PObdEventConnect().setConnected(ObdPlugin.self().isConnect()));
        }
        if (tv_lt != null && event.getlFTirePressure() != null) {
            tv_lt.setText(getContext().getString(R.string.launcher_tp, "左前", event.getlFTirePressure(), event.getlFTemp()));
        }

        if (tv_lb != null && event.getlBTirePressure() != null) {
            tv_lb.setText(getContext().getString(R.string.launcher_tp, "左后", event.getlBTirePressure(), event.getlBTemp()));
        }

        if (tv_rt != null && event.getrFTirePressure() != null) {
            tv_rt.setText(getContext().getString(R.string.launcher_tp, "右前", event.getrFTirePressure(), event.getrFTemp()));
        }

        if (tv_rb != null && event.getrBTirePressure() != null) {
            tv_rb.setText(getContext().getString(R.string.launcher_tp, "右后", event.getrBTirePressure(), event.getrBTemp()));
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onEvent(new PObdEventConnect().setConnected(ObdPlugin.self().isConnect()));
        onEvent(ObdPlugin.self().getCurrentPObdEventCarTp());
    }
}
