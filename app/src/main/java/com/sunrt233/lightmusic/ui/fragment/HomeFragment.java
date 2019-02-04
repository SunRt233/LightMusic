package com.sunrt233.lightmusic.ui.fragment;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.adapter.*;
import com.sunrt233.lightmusic.data.*;
import com.sunrt233.lightmusic.presenter.*;
import com.sunrt233.lightmusic.service.*;
import com.sunrt233.lightmusic.ui.activity.*;
import com.sunrt233.lightmusic.view.*;
import com.sunrt233.lightmusic.widget.*;
import java.util.*;

import android.support.v7.app.ActionBarDrawerToggle;

public class HomeFragment extends Fragment implements MusicSearchView,Runnable, ServiceConnection, MusicRecyclerViewAdapter.OnItemClickListener
{
	private View view,v1,v2;
	private Toolbar toolbar,musicbar;
	private MainActivity mainActivity;
	private TabLayout mTabLayout;
	private MyViewPager mMyViewPager;
	private AppBarLayout appbar;
	private MyMusicSearchView mMyMusicSearchView;
	private RecyclerView mRecyclerView;
	private MusicToolBarView mMusicToolBarView;
	private MusicRecyclerViewAdapter mMusicRecyclerViewAdapter;
	private ArrayList<View> viewList = new ArrayList<View>();
	private ArrayList<String> titleList = new ArrayList<String>();
	private Boolean isToolbarShowing = true;
	private MusicSearchPresenter mMusicSearchPresenter;
	private MusicPanelDialogFragment mMusicPanelDialogFragment = new MusicPanelDialogFragment();
	private MusicService.MusicController musicController;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		view = inflater.inflate(R.layout.fragment_home, container, false);
		mainActivity = (MainActivity) getActivity();

		initToolbar();
		initView(inflater);
		initContents();
		setHasOptionsMenu(true);

		mMusicSearchPresenter = new MusicSearchPresenterImpl(this);
		
		Intent intent = new Intent(mainActivity, MusicService.class);
		mainActivity.startService(intent);
		mainActivity.bindService(intent, this, mainActivity.BIND_AUTO_CREATE);

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
		v1 = l.inflate(R.layout.fragment_home_tab_mine, null);
		v2 = l.inflate(R.layout.fragment_home_tab_search, null);

		mRecyclerView = (RecyclerView) v2.findViewById(R.id.fragment_home_tab_search_resultsView);
		mMyMusicSearchView = (MyMusicSearchView) v2.findViewById(R.id.fragment_home_tab_search_musicSearchView);
		mMusicToolBarView = (MusicToolBarView) view.findViewById(R.id.fragment_home_musicbar);
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
					mainActivity.printToast(keyWord, 1);
					mMusicSearchPresenter.searchMusic(keyWord);
				}
			});

		MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList, titleList);
		mMyViewPager.setAdapter(adapter);
		mMyViewPager.setCurrentItem(0, true);
		mMyViewPager.setOffscreenPageLimit(3);

		mTabLayout.setupWithViewPager(mMyViewPager, true);
		mTabLayout.setTabsFromPagerAdapter(adapter);

		mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
			{

				@Override
				public void onTabSelected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
					mMyViewPager.setCurrentItem(p1.getPosition(), true);
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
					if (p1 == 1)
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


		mMusicToolBarView.setFunctionListener(new MusicToolBarView.FunctionListener(){

				@Override
				public void onRootLayoutClick()
				{
					// TODO: Implement this method

					mMusicPanelDialogFragment.show(mainActivity.getSupportFragmentManager(), "MusicPanelDialogFragment");
				}

				@Override
				public void onPreviousMusicBtnClick()
				{
					// TODO: Implement this method
				}

				@Override
				public void onMusicPlayBtnClick()
				{
					// TODO: Implement this method
				}

				@Override
				public void onNextMusicBtnClick()
				{
					// TODO: Implement this method
				}
			});
	}

	@Override
	public void onItemClick(View view, int position, DataList data)
	{
		// TODO: Implement this method
		musicController.playWithUrl(data.getMusicUrl());
	}

	@Override
	public void showResults(ArrayList<DataList> dataLists, int resultSize)
	{
		// TODO: Implement this method
		mainActivity.printToast("一共有" + resultSize + "个结果", 1);

		if (mMusicRecyclerViewAdapter == null)
		{
			mMusicRecyclerViewAdapter = new MusicRecyclerViewAdapter(mainActivity.getApplicationContext(), dataLists);
			mMusicRecyclerViewAdapter.setOnItemClickListener(this);
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

	@Override
	public void onServiceConnected(ComponentName p1, IBinder p2)
	{
		// TODO: Implement this method
		musicController = ((MusicService.MusicController)p2);
		//DataRepertory.putData(musicController, "musicController");
		mainActivity.printToast("服务连接",1);
	}

	@Override
	public void onServiceDisconnected(ComponentName p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
	}
	
}
