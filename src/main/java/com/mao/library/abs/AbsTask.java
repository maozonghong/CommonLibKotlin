package com.mao.library.abs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.mao.library.R;
import com.mao.library.interfaces.OnTaskCompleteListener;
import com.mao.library.manager.OkHttpManager;
import com.mao.library.manager.ThreadPoolManager;
import com.mao.library.utils.DipUtils;
import com.mao.library.utils.MainHandlerUtil;
import com.mao.library.utils.ToastUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Response;
/**
 * Created by maozonghong
 * on 2019/11/21
 */

/**
 * 网络请求的抽象类，实现了大部分网络请求中可能会遇到的需求。 所有非第三方http网络请求需继承此类
 *
 * @param <T> 网络请求完成后返回的对象
 */
public abstract class AbsTask<T extends Serializable> implements Runnable, DialogInterface.OnCancelListener {
    public AbsRequest request;
    protected MyThread<T> thread;
    private volatile Set<OnTaskCompleteListener<T>> onTaskPostCompleteListeners, onTaskCompleteListeners;
    private WeakReference<Context> weakReference;

    /**
     * 进度条的位置
     */
    public int progressY;

    private Dialog dialog;
    private ProgressBar progressBar;

    /**
     * 进度条类型,0为横向,1为正中央圆形
     */
    public int progressDialogType = 0;

    /**
     * 是否需要上传文件，默认为false。
     */
    public boolean needUploadFile;

    /**
     * 是否可取消，默认为true。如果为true，进度条出现时可以按返回键取消，并调用{@link #cancel()}
     * ；如果为false，则进度条出现时无法按返回键取消。
     */
    public boolean cancelable = true;

    /**
     * 是否需要上一次的数据，默认为false。如果为true，非加载更多时，会缓存返回的数据，下一次非加载更多时，会先返回一次上次缓存的数据，
     * 不影响本次网络请求。
     */
    public boolean needLast;

    /**
     * 是否仅需要上一次的数据，默认为false，当{@link #needLast}
     * 为false是，本属性无效。如果为true，当有上一次缓存的数据时，返回数据，并不再重新请求网络。
     */
    public boolean needOnlyLast;

    /**
     * 是否需要进度条和错误提示，默认为false。如果为true，则从本次网络请求开始至结束，显示一个dialog进度显示
     */
    public boolean needToast;

    /**
     * 当请求异常，执行{@link OnTaskCompleteListener#onTaskFailed(String, int)}
     * 时,自动提示错误信息
     */
    public boolean needFailedToast = true;

    /**
     * 是否需要在请求异常时自动重试，默认为false。自动重试仅持续数次，数分钟。
     */
    public boolean needRestart;

    /**
     * 是否必定需要定位，默认为false。如果为true，则在网络请求之前会等待定位成功，如已定位成功则正常流程。
     */
    public boolean needLocation;

    /**
     * 是否需要传header
     */
    public boolean needHeader = true;

    /**
     * 请求是否正在执行中。
     */
    public boolean isSending;

    private int delay = 1, progress;

    /**
     * 请求方式，默认为post。
     */
    public OkHttpManager.MethodType method_type = OkHttpManager.MethodType.Post;
    public String loadingText = "加载中";

    protected static HashMap<String, String> headers;

    /**
     * 获取具体的请求名，拼接在{@link #getRequestUrl()}之后
     *
     * @return
     */
    protected abstract String getApiMethodName();

    /**
     * 获取请求url固定的前缀
     *
     * @return
     */
    protected abstract String getRequestUrl();

    /**
     * 处理服务器返回的json，返回实体对象。建议以{@link AbsModel#AbsModel(JSONObject)}构造方法的形式返回对象
     *
     * @param json
     * @return
     * @throws Throwable
     */
    protected abstract T parseJson(JSONObject json) throws Throwable;

    public AbsTask(Context context, AbsRequest request) {
        this(context, request, null);
    }

    public AbsTask(Context context, AbsRequest request, OnTaskCompleteListener<T> completeListener) {
        this.weakReference = new WeakReference<Context>(context);
        this.request = request;
        addListener(completeListener);
        init();
    }

    public final Context getContext() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    /**
     * 增加网络请求完成后的监听。如调用时注册的{@link Context}是{@link Activity},本方法注册的监听会在主线程中调用，如该
     * {@link Activity}已经调用被杀死或调用了{@link Activity#finish()},则不会触发回调。
     *
     * @param completeListener
     */
    public final void addListener(OnTaskCompleteListener<T> completeListener) {
        if (completeListener != null) {
            if (onTaskPostCompleteListeners == null) {
                onTaskPostCompleteListeners = new HashSet<OnTaskCompleteListener<T>>();
            }
            onTaskPostCompleteListeners.add(completeListener);
        }
    }

    /**
     * 增加网络请求完成后的监听。本方法注册的监听会在异步线程中直接调用，所以回调中请勿执行修改界面的操作。无论注册时的{@link Activity}
     * 有没有被销毁，本回调都会执行，适合用于非界面的回调操作。
     *
     * @param completeListener
     */
    public final void addListenerWithOutPost(OnTaskCompleteListener<T> completeListener) {
        if (completeListener != null) {
            if (onTaskCompleteListeners == null) {
                onTaskCompleteListeners = new HashSet<>();
            }
            onTaskCompleteListeners.add(completeListener);
        }
    }

    public final void setContext(Context context) {
        this.weakReference = new WeakReference<>(context);
    }

    /**
     * 初始化请求的参数，如{@link #needToast},{@link #needLast}
     */
    protected void init() {
    }

    protected T doInBackground(MyThread<T> thread) throws Throwable {
        Context context = getContext();

        if (!thread.isCancelled && context != null && !isActivityFinishing(context)) {
            // initParms();

            String url = getApiMethodName();
            if (!url.startsWith("http")) {
                url = getRequestUrl() + url;
            }

            HashMap<String, String> headers;
            if (needHeader) {
                headers = initHeaders();
            } else {
                headers = null;
            }

            Response response;

            if (needUploadFile && request != null) {
                response = OkHttpManager.getResponse(hashCode(), url, headers, request.getMultipartEntity());
            } else {
                response = OkHttpManager.getResponse(hashCode(), url, headers, request == null ? null : request.getBody(), method_type);
            }

            if (thread.isCancelled || response == null || response.body() == null) {
                return null;
            }

            String s = response.body().string().trim();
//            if(this.lastResponse != null && this.lastResponse.equals(s)) {
//                this.lastResponse = null;
//                return null;
//            }
            try {
                response.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }

            if (!this.checkResponse(s)) {
                return null;
            }

            T t = handleResponse(s);
            if (t == null) {
                return null;
            } else {
                if (needLast && !thread.isLoadMore) {
                    saveLast(s);
                }
                return t;
            }
        }
        return null;
    }


    protected T handleResponse(String response) throws Throwable {
        return parseJson(new JSONObject(response));
    }

    protected boolean checkResponse(String s) {
        if ((s.toLowerCase().startsWith("<html") || s.toLowerCase().startsWith("<!doctype")) && s.toLowerCase().contains("</html>") && s.toLowerCase().contains("</body>") && s.toLowerCase().contains("</head>")) {
            this.failed("网络异常", 0);
            return false;
        } else {
            return true;
        }
    }


    protected HashMap<String, String> initHeaders() {
        return null;
    }

    protected final static boolean isActivityFinishing(Context context) {
        return context instanceof Activity && ((Activity) context).isFinishing();
    }

    // @SuppressWarnings("unused")
    // private static void initParms() {
    // if (headers == null) {
    // headers = new HashMap<String, String>();
    // headers.put("RELEASE", android.os.Build.VERSION.RELEASE);
    // headers.put("SDK", String.valueOf(android.os.Build.VERSION.SDK_INT));
    // headers.put("MODEL", android.os.Build.MODEL);
    // // headers.put("Appid", AndroidUtil.getAppId());
    // // headers.put("version", WeimiPreferences.version);
    // // headers.put("versionCode", ZUtil.versionCode);
    // // headers.put("primarykey",
    // // String.valueOf(Preferences.PRIMARYKEY));
    // // headers.put("mid", PreferencesUtils.getLocationCityId(context));
    // // headers.put("website", "zuo");
    // headers.put("device", "android");
    // headers.put("udid", ((TelephonyManager)
    // AbsApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
    // }
    //
    // // if (WeimiServiceConnection.weimiAIDL != null) {
    // // headers.put("igexinid", MApplication.getIgexinId());
    // // // Log.i("nero", ":" +
    // // // WeimiServiceConnection.weimiAIDL.getIgexin_id());
    // // }
    //
    // BDLocation location = MyLocationManager.getLocation();
    // if (location != null) {
    // headers.put("nklatitude", String.valueOf(location.getLatitude()));
    // headers.put("nklongitude", String.valueOf(location.getLongitude()));
    // }
    // // headers.put("forstat", "0"));
    // }

    private final void initDialog() {
        if (dialog == null) {
            Context context = getContext();
            if (context != null) {
                dialog = new Dialog(context, R.style.transparent_dialog);
                if (progressDialogType == 0) {
                    progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
                    progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_task));
                    progressBar.setMax(100);

                    dialog.setContentView(progressBar, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, DipUtils.getIntDip(3)));

                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.gravity = Gravity.TOP;
                    lp.y = progressY;
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = DipUtils.getIntDip(3);
                    dialogWindow.setAttributes(lp);
                } else {
                    progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyle);
                    if(getLoadingIndeterminate()!=null){
                        progressBar.setIndeterminateDrawable(getLoadingIndeterminate());
                    }
                    dialog.setContentView(progressBar);
                }
                dialog.setCanceledOnTouchOutside(false);
            }
        }
    }

    protected Drawable getLoadingIndeterminate(){
        return null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        cancel();
    }

    /**
     * 直接在当前线程中访问网络，并返回结果，会卡住线程。
     *
     * @return
     */
    public final T get() {
        if (thread != null) {
            thread.cancel();
        }

        isSending = true;

        try {
            T t = ThreadPoolManager.Companion.httpSubmit(thread = new MyThread<T>(this)).get(60, TimeUnit.SECONDS);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isSending = false;
        }
        return null;
    }

    /**
     * 开始执行请求，请求完成时调用 {@link OnTaskCompleteListener#onTaskComplete(Object)}
     */
    public void start() {
        start(false, false);
    }

    /**
     * 开始执行请求
     *
     * @param isLoadMore 是否是加载更多的请求。如果为true，则请求完成时调用
     *                   {@link OnTaskCompleteListener#onTaskLoadMoreComplete(Object)}
     *                   ;如果为false，则请求完成时调用
     *                   {@link OnTaskCompleteListener#onTaskComplete(Object)}
     */
    public void start(boolean isLoadMore) {
        start(isLoadMore, false);
    }

    protected void start(final boolean isLoadMore, final boolean isRestart) {
        isSending = true;

        ThreadPoolManager.Companion.cacheExecute(new Runnable() {

            @Override
            public void run() {
                Context context = getContext();
                if (context != null) {
                    boolean loadedLast = false;
                    if (thread == null) {
                        thread = new MyThread<T>(AbsTask.this);
                        if (needLast && !isLoadMore) {
                            T result = loadLast();
                            if (result != null) {
                                loadedLast = true;
                                completed(result, isLoadMore, true);
                                if (needOnlyLast) {
                                    isSending = false;
                                    return;
                                }
                            }
                        }
                    } else {
                        thread.cancel();
                        thread = new MyThread<T>(AbsTask.this);
                        thread.isRestart = isRestart;
                    }

                    if (!OkHttpManager.Companion.isNetworkAvailable()) {
                        if (!loadedLast) {
                            String error = "网络异常，请确认是否联网";
                            if (needToast && needFailedToast) {
                                ToastUtil.showErrorToast(error);
                            }
                            failed(error, 0);
                        }
                        return;
                    }

                    thread.isLoadMore = isLoadMore;

                    if (needToast && !thread.isRestart && !isLoadMore) {
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            if (!activity.isFinishing()) {
                                activity.runOnUiThread(()->
                                    showDialog()
                                );
                            }
                        } else {
                            MainHandlerUtil.INSTANCE.post(()->
                                showDialog()
                            );
                        }
                    }
                    try {
                        ThreadPoolManager.Companion.httpSubmit(thread).get(600, TimeUnit.SECONDS);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        if (e instanceof TimeoutException) {
                            error("请求超时", 0);
                        } else {
                            error(e.getMessage(), 0);
                        }
                    }
                }
            }
        });
    }

    private void showDialog() {
        initDialog();

        if (dialog != null && !dialog.isShowing()) {
            dialog.setCancelable(cancelable);
            if (cancelable) {
                dialog.setOnCancelListener(AbsTask.this);
            }

            Context context = getContext();
            if (context != null && context instanceof Activity && !((Activity) context).isFinishing()) {
                try {
                    dialog.show();
                } catch (Throwable e) {
                }
                if (progressBar != null) {
                    progressBar.setProgress(progress = 0);
                    setProgress();
                }
            }
        }
    }

    private void setProgress() {
        ThreadPoolManager.Companion.cacheExecute(()->{
            while (dialog.isShowing() && progress < 90) {
                progress += 3;

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getContext() != null && getContext() instanceof Activity) {
                    Activity activity = (Activity) getContext();
                    if (activity != null && !activity.isFinishing()) {
                        activity.runOnUiThread(AbsTask.this);
                    }
                }
            }
        });
    }

    @Override
    public final void run() {
        progressBar.setProgress(progress);
    }

    protected final T loadLast() {
        Context context = getContext();
        if (context != null) {
            FileInputStream fis = null;
            try {
                fis = context.openFileInput(getClass().getName() + getLastTag());

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] data = new byte[512];
                int count = -1;
                while ((count = fis.read(data, 0, data.length)) != -1) {
                    outStream.write(data, 0, count);
                }

                return parseJson(new JSONObject(new String(outStream.toByteArray(), "UTF-8")));
            } catch (Throwable e) {
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    protected final void saveLast(String s) {
        Context context = getContext();
        if (context != null) {
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(getClass().getName() + getLastTag(), Context.MODE_PRIVATE);
                fos.write(s.getBytes());
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 继承此方法，返回缓存数据的唯一标识符，缓存的数据会以本次唯一标识符作为标识，之后有请求与缓存的标识一致时，则返回该缓存，否则直接请求网络。
     * {@link #needLast}为false时本方法无效。
     *
     * @return
     */
    protected String getLastTag() {
        return "";
    }

    /**
     * 取消网络请求，如请求正在执行中会中断，并执行{@link OnTaskCompleteListener#onTaskCancel()}
     */
    public final void cancel() {
        if (thread != null) {
            thread.cancel(true);
        }
    }

    protected final static boolean isRunOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void completed(final T result, final boolean isLoadMore, final boolean isCache) {
        delay = 1;

        if (onTaskCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskCompleteListeners) {
                if (isLoadMore) {
                    onTaskCompleteListener.onTaskLoadMoreComplete(result);
                } else {
                    onTaskCompleteListener.onTaskComplete(result);
                }
            }
        }

        if (!thread.isCancelled) {
            Context context = getContext();
            if (context != null) {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing()) {
                        if (isRunOnMainThread()) {
                            handleComplete(result, isLoadMore);
                            if (!isCache) {
                                isSending = false;
                            }
                        } else {
                            activity.runOnUiThread(()->{
                                Activity activity1 = (Activity) getContext();
                                if (activity1 != null && !activity1.isFinishing()) {
                                    handleComplete(result, isLoadMore);
                                    if (!isCache) {
                                        isSending = false;
                                    }
                                }
                            });
                        }
                    }
                } else {
                    handleComplete(result, isLoadMore);
                    if (!isCache) {
                        isSending = false;
                    }
                }
            }
        } else if (!isCache) {
            isSending = false;
        }
    }

    private void handleComplete(T result, boolean isLoadMore) {
        if (needToast && dialog != null && dialog.isShowing()) {
            if (progressBar != null) {
                progressBar.setProgress(progress = 100);
            }

            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }

        if (result != null && onTaskPostCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskPostCompleteListeners) {
                if (isLoadMore) {
                    onTaskCompleteListener.onTaskLoadMoreComplete(result);
                } else {
                    onTaskCompleteListener.onTaskComplete(result);
                }
            }
        }
    }

    protected final void failed(String error, final int code) {
        if (error == null || error.isEmpty() || error.toLowerCase().equals("null")) {
            error = "数据异常";
        }

        if (thread != null && !thread.isCancelled) {
            if (onTaskCompleteListeners != null) {
                for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskCompleteListeners) {
                    onTaskCompleteListener.onTaskFailed(error, code);
                }
            }

            Context context = getContext();
            if (context != null) {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing()) {
                        if (isRunOnMainThread()) {
                            handleFail(error, code);
                        } else {
                            final String temp = error;
                            activity.runOnUiThread(()->{
                                Activity activity1 = (Activity) getContext();
                                if (activity1 != null && !activity1.isFinishing()) {
                                    handleFail(temp, code);
                                }
                            });
                        }
                    }
                } else {
                    handleFail(error, code);
                }
            }
        } else {
            isSending = false;
        }
    }

    private void handleFail(String error, int code) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }

        if (needToast && needFailedToast) {
            ToastUtil.showErrorToast(error);
        }

        if (onTaskPostCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskPostCompleteListeners) {
                onTaskCompleteListener.onTaskFailed(error, code);
            }
        }

        isSending = false;
    }

    private void onCancel() {
        if (onTaskCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskCompleteListeners) {
                onTaskCompleteListener.onTaskCancel();
            }
        }

        Context context = getContext();
        if (context != null) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (!activity.isFinishing()) {
                    if (isRunOnMainThread()) {
                        handleCancel();
                    } else {
                        activity.runOnUiThread(()->{
                            Activity activity1 = (Activity) getContext();
                            if (activity1 != null && !activity1.isFinishing()) {
                                handleCancel();
                            }
                        });
                    }
                }
            } else {
                handleCancel();
            }
        }
    }

    private void handleCancel() {
        if (onTaskPostCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskPostCompleteListeners) {
                onTaskCompleteListener.onTaskCancel();
            }
        }
    }

    private void error(final String error, final int code) {
        Log.i("mao", getClass().getSimpleName() + ":" + error);
        if (thread != null && !thread.isCancelled) {
            if (needRestart) {
                if (delay < 60 && thread != null && !thread.isCancelled) {
                    isSending=false;
                    ThreadPoolManager.Companion.cacheExecute(()->{
                        try {
                            Thread.sleep((delay = delay * 2) * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (needRestart && thread != null && !thread.isCancelled && !isSending) {
                            AbsTask.this.start(thread.isLoadMore, true);
                        }
                    });
                } else {
                    delay = 1;
                    failed(error, code);
                }
            } else {
                failed(error, code);
            }
        } else {
            isSending = false;
        }
    }

    protected static final class MyThread<T extends Serializable> implements Callable<T> {
        public boolean isCancelled, isRestart, isLoadMore;
        private AbsTask<T> absTask;

        public MyThread(AbsTask<T> absTask) {
            this.absTask = absTask;
        }

        @Override
        public T call() {

            final AbsTask<T> absTask = this.absTask;

            if (absTask == null) {
                return null;
            }

            if (absTask.needLocation) {
                waitLocation();
            }

            if (isCancelled) {
                absTask.isSending = false;
                return null;
            }

            T result = null;

            try {
                result = absTask.doInBackground(this);
            } catch (Throwable e) {
                e.printStackTrace();
//                if (absTask.needUploadFile && absTask.request != null) {
//                }
                absTask.error(e.getMessage(), 0);
            }

            if (!isCancelled && result != null) {
                absTask.completed(result, isLoadMore, false);
            }

            this.absTask = null;

            return result;
        }

        protected boolean waitLocation() {
//            for (int i = 0; i < 100 && !isCancelled && MyLocationManager.getLocation() == null && MyLocationManager.isLocationOpen(); i++) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            return true;
        }

        private void cancel() {
            cancel(false);
        }

        private void cancel(boolean doListener) {
            final AbsTask<T> absTask = this.absTask;

            if (absTask == null) {
                return;
            }

            if (!isCancelled && absTask.isSending) {
                OkHttpManager.Companion.cancel(absTask.hashCode());

                absTask.isSending = false;
                isCancelled = true;

                if (absTask.needToast && absTask.dialog != null && absTask.dialog.isShowing()) {
                    try {
                        absTask.dialog.dismiss();
                    } catch (Exception e) {
                    }
                }

                if (doListener) {
                    absTask.onCancel();
                }

                this.absTask = null;
            }
        }
    }
}