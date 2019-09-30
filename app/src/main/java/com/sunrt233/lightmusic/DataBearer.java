package com.sunrt233.lightmusic;

import com.sunrt233.lightmusic.service.*;
import java.util.*;
import com.sunrt233.music.DataList;

public class DataBearer
{
	public static MusicList ml = new MusicList();
	public static MusicService.MusicController mMusicController;
	public static ArrayList<DataList> mDl;
	
	public static void put(MusicService.MusicController musicController, ArrayList<DataList> dl)
	{
		mMusicController = musicController;
		mDl = dl;
	}
	
	public static MusicService.MusicController getMusicController()
	{
		return mMusicController;
	}
	
	//public static void 
	
	public static class MusicList
	{
		public DataList dataNow,dataPrevious,dataNext;
		public ArrayList<DataList> dl;
		
		public MusicList()
		{
			
		}
		
	}
	
}
