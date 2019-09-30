package com.sunrt233.lightmusic.base;

import android.content.*;
import android.view.*;

public class ViewCombination 
{
	private Context mContext;
	private View mContentView;
	private LayoutInflater inflater;
	
	public ViewCombination(Context context)
	{
		mContext = context;
		inflater = LayoutInflater.from(context);
		mContentView = new View(context);
	}
	
	//生命周期
	public void create()
	{
		
	}
	
	protected void onCreated()
	{
		
	}
	
	protected void onStart()
	{
		
	}
	
	protected void onResume()
	{
		
	}
	
	protected void onPause()
	{
		
	}
	
	protected void onStop()
	{
		
	}
	
	protected void onDestroy()
	{
		
	}
	
	protected void addToViewGroup(ViewGroup ViewGroup)
	{
		ViewGroup.addView(mContentView);
	}
	
}
