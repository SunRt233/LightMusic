package com.sunrt233.lightmusic.ui.fragment;

import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.adapter.*;
import com.sunrt233.lightmusic.presenter.*;
import com.sunrt233.lightmusic.ui.activity.*;
import com.sunrt233.lightmusic.view.*;
import com.sunrt233.lightmusic.widget.*;
import java.util.*;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.*;
import com.sunrt233.lightmusic.data.*;
import android.support.v4.database.*;

public class HomeFragment extends Fragment implements MusicSearchView
{
	private View view,v1,v2;
	private Toolbar toolbar,musicbar;
	private MainActivity mainActivity;
	private TabLayout mTabLayout;
	private MyViewPager mMyViewPager;
	private AppBarLayout appbar;
	private MyMusicSearchView mMyMusicSearchView;
	private RecyclerView mRecyclerView;
	private MusicRecyclerViewAdapter mMusicRecyclerViewAdapter;
	private ArrayList<View> viewList = new ArrayList<View>();
	private ArrayList<String> titleList = new ArrayList<String>();
	private Boolean isToolbarShowing = true;
	private MusicSearchPresenter mMusicSearchPresenter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		view = inflater.inflate(R.layout.fragment_home,container,false);
		mainActivity = (MainActivity) getActivity();
		
		initToolbar();
		initView(inflater);
		initContents();
		setHasOptionsMenu(true);
		
		mMusicSearchPresenter = new MusicSearchPresenterImpl(this);
		
		return view;
	}
	
	public void initToolbar()
	{
		appbar = (AppBarLayout) view.findViewById(R.id.fragment_home_appBarLayout);
		toolbar = (Toolbar) view.findViewById(R.id.fragment_home_toolbar);
		mainActivity.setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			mainActivity,
			mainActivity.getDrawerLayout(),
			toolbar,
			R.string.navigation_drawer_open,
			R.string.navigation_drawer_close
		);
        mainActivity.getDrawerLayout().setDrawerListener(toggle);
        toggle.syncState();
		
	}
	
	public void initView(LayoutInflater l)
	{
		mTabLayout = (TabLayout) view.findViewById(R.id.fragment_home_tabLayout);
		mMyViewPager = (MyViewPager) view.findViewById(R.id.fragment_home_myViewPager);
		//musicbar = (Toolbar) view.findViewById(R.id.fragment_home_musiclbar);
		v1 = l.inflate(R.layout.fragment_home_tab_mine,null);
		v2 = l.inflate(R.layout.fragment_home_tab_search,null);
		
		mRecyclerView = (RecyclerView) v2.findViewById(R.id.fragment_home_tab_search_resultsView);
		mMyMusicSearchView = (MyMusicSearchView) v2.findViewById(R.id.fragment_home_tab_search_musicSearchView);
		
	}
	
	public void initContents()
	{
		viewList.add(v1);
		viewList.add(v2);
		titleList.add("我的");
		titleList.add("搜索");
		
		mMyMusicSearchView.setOnSearchStartListener(new MyMusicSearchView.OnSearchStartListener(){

				@Override
				public void onStartSearch(String keyWord)
				{
					// TODO: Implement this method
					mainActivity.printToast(keyWord,1);
					mMusicSearchPresenter.searchMusic(keyWord);
				}
			});
		
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList,titleList);
		mMyViewPager.setAdapter(adapter);
		mMyViewPager.setCurrentItem(0,true);
		mMyViewPager.setOffscreenPageLimit(3);
		
		mTabLayout.setupWithViewPager(mMyViewPager,true);
		mTabLayout.setTabsFromPagerAdapter(adapter);
		
		mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
			{
				
				@Override
				public void onTabSelected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
					mMyViewPager.setCurrentItem(p1.getPosition(),true);
				}

				@Override
				public void onTabUnselected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTabReselected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
				}
				
			}
			
		);
		
		mMyViewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener(){

				@Override
				public void onPageScrolled(int p1, float p2, int p3)
				{
					// TODO: Implement this method
				}

				@Override
				public void onPageSelected(int p1)
				{
					// TODO: Implement this method
					if(p1==1)
					{
						mainActivity.getSupportActionBar().hide();
					}
					else
					{
						mainActivity.getSupportActionBar().show();
					}
				}

				@Override
				public void onPageScrollStateChanged(int p1)
				{
					// TODO: Implement this method
				}
			});
		
		
//		Button btn = (Button) v1.findViewById(R.id.fragment_home_tab_mine_btn);
//		btn.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View p1)
//				{
//					// TODO: Implement this method
//					mMusicSearchPresenter.searchMusic("megalovania");
//					Log.i("log","Strat search from HomeFragment");
//				}
//			});
		
		/*musicbar.setTitle("megalovania");
		musicbar.setSubtitle("TobyFox");
		musicbar.setLogo(R.drawable.ic_launcher);*/
		
		
	}

	@Override
	public void showResults(ArrayList<DataList> dataLists,int resultSize)
	{
		// TODO: Implement this method
		mainActivity.printToast("一共有" + resultSize + "个结果",1);
		
		if(mMusicRecyclerViewAdapter == null)
		{
			mMusicRecyclerViewAdapter = new MusicRecyclerViewAdapter(mainActivity.getApplicationContext(),dataLists);
			mRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
			mRecyclerView.setItemAnimator(new DefaultItemAnimator());
			mRecyclerView.setHasFixedSize(true);
			mRecyclerView.setAdapter(mMusicRecyclerViewAdapter);
			//mRecyclerView.
		}
		else
		{
			mMusicRecyclerViewAdapter.setNewList(dataLists);
			mMusicRecyclerViewAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void searchError()
	{
		// TODO: Implement this method
	}
	
}
