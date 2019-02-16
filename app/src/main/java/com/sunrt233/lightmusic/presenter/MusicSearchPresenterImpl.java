package com.sunrt233.lightmusic.presenter;
import com.sunrt233.lightmusic.view.*;
import com.sunrt233.lightmusic.model.*;
import android.util.*;
import java.util.*;
import com.sunrt233.lightmusic.data.*;

public class MusicSearchPresenterImpl implements MusicSearchPresenter
{
	private MusicSearchView mMusicSearchView;
	private MusicSearchModel mMusicSearchModel;
	
	public MusicSearchPresenterImpl(MusicSearchView musicSearchView)
	{
		mMusicSearchView = musicSearchView;
		mMusicSearchModel = new MusicSearchModelImpl();
		
	}
	
	@Override
	public void searchMusic(String keyWord)
	{
		// TODO: Implement this method
		mMusicSearchModel.setOnSearchingListener(new MusicSearchModel.OnSearchingListener(){

				@Override
				public void onGettingResult(ArrayList<DataList> dataLists,int resultSize)
				{
					// TODO: Implement this method
					mMusicSearchView.hideProgressBar();
					mMusicSearchView.showResults(dataLists,resultSize);
				}
				
			});
			
		mMusicSearchModel.search(keyWord);
		mMusicSearchView.showProgressBar();
		Log.i("log","Start search from MusicSearchPresenterImpl");
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
	}
	
}
