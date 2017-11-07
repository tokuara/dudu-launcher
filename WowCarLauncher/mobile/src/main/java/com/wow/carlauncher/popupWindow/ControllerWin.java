package com.wow.carlauncher.popupWindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.wow.carlauncher.CarLauncherApplication;
import com.wow.carlauncher.R;
import com.wow.carlauncher.activity.LockActivity;
import com.wow.carlauncher.common.CommonData;
import com.wow.carlauncher.common.util.SharedPreUtil;

/**
 * Created by 10124 on 2017/11/7.
 */

public class ControllerWin implements View.OnClickListener {
    private static ControllerWin self;

    public static ControllerWin self() {
        if (self == null) {
            self = new ControllerWin();
        }
        return self;
    }

    private ControllerWin() {

    }

    //窗口管理器
    private WindowManager wm = null;
    private WindowManager.LayoutParams winparams;
    //窗口的布局参数
    private CarLauncherApplication context;
    //窗口视图
    private View popupWindow;
    private boolean isShow = false;
    private AudioManager audioManager;
    private int oldVolume = -1;

    public void init(CarLauncherApplication context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        winparams = new WindowManager.LayoutParams();
        // 类型
        winparams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        winparams.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        winparams.format = PixelFormat.TRANSLUCENT;
        winparams.width = outMetrics.widthPixels;
        winparams.height = outMetrics.heightPixels;
        winparams.gravity = Gravity.TOP | Gravity.START;
        winparams.x = SharedPreUtil.getSharedPreInteger(CommonData.SDATA_POPUP_WIN_X, 0);
        winparams.y = SharedPreUtil.getSharedPreInteger(CommonData.SDATA_POPUP_WIN_Y, 0);

        popupWindow = View.inflate(context, R.layout.popup_controller, null);
        popupWindow.findViewById(R.id.popup_bg).setOnClickListener(this);
        popupWindow.findViewById(R.id.btn_close_screen).setOnClickListener(this);
        popupWindow.findViewById(R.id.btn_jy).setOnClickListener(this);
        popupWindow.findViewById(R.id.btn_vu).setOnClickListener(this);
        popupWindow.findViewById(R.id.btn_vd).setOnClickListener(this);
    }

    //隐藏方法
    private void hide() {
        if (isShow) {
            wm.removeView(popupWindow);
            isShow = false;
        }
    }

    //显示方法
    public void show() {
        if (!isShow) {
            wm.addView(popupWindow, winparams);
            isShow = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_bg: {
                hide();
                break;
            }
            case R.id.btn_close_screen: {
                hide();
                Intent intent = new Intent(context, LockActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            }
            case R.id.btn_jy: {
                if (oldVolume == 0) {
                    oldVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.STREAM_MUSIC);
                } else {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, oldVolume, AudioManager.STREAM_MUSIC);
                    oldVolume = 0;
                }
                break;
            }
            case R.id.btn_vu: {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
            }
            case R.id.btn_vd: {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                break;
            }
        }
    }
}