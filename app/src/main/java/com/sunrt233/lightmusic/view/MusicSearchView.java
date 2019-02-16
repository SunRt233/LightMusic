package com.sunrt233.lightmusic.view;

import com.sunrt233.lightmusic.data.*;
import java.util.*;

public interface MusicSearchView
{
	//显示结果
	public void showResults(ArrayList<DataList> dataLists,int resultSize);
	//搜索错误
	public void searchError();
	
	public void showProgressBar();
	
	public void hideProgressBar();
	
}
