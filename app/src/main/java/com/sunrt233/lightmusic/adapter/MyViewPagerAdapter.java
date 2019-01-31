package com.sunrt233.lightmusic.adapter;

import android.support.v4.view.*;
import android.view.*;
import java.util.*;

/**
 * Created by lwh on 2016/12/3.
 */

public class MyViewPagerAdapter extends PagerAdapter {
    private ArrayList<View>mViewList;
    private ArrayList<String>mTabList;
    public MyViewPagerAdapter(ArrayList<View>viewList,ArrayList<String>tabList)
    {
        this.mTabList = tabList;
        this.mViewList = viewList;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.remove(position));
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position);
    }

	@Override
	public int getItemPosition(Object object)
	{
		// TODO: Implement this method
		return POSITION_NONE;
	}
	
}
