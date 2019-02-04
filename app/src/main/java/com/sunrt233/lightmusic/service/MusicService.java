package com.sunrt233.lightmusic.service;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import com.sunrt233.lightmusic.data.*;
import android.util.*;

public class MusicService extends Service {

    private MediaPlayer mPlayer; 
	final public static int PLAYER_NEVERPLAYING = 0;
	final public static int PLAYER_PLAYING = 1;
	final public static int PLAYER_PAUSE = 2;
	private int playerStatus = 0;
	private String dataSource = "";
	private DataList data = null;

    public MusicService() 
	{
		
    }

    /*
	 * 绑定服务的实现流程：
	 * 1.服务 onCreate， onBind， onDestroy 方法
	 * 2.onBind 方法需要返回一个 IBinder 对象
	 * 3.如果 Activity 绑定，Activity 就可以取到 IBinder 对象，可以直接调用对象的方法
	 */

    // 相同应用内部不同组件绑定，可以使用内部类以及Binder对象来返回。
    public class MusicController extends Binder {
        public void play() {
            mPlayer.start();//开启音乐
			playerStatus = PLAYER_PLAYING;
        }
		
		public void putData(DataList d)
		{
			data = d;
		}
		
		public DataList getData()
		{
			if(data != null)
			{
				return data;
			}
			else
			{
				return null;
			}
		}
		
		public void playWithUrl(String url)
		{
			try
			{
				mPlayer.reset();
				mPlayer.setDataSource(url);
				dataSource = url;
				mPlayer.prepare();
				mPlayer.start();
				playerStatus = PLAYER_PLAYING;
			}
			catch (Throwable e)
			{
				
			}
			
		}
		
		public String getDataSource()
		{
			return dataSource;
		}

		public boolean isPlaying()
		{
			return mPlayer.isPlaying();
		}
		
		public int getPlayStatus()
		{
			return playerStatus;
		}
		
        public void pause() {
            mPlayer.pause();//暂停音乐
			playerStatus = PLAYER_PAUSE;
        }

        public long getMusicDuration() {
            return mPlayer.getDuration();//获取文件的总长度
        }

        public long getPosition() {
            return mPlayer.getCurrentPosition();//获取当前播放进度
        }

        public void setPosition (int position) {
            mPlayer.seekTo(position);//重新设定播放进度
        }
    }

    /**
     * 当绑定服务的时候，自动回调这个方法
     * 返回的对象可以直接操作Service内部的内容
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
		Log.i("log","Service create");
		
    }

    /**
     * 任意一次unbindService()方法，都会触发这个方法
     * 用于释放一些绑定时使用的资源
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
        mPlayer = null;
        super.onDestroy();
    }
}
