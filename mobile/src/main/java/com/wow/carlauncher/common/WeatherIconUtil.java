package com.wow.carlauncher.common;

import android.util.Log;

import com.wow.carlauncher.R;

import java.util.Calendar;

import static com.wow.carlauncher.common.CommonData.TAG;

/**
 * Created by 10124 on 2017/11/3.
 */

public class WeatherIconUtil {
    public static int getWeatherResId(String paramString) {
        if (paramString.equals("晴")) {
            if (!isNight()) {
                return R.mipmap.n_weather_q;
            } else {
                return R.mipmap.n_weather_q;
            }
        } else if (paramString.equals("多云")) {
            if (!isNight()) {
                return R.mipmap.n_weather_yin;
            } else {
                return R.mipmap.n_weather_yin;
            }
        } else if (paramString.equals("阴")) {
            return R.mipmap.n_weather_yin;
        } else if (paramString.equals("阵雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("雷阵雨")) {
            return R.mipmap.n_weather_lei;
        } else if (paramString.equals("雷阵雨伴有冰雹")) {
            return R.mipmap.n_weather_lei;
        } else if (paramString.equals("雨夹雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("小雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("中雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("大雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("暴雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("大暴雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("特大暴雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("阵雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("小雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("中雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("大雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("暴雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("雾")) {
            return R.mipmap.n_weather_wu;
        } else if (paramString.equals("冻雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("小雨-中雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("中雨-大雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("大雨-暴雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("暴雨-大暴雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("大暴雨-特大暴雨")) {
            return R.mipmap.n_weather_yu;
        } else if (paramString.equals("小雪-中雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("中雪-大雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("大雪-暴雪")) {
            return R.mipmap.n_weather_xue;
        } else if (paramString.equals("沙尘暴")) {
            return R.mipmap.n_weather_sha;
        } else if (paramString.equals("浮沉")) {
            return R.mipmap.n_weather_mai;
        } else if (paramString.equals("扬沙")) {
            return R.mipmap.n_weather_sha;
        } else if (paramString.equals("强沙尘暴")) {
            return R.mipmap.n_weather_sha;
        } else if (paramString.equals("轻雾")) {
            return R.mipmap.n_weather_wu;
        } else if (paramString.equals("霾")) {
            return R.mipmap.n_weather_mai;
        }
        return R.mipmap.n_weather_weizhi;
    }

    static boolean isNight() {
        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.e(TAG, "isNight: " + i);
        return (i < 6) || (i >= 18);
    }
}