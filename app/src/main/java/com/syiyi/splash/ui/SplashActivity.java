package com.syiyi.splash.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.syiyi.splash.R;
import com.syiyi.splash.util.CommonUtil;
import com.syiyi.splash.util.SharePerferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *启动引导页
 */
public class SplashActivity extends Activity implements Handler.Callback {
    private static final int SHOW_START_PAGE_SP = 0X001;
    private static final int SHOW_START_PAGE = 0X002;
    @Bind(R.id.vp_splash)
    ViewPager mVpSplash;
    @Bind(R.id.rl_splash)
    RelativeLayout mRlSplash;
    @Bind(R.id.rl_startPage)
    RelativeLayout mRlStartPage;
    @Bind(R.id.rg_point)
    RadioGroup mRgPoint;
    @Bind(R.id.btn_enter)
    Button mBtnEnter;

    private int[] splash_imgs = new int[]{R.drawable.splash0, R.drawable.splash1, R.drawable.splash2};
    private ImageView[] splash_imageViews = new ImageView[splash_imgs.length];
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CommonUtil.showFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mHandler = new Handler(this);
        if (CommonUtil.isUpdateMode(this)) {
            SharePerferencesUtil.saveVersion(this);
            showSplash();
        } else {
            showStartPage(SHOW_START_PAGE, 3000);
        }

    }

    @OnClick(R.id.btn_enter)
    public void onClick(View v) {
        loadMainAPP();
    }

    /**
     * @param mode 启动模式，是否启动splash
     * @param time 等待时间
     */
    private void showStartPage(int mode, long time) {

        mRlStartPage.setVisibility(View.VISIBLE);
        Message msg = mHandler.obtainMessage();
        msg.what = mode;
        mHandler.sendMessageDelayed(msg, time);
    }

    /**
     * 显示splash
     */
    private void showSplash() {
        showStartPage(SHOW_START_PAGE_SP, 1000);
        for (int i = 0; i < splash_imgs.length; i++) {
            View point = getLayoutInflater().inflate(R.layout.point2viewpage, null);
            point.setTag(i);
            mRgPoint.addView(point);
            ImageView iv = new ImageView(SplashActivity.this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(splash_imgs[i]);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            splash_imageViews[i] = iv;
        }
        mRgPoint.check(mRgPoint.getChildAt(0).getId());
        mVpSplash.setAdapter(new SplashAdapter());
        mVpSplash.setOffscreenPageLimit(2);
        mVpSplash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRgPoint.check(mRgPoint.getChildAt(position).getId());
                if (position == splash_imgs.length - 1) {
                    mRgPoint.setVisibility(View.GONE);
                    mBtnEnter.setVisibility(View.VISIBLE);
                } else {
                    mRgPoint.setVisibility(View.VISIBLE);
                    mBtnEnter.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRgPoint.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mVpSplash.setCurrentItem((int) group.findViewById(checkedId).getTag());
            }
        });

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_START_PAGE:
                loadMainAPP();
                break;
            case SHOW_START_PAGE_SP:
                mRlStartPage.setVisibility(View.GONE);
                mRlSplash.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }

    /**
     * 进入app主界面
     */
    private void loadMainAPP() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    class SplashAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return splash_imageViews.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(splash_imageViews[position]);
            return splash_imageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(splash_imageViews[position]);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
