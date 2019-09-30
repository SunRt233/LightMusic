package com.sunrt233.lightmusic.service;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.util.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.music.*;

public class MusicService extends Service
{

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
    public class MusicController extends Binder implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener
	{
		private int mIndex = 0;
		private MusicMasterController.Listener listener;
		private boolean isPrepared = true;
		//private Timer timer = new Timer();

		

        public void play()
		{
            mPlayer.start();//开启音乐
			listener.onPlayerPlay();
			isPrepared = true;
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

		public void playWithUrl(String url, int index)
		{
			try
			{
				//if(getPlayStatus() == PLAYER_NEVERPLAYING) t.start();
				isPrepared = false;
				mPlayer.reset();
				mPlayer.setDataSource(url);
				dataSource = url;
				mPlayer.setOnPreparedListener(this);
				mPlayer.setOnCompletionListener(this);
				mPlayer.setOnBufferingUpdateListener(this);
				mPlayer.prepareAsync();
				//Thread.sleep(30);
				//mPlayer.start();
				mIndex = index;

			}
			catch(Throwable e)
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

        public void pause()
		{
            mPlayer.pause();//暂停音乐
			listener.onPlayerPause();
			//timer.cancel();
			playerStatus = PLAYER_PAUSE;
        }

        public int getMusicDuration()
		{
			if(isPrepared)
			{
				return mPlayer.getDuration();//获取文件的总长度
			}
			else
			{
				return 0;
			}
        }

        public int getPosition()
		{
			//Log.i("log",""+getCurrentPosition);
			if(isPrepared)
            {
				return mPlayer.getCurrentPosition();//获取当前播放进度
			}
			else
			{
				return 0;
			}
        }

        public void setPosition(int position)
		{
			//isChanging = true;
			
            mPlayer.seekTo(position);//重新设定播放进度
			//isChanging = false;
        }

		public Handler handler = new Handler()
		{

			@Override
			public void handleMessage(Message msg)
			{
				// TODO: Implement this method
				super.handleMessage(msg);
				Object[] o = (Object[]) msg.obj;
				if(msg.what == 1)
				{
					playWithUrl((String) o[0], (int) o[1]);
				}
			}

		};

		public void setListener(MusicMasterController.Listener l)
		{
			listener = l;
		}

		@Override
		public void onPrepared(MediaPlayer p1)
		{
			// TODO: Implement this method
			mPlayer.start();
			listener.onPlayerPlay();
			isPrepared = true;
			playerStatus = PLAYER_PLAYING;
			if(listener != null) listener.onPrepared(mIndex);
		}

		@Override
		public void onBufferingUpdate(MediaPlayer p1, int p2)
		{
			// TODO: Implement this method
			//isPrepared = true;
		}

		@Override
		public void onCompletion(MediaPlayer p1)
		{
			// TODO: Implement this method
			listener.onCompleted(mIndex);
		}

    }

    /**
     * 当绑定服务的时候，自动回调这个方法
     * 返回的对象可以直接操作Service内部的内容
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent)
	{
        return new MusicController();
    }

    @Override
    public void onCreate()
	{
        super.onCreate();
        mPlayer = new MediaPlayer();
		Log.i("log", "Service create");

    }

    /**
     * 任意一次unbindService()方法，都会触发这个方法
     * 用于释放一些绑定时使用的资源
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent)
	{
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy()
	{
        if(mPlayer.isPlaying())
		{
            mPlayer.stop();
        }
        mPlayer.release();
        mPlayer = null;
        super.onDestroy();
    }
}
