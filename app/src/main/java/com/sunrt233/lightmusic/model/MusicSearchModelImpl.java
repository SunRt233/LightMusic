package com.sunrt233.lightmusic.model;


import android.os.*;
import android.util.*;
import com.sunrt233.music.*;
import java.util.*;
import org.jsoup.nodes.*;
import android.widget.*;

public class MusicSearchModelImpl implements MusicSearchModel
{
	ArrayList<DataList> dl = new ArrayList<DataList>();

	@Override
	public void search(String keyWord)
	{
		// TODO: Implement this method
		Log.i("log", "Start search from MusicSearchModelImpl");

		// Remove by SunRt233 2019.6.27 11:21
		//MusicSearchUtils.searchFromKuGou(keyWord, mhd);
		
		
		//dl.add(new DataList("Megalovania_UnderFell_Remix", "Toby_Fox", "http://127.0.0.1:8080/assets/music.mp3", "http://127.0.0.1:8099/assets/music.mp3"));
		
		//mOnSearchingListener.onGettingResult(dl,1);
		
		
		MusicSearch.searchFromNetease(new ResultCallBack(){

				@Override
				public void result(Document connection, ArrayList<com.sunrt233.music.DataList> songInfo, int pagesize, int page)
				{
					// TODO: Implement this method
					
					if(mOnSearchingListener != null) mOnSearchingListener.onGettingResult(songInfo,songInfo.size());
					
				}
			}, keyWord, 20, 0);
		
		
		Log.i("log", "Send search");

	}

	@Override
	public void setOnSearchingListener(MusicSearchModel.OnSearchingListener onSearchingListener)
	{
		// TODO: Implement this method
		mOnSearchingListener = onSearchingListener;

	}

	Handler mhd = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			if (msg.what == 0)
			{
				ArrayList<DataList> dataLists = (ArrayList<DataList>) msg.obj;
				Log.i("log", "Get search result");

				try
				{
					if (dataLists.get(dataLists.size() - 1).getMusicName() == "获取失败")
					{
						dataLists.remove(dataLists.size() - 1);
					}
					//dataLists.add(dl.get(0));
					mOnSearchingListener.onGettingResult(dataLists,dataLists.size());
				}
				catch (Throwable e)
				{

				}

			}

		}

	};

}
