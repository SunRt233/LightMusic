package com.sunrt233.lightmusic.model;

import com.sunrt233.music.*;
import java.util.*;

public interface MusicSearchModel
{
	public OnSearchingListener mOnSearchingListener;
	
	public void search(String keyword);
	
	public void setOnSearchingListener(OnSearchingListener onSearchingListener);
	
	public interface OnSearchingListener
	{
		public void onGettingResult(ArrayList<DataList> dataLists,int resultSize);
	}
}
