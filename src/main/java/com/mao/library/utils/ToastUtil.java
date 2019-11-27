package com.mao.library.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.mao.library.abs.AbsApplication;

public class ToastUtil {
    private static Toast t;
    // private static TextView textView;
    // private static ImageView image;

    private static Handler handler;

    private synchronized static void getToast(final CharSequence text, final int duration, final int type) {
        try {
            if (handler == null) {
                Thread thread = new Thread() {

                    @Override
                    public void run() {
                        boolean result = false;

                        synchronized (ToastUtil.class) {
                            if (handler == null) {
                                result = true;
                                Looper.prepare();
                                handler = new Handler();
                            }
                        }

                        handler.post(() -> makeToast(text, duration));

                        if (result) {
                            Looper.loop();
                        }
                    }
                };
                thread.start();
            } else {
                handler.post(() -> makeToast(text, duration));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void makeToast(final CharSequence text, final int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        if (t == null) {
            final Context context = AbsApplication.getInstance();
            if (context != null) {
                MainHandlerUtil.INSTANCE.post(() -> {
                    t = Toast.makeText(context, text, duration);
                    t.show();
                });
            }
        } else {
            if(Looper.myLooper()==Looper.getMainLooper()){
                t.setDuration(duration);
                t.setText(text);
                t.show();
            }else{
                MainHandlerUtil.INSTANCE.post(() -> {
                    if(t!=null){
                        t.setDuration(duration);
                        t.setText(text);
                        t.show();
                    }
                });
            }
        }
    }

    public static void showToast(CharSequence text, int duration, int type) {
        getToast(text, duration, type);
    }

    public static void showToast(CharSequence text, int type) {
        getToast(text, Toast.LENGTH_SHORT, type);
    }

    public static void showErrorToast(CharSequence text) {
        makeToast(text, Toast.LENGTH_LONG);
    }

    public static void showOkToast(CharSequence text) {
        makeToast(text, Toast.LENGTH_LONG);
    }

    public static void cancelToast(){
        if(t!=null){
            t.cancel();
            t=null;
        }
    }
}
