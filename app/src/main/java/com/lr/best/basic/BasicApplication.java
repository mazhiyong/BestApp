package com.lr.best.basic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.lr.best.BuildConfig;
import com.lr.best.R;
import com.lr.best.api.Config;

import com.lr.best.utils.tool.AppContextUtil;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.tencent.bugly.Bugly;
import com.wanou.framelibrary.okgoutil.websocket.WsManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.wildfire.chat.kit.WfcUIKit;
import cn.wildfire.chat.kit.conversation.message.viewholder.MessageViewHolderManager;
import cn.wildfire.chat.kit.location.viewholder.LocationMessageContentViewHolder;
import okhttp3.OkHttpClient;

import static com.wanou.framelibrary.okgoutil.OkGoUtils.TIMEOUT_SECOND;


public class BasicApplication extends MultiDexApplication {
	public  int appCount=0;

	private WfcUIKit wfcUIKit;

	private static Context mContext;
	public static Typeface typeFace;
	/*public void setTypeface(){
		typeFace = Typeface.createFromAsset(getAssets(), "fonts/gagayi.ttf");
		try
		{
			Field field = Typeface.class.getDeclaredField("SERIF");
			field.setAccessible(true);
			field.set(null, typeFace);
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}*/

	private static WsManager wsManager;
	private static WsManager wsManager1;
	private static WsManager wsManager2;
	private static WsManager wsManager3;

	public static WsManager getWsManager() {
		return wsManager;
	}
	public static WsManager getWsManager1() {
		return wsManager1;
	}

	public static WsManager getWsManager2() {
		return wsManager2;
	}

	public static WsManager getWsManager3() {
		return wsManager3;
	}

	private static long mMainThreadId;//主线程id
	private static Handler mHandler;//主线程Handler

	@Override
	public void onCreate() {
		super.onCreate();

		mMainThreadId = android.os.Process.myTid();
		mHandler = new Handler();


		wfcUIKit = new WfcUIKit();
		wfcUIKit.init(this);
		//注册推送服务
		//PushService.init(this, BuildConfig.APPLICATION_ID);
		//注册位置信息
		MessageViewHolderManager.getInstance().registerMessageViewHolder(LocationMessageContentViewHolder.class);
		setupWFCDirs();
		Stetho.initializeWithDefaults(this);

		// 微信分享初始化
		//WXManager.getInstance().init(this);

		registerActivityListener();
		//setTypeface();
		AppContextUtil.init(this);

		/*Context context = getApplicationContext();
		// 获取当前包名
		String packageName = context.getPackageName();
		// 获取当前进程名
		String processName = getProcessName(android.os.Process.myPid());
		// 设置是否为上报进程
		CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
		strategy.setUploadProcess(processName == null || processName.equals(packageName));

		CrashReport.initCrashReport(context, "5f54eab52c", true, strategy);*/

		/**
		 * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
		 * 第一个参数：应用程序上下文
		 * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
		 */
		BGASwipeBackHelper.init(this,  null);

		Bugly.init(this, "006c11db32", false);

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				/*CrashHandler mUncaughtHandler= CrashHandler.getmContext();
				mUncaughtHandler.init(getApplicationContext());*/
				mContext = getApplicationContext();
			}
		});

		initWS();
		initWS1();
		initWS2();
		initWS3();


	}


	private void initWS() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("webSocket");
		if (BuildConfig.DEBUG) {
			loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
		}
		loggingInterceptor.setColorLevel(Level.WARNING);
		builder.addInterceptor(loggingInterceptor);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		//cookie的缓存设置
		builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
		OkHttpClient websocketBuild = builder.build();
		wsManager = new WsManager.Builder(this)
				.wsUrl(MbsConstans.DEPTH_LEVER)
				.needReconnect(true)
				.client(websocketBuild)
				.build();


	}
	private void initWS1() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("webSocket");
		if (BuildConfig.DEBUG) {
			loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
		}
		loggingInterceptor.setColorLevel(Level.WARNING);
		builder.addInterceptor(loggingInterceptor);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		//cookie的缓存设置
		builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
		OkHttpClient websocketBuild = builder.build();
		wsManager1 = new WsManager.Builder(this)
				.wsUrl(MbsConstans.CURRENT_PRICE_URL)
				.needReconnect(true)
				.client(websocketBuild)
				.build();


	}

	private void initWS2() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("webSocket");
		if (BuildConfig.DEBUG) {
			loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
		}
		loggingInterceptor.setColorLevel(Level.WARNING);
		builder.addInterceptor(loggingInterceptor);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		//cookie的缓存设置
		builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
		OkHttpClient websocketBuild = builder.build();
		wsManager2 = new WsManager.Builder(this)
				.wsUrl(MbsConstans.KLINE_WEBSOCKET_URL)
				.needReconnect(true)
				.client(websocketBuild)
				.build();


	}


	private void initWS3() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("webSocket");
		if (BuildConfig.DEBUG) {
			loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
		}
		loggingInterceptor.setColorLevel(Level.WARNING);
		builder.addInterceptor(loggingInterceptor);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
		//cookie的缓存设置
		builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
		OkHttpClient websocketBuild = builder.build();
		wsManager3 = new WsManager.Builder(this)
				.wsUrl(MbsConstans.HUOBI_WEBSOCKET)
				.needReconnect(true)
				.client(websocketBuild)
				.build();


	}



	public static Context getContext() {
		return mContext;
	}


	/* (non-Javadoc)
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
	}



	/**
	 * 获取进程号对应的进程名
	 *
	 * @param pid 进程号
	 * @return 进程名
	 */
	private static String getProcessName(int pid) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName)) {
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	private void registerActivityListener() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			registerActivityLifecycleCallbacks(
					new ActivityLifecycleCallbacks() {

						@Override
						public void onActivityCreated(Activity activity, Bundle
								savedInstanceState) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onActivityStarted(Activity activity) {
							// TODO Auto-generated method stub
							appCount++;
						}
						@Override
						public void onActivityResumed(Activity activity) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onActivityPaused(Activity activity) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onActivityStopped(Activity activity) {
							// TODO Auto-generated method stub
							appCount--;
							if(appCount==0){
								Toast.makeText(getApplicationContext(),
										getResources().getString(R.string.app_name_main)+"应用进入后台运行",
										Toast.LENGTH_LONG).show();
							}
						}
						@Override
						public void onActivitySaveInstanceState(Activity activity, Bundle
								outState) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onActivityDestroyed(Activity activity) {
							// TODO Auto-generated method stub
						}
					}
			);
		}
	}

	private void setupWFCDirs() {
		File file = new File(Config.VIDEO_SAVE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(Config.AUDIO_SAVE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(Config.FILE_SAVE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(Config.PHOTO_SAVE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static long getMainThreadId() {
		return mMainThreadId;
	}

	public static Handler getMainHandler() {
		return mHandler;
	}
}
