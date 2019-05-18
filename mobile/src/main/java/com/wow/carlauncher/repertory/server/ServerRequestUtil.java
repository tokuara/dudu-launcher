package com.wow.carlauncher.repertory.server;

import android.support.annotation.NonNull;

import com.wow.carlauncher.common.LogEx;
import com.wow.carlauncher.common.util.CommonUtil;
import com.wow.carlauncher.common.util.GsonUtil;
import com.wow.carlauncher.ex.manage.okHttp.OkHttpManage;
import com.wow.carlauncher.ex.manage.toast.ToastManage;
import com.wow.carlauncher.repertory.server.response.BaseResult;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ServerRequestUtil {
    public static <D, T extends BaseResult<D>> Call get(String url, Class<T> clazz, final CommonCallback<D> commonCallback) {
        return OkHttpManage.self().get(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                LogEx.e(ServerRequestUtil.class, "onError: ");
                e.printStackTrace();
                if (commonCallback != null) {
                    commonCallback.callback(ServerConstant.NET_ERROR, "网络请求失败", null);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    String result = response.body().string();
                    LogEx.d(ServerRequestUtil.class, "onSuccess: " + result);
                    if (result.length() > 2) {
                        if (commonCallback != null) {
                            BaseResult<D> res = GsonUtil.getGson().fromJson(result, clazz);
                            if (res == null) {
                                commonCallback.callback(ServerConstant.RES_ERROR, "网络请求失败", null);
                                return;
                            }
                            if (CommonUtil.equals(res.getCode(), 0)) {
                                commonCallback.callback(res.getCode(), "", res.getData());
                            } else {
                                ToastManage.self().show(res.getMsg());
                                commonCallback.callback(res.getCode(), res.getMsg(), null);
                            }
                            return;
                        }
                    }
                }
                if (commonCallback != null) {
                    commonCallback.callback(ServerConstant.RES_ERROR, "网络请求失败", null);
                }
            }
        });
    }

    public static <D, T extends BaseResult<D>> Call post(String url, Map<String, Object> param, Class<T> clazz, final CommonCallback<D> commonCallback) {
        return OkHttpManage.self().post(url, param, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                LogEx.e(ServerRequestUtil.class, "onError: ");
                e.printStackTrace();
                if (commonCallback != null) {
                    commonCallback.callback(ServerConstant.NET_ERROR, "网络请求失败", null);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    String result = response.body().string();
                    LogEx.d(ServerRequestUtil.class, "onSuccess: " + result);
                    if (result.length() > 2) {
                        if (commonCallback != null) {
                            BaseResult<D> res = GsonUtil.getGson().fromJson(result, clazz);
                            if (res == null) {
                                commonCallback.callback(ServerConstant.RES_ERROR, "网络请求失败", null);
                                return;
                            }
                            if (CommonUtil.equals(res.getCode(), 0)) {
                                commonCallback.callback(res.getCode(), "", res.getData());
                            } else {
                                ToastManage.self().show(res.getMsg());
                                commonCallback.callback(res.getCode(), res.getMsg(), null);
                            }
                            return;
                        }
                    }
                }
                if (commonCallback != null) {
                    commonCallback.callback(ServerConstant.RES_ERROR, "网络请求失败", null);
                }
            }
        });
    }
}