package com.lr.best.ui.moudle.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.db.DataBaseHelper;
import com.lr.best.mywidget.view.CountDownProgressView;
import com.lr.best.utils.tool.AppUtil;
import com.lr.best.utils.tool.HandlerUtil;
import com.lr.best.utils.tool.JSONUtil;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;

import java.io.File;
import java.util.Map;

import butterknife.BindView;


/**
 * 初始化程序界面
 */
public class SplashActivity extends BasicActivity {

    @BindView(R.id.splash_view)
    ImageView mMainView;
    @BindView(R.id.splash_logo)
    ImageView mSplashLogo;
    @BindView(R.id.splash_loading_item)
    ImageView mSplashImageView;
    @BindView(R.id.relativeLayout1)
    RelativeLayout mRelativeLayout1;
    @BindView(R.id.version_name)
    TextView mNameView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.countdownProgressView)
    CountDownProgressView mCountdownProgressView;
    @BindView(R.id.tiaoguo_lay)
    LinearLayout mTiaoGuoLay;


    private Animation mRotateAnimation;
    /**
     * 旋转动画的时间
     */
    static final int ROTATION_ANIMATION_DURATION = 1200;
    /**
     * 动画插值
     */
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();

    private SharedPreferences mShared;//存放配置信息的文件

    private DataBaseHelper dataBaseHelper;
    // 声明控件对象
    private int count = 2;
    private Animation animation;
    private boolean isJump = false;
    private boolean isClick = false;


    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }


    @Override
    public int getContentView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            MbsConstans.ALPHA = 100;
            MbsConstans.TOP_BAR_COLOR = R.color.top_bar_bg;
            StatusBarUtil.setTranslucent(this, MbsConstans.ALPHA);
        } else {
            MbsConstans.ALPHA = 0;
            MbsConstans.TOP_BAR_COLOR = R.color.top_bar_bg;
            StatusBarUtil.setTranslucent(this, MbsConstans.ALPHA);
        }

        return R.layout.activity_splash;
    }


    @Override
    public void init() {

        mShared = getSharedPreferences(MbsConstans.SharedInfoConstans.LOGIN_INFO, Context.MODE_PRIVATE);
        AppUtil.getInstance(this).getAppVersion();
        setupView();
        initView();
        //loadImage();
        //imageTask();

    }





    private void setupView() {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_text);
        mTiaoGuoLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerUtil.init(SplashActivity.this).postMessage(null, null, 0, 0);
            }
        });
        mNameView.setText(MbsConstans.UpdateAppConstans.VERSION_APP_NAME);
        mNameView.setVisibility(View.GONE);
        textView.setText(count + "");

        mCountdownProgressView.setTimeMillis(2000);
        mCountdownProgressView.start();
        mCountdownProgressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress<=0){
                    HandlerUtil.init(SplashActivity.this).postMessage(null, null, 0, 0);
                }
            }
        });
        mCountdownProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerUtil.init(SplashActivity.this).postMessage(null, null, 0, 0);
            }
        });
    }

    private void initView() {
        initSqlit();
       // initAnimation();
       // HandlerUtil.init(this).postMessage(null, null, 500, count);

        HandlerUtil.doMessage(new HandlerUtil.MessageCallBack() {
            @Override
            public void runHandleMessage(Message msg) {
                if (msg.what == 0) {
                    boolean bb = (boolean) SPUtils.get(SplashActivity.this, MbsConstans.SharedInfoConstans.LOGIN_OUT, true);

                    String code = SPUtils.get(SplashActivity.this, MbsConstans.SharedInfoConstans.IS_FIRST_START, "") + "";
                    Intent intent;
                    //去掉引导页
                   // if (code.equals(MbsConstans.UpdateAppConstans.VERSION_APP_CODE + "")) {
                        if (bb) {
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                        } else {
                            MbsConstans.ACCESS_TOKEN = SPUtils.get(SplashActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
                            LogUtilDebug.i("show","MbsConstans.ACCESS_TOKEN >"+MbsConstans.ACCESS_TOKEN);
                            //MbsConstans.REFRESH_TOKEN = SPUtils.get(SplashActivity.this, MbsConstans.SharedInfoConstans.REFRESH_TOKEN, "").toString();
                            String s = SPUtils.get(SplashActivity.this, MbsConstans.SharedInfoConstans.LOGIN_INFO, "").toString();
                            MbsConstans.USER_MAP = JSONUtil.getInstance().jsonMap(s);
                            if (MbsConstans.USER_MAP == null || MbsConstans.USER_MAP.isEmpty() || UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
                                intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                MbsConstans.USER_MAP = JSONUtil.getInstance().jsonMap(s);
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    /*} else {
                        intent = new Intent(SplashActivity.this, GuideViewPageActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out);
                        finish();
                    }*/
                } else {
                    count--;
                    textView.setText(count + "");
                    animation.reset();
                    textView.startAnimation(animation);
                    HandlerUtil.init(SplashActivity.this).postMessage(null, null, 1000, count);
                }


            }
        });

    }

    private void initAnimation() {

	/*	new Handler(){
,
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String code = mShared.getString(MbsConstans.SharedInfoConstans.IS_FIRST_START, "");
				Intent intent ;
				if (code.equals(MbsConstans.UpdateAppConstans.VERSION_APP_CODE+"")) {
					intent = new Intent(SplashActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
					//loadImage();
				}else{
					intent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					SplashActivity.this.finish();
				}

			}
		}.sendEmptyMessageDelayed(0, 5000);*/


		/*float pivotValue = 0.5f;
		float toDegree = 360.0f;
		mRotateAnimation = new RotateAnimation(toDegree, toDegree, Animation.RELATIVE_TO_SELF, pivotValue,
				Animation.RELATIVE_TO_SELF, pivotValue);
		mRotateAnimation.setFillAfter(true);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		//mRotateAnimation.setRepeatCount(Animation.INFINITE);
		//mRotateAnimation.setRepeatMode(Animation.RESTART);
		mRotateAnimation.setDuration(1000);
		mSplashImageView.startAnimation(mRotateAnimation);

		mRotateAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				String code = mShared.getString(MbsConstans.SharedInfoConstans.IS_FIRST_START, "");
				Intent intent ;
				if (code.equals(MbsConstans.UpdateAppConstans.VERSION_APP_CODE+"")) {
					intent = new Intent(SplashActivity.this, MainActivity.class);
				}else{
					intent = new Intent(SplashActivity.this, GuideViewPageActivity.class);
				}
				startActivity(intent);
				overridePendingTransition(R.anim.splash_fade_in,R.anim.splash_fade_out);
				SplashActivity.this.finish();
			}
		});*/
    }

    private void initSqlit() {
        dataBaseHelper = new DataBaseHelper(this, MbsConstans.DATABASE_NAME, null, 1);
        dataBaseHelper.getWritableDatabase();

        File dbFile = this.getDatabasePath(MbsConstans.DATABASE_NAME);
        if (!dbFile.exists()) {

        } else {

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HandlerUtil.release();
    }

    private void getCount() {
        count--;
        textView.setText(count + "");
        animation.reset();
        textView.startAnimation(animation);
    }

    /**
     * @descriptoin 请求前加载progress
     * @author dc
     * @date 2017/2/16 11:00
     */
    @Override
    public void showProgress() {

    }

    /**
     * @descriptoin 请求结束之后隐藏progress
     * @author dc
     * @date 2017/2/16 11:01
     */
    @Override
    public void disimissProgress() {

    }

    /**
     * @param tData 数据类型
     * @param mType
     * @descriptoin 请求数据成功
     * @author dc
     * @date 2017/2/16 11:01
     */
    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {

    }

    /**
     * @param map
     * @param mType
     * @descriptoin 请求数据错误
     * @date 2017/2/16 11:01
     */
    @Override
    public void loadDataError(Map<String, Object> map, String mType) {

    }


}

