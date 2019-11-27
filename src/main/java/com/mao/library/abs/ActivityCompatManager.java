package com.mao.library.abs;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.mao.library.interfaces.ActivityInterface;

import java.util.ArrayList;
import java.util.List;

public class ActivityCompatManager extends AbsActivityCompat {

    public static void init() {
        Companion.setInstance(new ActivityCompatManager());
    }

    public static ActivityCompatManager getInstance() {
        return (ActivityCompatManager) AbsActivityCompat.Companion.getInstance();
    }

    @Override
    public void onCreate(ActivityInterface absActivity, Bundle savedInstanceState) {

        AbsActivityManager.getInstance().onCreate(absActivity);
    }


    public void onSetContentView(ActivityInterface activity) {
        if (Build.VERSION.SDK_INT >= 19) {
           changeToMyFrameLayout(activity);
        }
    }

    private void changeToMyFrameLayout(ActivityInterface activity) {
        ViewGroup decorView = activity.getDecorView();
        int childCount = decorView.getChildCount();
        if (childCount > 0) {
            List<View> list = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                list.add(decorView.getChildAt(i));
            }
            decorView.removeAllViews();

            MyFrameLayout frameLayout = new MyFrameLayout(activity.getActivity());
            for (View child : list) {
                frameLayout.addView(child);
            }
            decorView.addView(frameLayout);

            frameLayout.setBackground(decorView.getBackground());
            decorView.setBackground(null);
        }
    }

    public static final class MyFrameLayout extends FrameLayout {

        public MyFrameLayout(Context context) {
            super(context);
        }

        @Override
        public WindowInsets onApplyWindowInsets(WindowInsets insets) {
            return super.onApplyWindowInsets(insets);
        }


        @Override
        protected boolean fitSystemWindows(Rect insets) {
            int insetBottom = insets.bottom;

            if(getPaddingBottom()!=insetBottom){
                setPadding(getPaddingLeft(), getPaddingTop(),getPaddingRight(), insetBottom);
            }
            return true;
        }
    }
}
