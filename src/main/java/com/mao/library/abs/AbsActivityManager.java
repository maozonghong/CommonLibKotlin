package com.mao.library.abs;

import android.util.Log;

import java.util.HashMap;

import com.mao.library.interfaces.ActivityInterface;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
public class AbsActivityManager {
    protected final HashMap<String, ActivityInterface> mActivities = new HashMap<String, ActivityInterface>();
    protected ActivityInterface mCurrentActivity, mTopActivity;

    protected static AbsActivityManager instance;

    protected AbsActivityManager() {
    }

    public static AbsActivityManager getInstance() {
        if (instance == null) {
            instance = new AbsActivityManager();
        }
        return instance;
    }

    public void onCreate(ActivityInterface activity) {
    }

    public void finishActivities() {
        for (ActivityInterface activity : mActivities.values()) {
            activity.setHasFinishAnimation(true);
            Log.i("mao", "finish:" + activity.getClass().getSimpleName());
            activity.finish();
        }
        mActivities.clear();
    }

    public void finishBackActivities() {
        for (ActivityInterface activity : mActivities.values()) {
            if (activity != mCurrentActivity) {
                Log.i("mao", "finish:" + activity.getClass().getSimpleName());
                activity.setHasFinishAnimation(true);
                activity.finish();
            }
        }
        mActivities.clear();
        if (mCurrentActivity != null) {
            mActivities.put(mCurrentActivity.getClass().getName(), mCurrentActivity);
            onCreate(mCurrentActivity);
        }
    }

    public void onDestroy(ActivityInterface activity) {
        mActivities.remove(activity.getClass().getName());
    }

    public boolean onResume(ActivityInterface activity) {
        mActivities.put(activity.getClass().getName(), activity);
        mCurrentActivity = activity;
        mTopActivity = activity;
        return true;
    }

    public boolean onPause(ActivityInterface activity) {
        if (activity == mCurrentActivity) {
            mCurrentActivity = null;
            mTopActivity=null;
        }
        return true;
    }

    public boolean finishActivity(Class<? extends AbsActivity> clz) {
        ActivityInterface activity = mActivities.get(clz.getName());
        if (activity != null) {
            activity.setHasFinishAnimation(true);
            activity.finish();
            return true;
        }
        return false;
    }

    public ActivityInterface getCurrentActivity() {
        return mCurrentActivity;
    }

    public ActivityInterface getTopActivity() {
        return mTopActivity;
    }

    public int getActivityCount() {
        return mActivities == null ? 0 : mActivities.size();
    }

    public ActivityInterface getActivity(String className) {
        return mActivities == null ? null : mActivities.get(className);
    }
}
