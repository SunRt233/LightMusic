package com.sunrt233.lightmusic;

import com.sunrt233.lightmusic.service.*;
import com.sunrt233.music.*;
import java.util.*;

public class MusicMasterController 
{
	public static enum PLAY_MODE
	{
		MODE_ORDER,
		MODE_LOOP,
		MODE_RANDOM
	}
	private Enum playMode = PLAY_MODE.MODE_ORDER;
	private MusicService.MusicController musicController;
	public int mIndex = 0;
	private MListener mListener = new MListener();
	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	public ArrayList<DataList> playlist = new ArrayList<DataList>();

	public MusicMasterController(MusicService.MusicController c)
	{
		musicController = c;
		musicController.setListener(mListener);
	}

	public void play(int index)
	{
		mIndex = index;
		musicController.playWithUrl(playlist.get(mIndex).getMusicUrl(), mIndex);
		mListener.onPreparing(index);
	}

	public void play()
	{
		if(musicController.getPlayStatus() != MusicService.PLAYER_NEVERPLAYING) musicController.play();
	}
	
	public void playPrevious()
	{
		if(playMode ==PLAY_MODE.MODE_RANDOM)
		{
			Random r = new Random();
			play(r.nextInt(mIndex + 1));
		}
		else
		{
			if(mIndex - 1 >= 0)
			{
				play(mIndex - 1);
			}
			else
			{
				play(playlist.size() - 1);
			}
		}
	}
	
	public void playNext()
	{
		if(playMode == PLAY_MODE.MODE_RANDOM)
		{
			Random r = new Random();
			play(r.nextInt(mIndex + 1));
		}
		else
		{
			if(mIndex + 1 <= playlist.size() - 1)
			{
				play(mIndex + 1);
			}
			else
			{
				play(0);
			}
		}
	}

	public void pause()
	{
		musicController.pause();
	}

	public void setPosition(int position)
	{
		musicController.setPosition(position);
	}

	public int getPosition()
	{
		return musicController.getPosition();
	}
	
	public boolean isPlaying()
	{
		return musicController.isPlaying();
	}
	
	public void addListener(Listener l)
	{
		listeners.add(l);
	}
	
	public String getDataSource()
	{
		return musicController.getDataSource();
	}
	
	public int getPlayerState()
	{
		return musicController.getPlayStatus();
	}
	
	public int getMusicDuration()
	{
		return musicController.getMusicDuration();
	}

	public class MListener implements Listener
	{

		@Override
		public void onPlayerPlay()
		{
			// TODO: Implement this method
			for(Listener l:listeners)
			{
				l.onPlayerPlay();
			}
		}

		@Override
		public void onPlayerPause()
		{
			// TODO: Implement this method
			for(Listener l:listeners)
			{
				l.onPlayerPause();
			}
		}

		@Override
		public void onPreparing(int index)
		{
			for(int i = 0;i < listeners.size();i++)
			{
				listeners.get(i).onPreparing(index);
			}
		}

		@Override
		public void onPrepared(int index)
		{
			// TODO: Implement this method
			for (Listener l : listeners) {
				l.onPrepared(index);
			}
		}

		@Override
		public void onCompleted(int index)
		{
			// TODO: Implement this method
			if(playMode == PLAY_MODE.MODE_ORDER)
			{
				if(index + 1 <= playlist.size() - 1) play(index + 1);
			}
			else if(playMode == PLAY_MODE.MODE_LOOP)
			{
				play(index);
			}
			else if(playMode == PLAY_MODE.MODE_RANDOM)
			{
				Random r = new Random();
				play(r.nextInt(index + 1));
			}
			for (Listener l : listeners) {
				l.onCompleted(index);
			}
		}

	}

	public interface Listener
	{
		public void onPlayerPlay();
		public void onPlayerPause();
		public void onPreparing(int index);
		public void onPrepared(int index);
		public void onCompleted(int index);
		//public void onPositionChanged(int position);
	}

}
