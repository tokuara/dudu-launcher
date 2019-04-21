package com.wow.carlauncher.view.activity.driving.coolBlack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wow.carlauncher.R;
import com.wow.carlauncher.ex.plugin.music.MusicPlugin;
import com.wow.carlauncher.ex.plugin.music.event.PMusicEventInfo;
import com.wow.carlauncher.ex.plugin.music.event.PMusicEventState;
import com.wow.carlauncher.view.base.BaseEXView;
import com.wow.carlauncher.common.util.CommonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.wow.carlauncher.common.CommonData.TAG;

/**
 * Created by 10124 on 2018/5/11.
 */

public class MusicView extends BaseEXView {
    public MusicView(@NonNull Context context) {
        super(context);
    }

    public MusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getContent() {
        return R.layout.content_driving_cool_black_music;
    }

    @ViewInject(R.id.iv_play)
    private ImageView music_iv_play;

    @ViewInject(R.id.tv_title)
    private TextView music_tv_title;

    @Event(value = {R.id.iv_play, R.id.ll_prew, R.id.ll_next})
    private void clickEvent(View view) {
        Log.d(TAG, "clickEvent: " + view);
        switch (view.getId()) {
            case R.id.ll_prew: {
                MusicPlugin.self().pre();
                break;
            }
            case R.id.iv_play: {
                MusicPlugin.self().playOrPause();
                break;
            }
            case R.id.ll_next: {
                MusicPlugin.self().next();
                break;
            }
        }
    }

    private String nowTitle = "";

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final PMusicEventInfo event) {
        if (music_tv_title != null) {
            if (CommonUtil.isNotNull(event.getTitle())) {
                String msg = event.getTitle();
                if (CommonUtil.isNotNull(event.getArtist())) {
                    msg = msg + "-" + event.getArtist();
                }
                if (!msg.equals(nowTitle)) {
                    nowTitle = msg;
                    music_tv_title.setText(msg);
                    music_tv_title.setSelected(true);
                }
            } else {
                music_tv_title.setText("音乐名称");
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final PMusicEventState event) {
        if (music_iv_play != null) {
            if (event.isRun()) {
                music_iv_play.setImageResource(R.mipmap.ic_pause2);
            } else {
                music_iv_play.setImageResource(R.mipmap.ic_play2);
            }
        }
    }
}