package com.sunrt233.lightmusic.base;

import android.support.v4.app.*;

public class BaseFragment extends Fragment
{
	private ViewCombinationLifeCycleListener mViewCombinationLifeCycleListener = null;
	private boolean hasDestroiedView = false;
	private boolean isFirstTimeRecreateView = false;

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		if(hasDestroiedView && mViewCombinationLifeCycleListener != null) mViewCombinationLifeCycleListener.recreateView();
		hasDestroiedView = false;
	}
	
	@Override
	public void onPause()
	{
	// TODO: Implement this method
		super.onPause();
		if(mViewCombinationLifeCycleListener != null) mViewCombinationLifeCycleListener.onPause();
	}

	@Override
	public void onStop()
	{
		// TODO: Implement this method
		super.onStop();
		if(mViewCombinationLifeCycleListener != null) mViewCombinationLifeCycleListener.onStop();
	}

	@Override
	public void onDestroyView()
	{
		// TODO: Implement this method
		super.onDestroyView();
		hasDestroiedView = true;
		if(mViewCombinationLifeCycleListener != null) mViewCombinationLifeCycleListener.onDestroyView();
	}
	
	public void setViewCombinationLifeCycleListener(ViewCombinationLifeCycleListener listener)
	{
		mViewCombinationLifeCycleListener = listener;
	}

	public interface ViewCombinationLifeCycleListener
	{
		public void recreateView();
		public void onPause();
		public void onStop();
		public void onDestroyView();
	}
}
