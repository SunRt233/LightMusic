package com.sunrt233.lightmusic.model;

import com.sunrt233.lightmusic.model.MusicSearchModel.*;
import com.sunrt233.lightmusic.utils.*;
import android.os.*;
import java.util.*;
import com.sunrt233.lightmusic.data.*;
import android.util.*;


public class MusicSearchModelImpl implements MusicSearchModel
{
	ArrayList<DataList> dl = new ArrayList<DataList>();

	@Override
	public void search(String keyWord)
	{
		// TODO: Implement this method
		Log.i("log", "Start search from MusicSearchModelImpl");

		MusicSearchUtils.searchFromKuGou(keyWord, mhd);
		
		
		dl.add(new DataList("Megalovania_UnderFell_Remix", "Toby_Fox", "http://127.0.0.1:8080/assets/music.mp3", "http://127.0.0.1:8099/assets/music.mp3"));
		
		//mOnSearchingListener.onGettingResult(dl,1);
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
