package com.sunrt233.lightmusic.ui.fragment;

import android.animation.*;
import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.sothree.slidinguppanel.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.adapter.*;
import com.sunrt233.music.*;
import com.sunrt233.lightmusic.presenter.*;
import com.sunrt233.lightmusic.service.*;
import com.sunrt233.lightmusic.ui.activity.*;
import com.sunrt233.lightmusic.view.*;
import com.sunrt233.lightmusic.widget.*;
import java.util.*;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import com.sunrt233.lightmusic.utils.*;
import android.widget.FrameLayout.*;
import com.bamboy.bamboycollected.utils.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.util.*;
import android.support.v7.graphics.*;

public class HomeFragment extends Fragment implements MusicSearchView,Runnable, ServiceConnection, MusicRecyclerViewAdapter.OnItemClickListener, View.OnClickListener, MusicMasterController.Listener
{
	//主视图
	private View view,v1,tab_search,v3,panel;
	private Toolbar toolbar,musicbar;
	private MainActivity mainActivity;
	private SlidingUpPanelLayout mSlidingUpPanelLayout;
	private TabLayout mTabLayout;
	private MyViewPager mMyViewPager;
	private AppBarLayout appbar;
	private MyMusicSearchView mMyMusicSearchView;
	private RecyclerView mRecyclerView;
	private MusicToolBarView mMusicToolBarView;
	private ProgressBar mProgressBar;
	private MusicRecyclerViewAdapter mMusicRecyclerViewAdapter;
	private ArrayList<View> viewList = new ArrayList<View>();
	private ArrayList<String> titleList = new ArrayList<String>();
	private Boolean onSearched = false;
	private MusicSearchPresenter mMusicSearchPresenter;
	private MusicPanelDialogFragment mMusicPanelDialogFragment;
	private MusicMasterController controller;
	
	private LinearLayout btnLocalMusic;

	//滑动面板
	private LinearLayout panelroot;
	private AppCompatImageView artWorkImg,musicPlayBtn,musicPreviousBtn,musicNextBtn;
	private TextView musicName,musicAuthor;
	private TextView positionText,durationText;
	private AppCompatSeekBar seekbar;

	//进度条
	private Th t;

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
		mSlidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.fragment_home_slidingUpPanelLayout);
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
		v1 = l.inflate(R.layout.fragment_home_tab_songs, null);
		tab_search = l.inflate(R.layout.fragment_home_tab_search, null);
		v3 = l.inflate(R.layout.fragment_home_tab_lists, null);

		panel = view.findViewById(R.id.fragment_home_panel);
		mRecyclerView = (RecyclerView) tab_search.findViewById(R.id.fragment_home_tab_search_resultsView);
		mMyMusicSearchView = (MyMusicSearchView) tab_search.findViewById(R.id.fragment_home_tab_search_musicSearchView);
		mMusicToolBarView = (MusicToolBarView) view.findViewById(R.id.fragment_home_musicbar);
		mProgressBar = (ProgressBar) tab_search.findViewById(R.id.fragment_home_tab_search_progressBar);

		btnLocalMusic = (LinearLayout) v1.findViewById(R.id.fragment_home_tab_songs_btnLocalMusic);
		mainActivity.setSnackBarRootlayout(mMusicToolBarView.getChildAt(0));

		panelroot = (LinearLayout) view.findViewById(R.id.fragment_musicpaneldialog_rootLayout);
		musicPlayBtn = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog__musicPlayBtn);
		musicPreviousBtn = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog_previousMusicBtn);
		musicNextBtn = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog_nextMusicBtn);
		musicName = (TextView) view.findViewById(R.id.fragment_musicpaneldialog__musicName);
		musicAuthor = (TextView) view.findViewById(R.id.fragment_musicpaneldialog_musicAuthor);
		artWorkImg = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog_artWorkImageView);
		seekbar = (AppCompatSeekBar) view.findViewById(R.id.fragment_musicpaneldialog_seekBar);
		positionText = (TextView) view.findViewById(R.id.fragment_musicpaneldialog_positionText);
		durationText = (TextView) view.findViewById(R.id.fragment_musicpaneldialog_durationText);
	}

	public void initContents()
	{
		musicPlayBtn.setOnClickListener(this);
		musicNextBtn.setOnClickListener(this);
		musicPreviousBtn.setOnClickListener(this);
		
		btnLocalMusic.setOnClickListener(this);

		musicName.setSelected(true);
		musicName.setSelected(true);

		viewList.add(v1);
		viewList.add(v3);
		viewList.add(tab_search);
		
		titleList.add("我的");
		titleList.add("播放列表");
		titleList.add("搜索");
		

		mMyMusicSearchView.setBaseActivity(mainActivity);

		mMyMusicSearchView.setOnSearchStartListener(new MyMusicSearchView.OnSearchStartListener(){

				@Override
				public void onStartSearch(String keyWord)
				{
					// TODO: Implement this method
					mainActivity.showSnackBar("正在搜索\"" + keyWord + "\"", Snackbar.LENGTH_SHORT);
					mMusicSearchPresenter.searchMusic(keyWord);
				}
			});

		MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList, titleList);
		mMyViewPager.setAdapter(adapter);
		mMyViewPager.setCurrentItem(1, true);
		mMyViewPager.setOffscreenPageLimit(4);

		mTabLayout.setupWithViewPager(mMyViewPager, true);
		mTabLayout.setTabsFromPagerAdapter(adapter);

		mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
			{

				@Override
				public void onTabSelected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
					mMyViewPager.setCurrentItem(p1.getPosition(), true);
					//mainActivity.showSnackBar( new Boolean( mMyMusicSearchView.isOpen()).toString(), 1000);
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
					if(p1 == 1)
					{
						//mainActivity.getSupportActionBar().hide();
					}
					else
					{
						//mainActivity.getSupportActionBar().show();
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
					mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
					//mMusicPanelDialogFragment.show(mainActivity.getSupportFragmentManager(), "MusicPanelDialogFragment");
				}

				@Override
				public void onPreviousMusicBtnClick()
				{
					// TODO: Implement this method
					controller.playPrevious();
				}

				@Override
				public void onMusicPlayBtnClick()
				{
					// TODO: Implement this method
					if(controller.isPlaying())
					{
						controller.pause();
					}
					else
					{
						controller.play();
					}

				}

				@Override
				public void onNextMusicBtnClick()
				{
					// TODO: Implement this method
					controller.playNext();
				}
			});

		mSlidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){

				@Override
				public void onPanelSlide(View p1, float p2)
				{
					// TODO: Implement this method
					Log.i("srt","" + p2);
					mMusicToolBarView.setAlpha(1f - p2);
					if(p2 == 1f)
					{
						mMusicToolBarView.setVisibility(View.GONE);
					}
					else
					{
						mMusicToolBarView.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onPanelStateChanged(View p1, SlidingUpPanelLayout.PanelState p2, SlidingUpPanelLayout.PanelState p3)
				{
					// TODO: Implement this method
					if(p2 == SlidingUpPanelLayout.PanelState.COLLAPSED && p3 == SlidingUpPanelLayout.PanelState.DRAGGING)
					{
						//mMusicToolBarView.setVisibility(View.GONE);
					}
					if(p2 == SlidingUpPanelLayout.PanelState.EXPANDED && p3 == SlidingUpPanelLayout.PanelState.DRAGGING)
					{
						//mMusicToolBarView.setVisibility(View.VISIBLE);
					}
					if(p3 == SlidingUpPanelLayout.PanelState.EXPANDED && mMusicToolBarView.getVisibility() == View.VISIBLE)
					{
						//mMusicToolBarView.setVisibility(View.GONE);
					}
					if(p3 == SlidingUpPanelLayout.PanelState.COLLAPSED && mMusicToolBarView.getVisibility() == View.GONE)
					{
						//mMusicToolBarView.setVisibility(View.VISIBLE);
					}
				}
			});

		panel.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
				}
			});
			
		seekbar.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					// TODO: Implement this method
					durationText.setText(DateUtils.format(controller.getMusicDuration()));
					positionText.setText(DateUtils.format(controller.getPosition()));
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
					controller.setPosition(p1.getProgress());
				}
			});

		/*new MaterialTapTargetSequence()
		 .addPrompt(new MaterialTapTargetPrompt.Builder(mainActivity)
		 .setTarget(((ViewGroup)mTabLayout.getChildAt(mTabLayout.getChildCount()-1)).getChildAt(1))
		 .setPrimaryText("第一步")
		 .setSecondaryText("This will show for 4 seconds")
		 .setPromptBackground(new RectanglePromptBackground())
		 .setPromptFocal(new RectanglePromptFocal()))
		 .addPrompt(new MaterialTapTargetPrompt.Builder(mainActivity)
		 .setTarget(mMyMusicSearchView)
		 .setPrimaryText("第二步")
		 .setSecondaryText("This will show till you press it")

		 .setPromptBackground(new RectanglePromptBackground())
		 .setPromptFocal(new RectanglePromptFocal()))
		 .show();*/

		/*for(int i = 0;i <= mTabLayout.getChildCount();i++)
		 {
		 //mainActivity.showSnackBar(""+i,Snackbar.LENGTH_LONG);
		 mainActivity.printToast(""+i,0);
		 }*/
	}

	@Override
	public void onItemClick(View view, int position, final DataList data)
	{
		// TODO: Implement this method
		mMusicToolBarView.setData(data);
		if(controller.getDataSource() == data.getMusicUrl())
		{
			//mainActivity.showSnackBar("你点的歌曲已经在播放了哦～(￣▽￣～)~", Snackbar.LENGTH_SHORT);
			if(controller.isPlaying())
			{
				controller.pause();
			}
			else
			{
				controller.play();
			}

		}
		else if(data.getMusicUrl() != "不要播放")
		{
			Message message = new Message();
			message.obj = data.getMusicUrl();
			message.what = 1;
			//musicController.handler.sendMessage(message);
			controller.play(position);

			DataRepertory.putData(data, "DataList");
			/*Message msg = new Message();
			 msg.what = 1;
			 msg.obj = data;
			 DataRepertory.putData(data,"DataList");*/

			//mMusicPanelDialogFragment.mhd.handleMessage(msg);			
		}
		//musicController.playWithUrl(data.getMusicUrl());

	}

	@Override
	public void showResults(ArrayList<DataList> dataLists, int resultSize)
	{
		// TODO: Implement this method
		mainActivity.showSnackBar("一共有" + resultSize + "个结果", Snackbar.LENGTH_SHORT);

		if(mMusicRecyclerViewAdapter == null)
		{
			mMusicRecyclerViewAdapter = new MusicRecyclerViewAdapter(mainActivity, mainActivity.getApplicationContext(), dataLists);
			mMusicRecyclerViewAdapter.setOnItemClickListener(this);
			mRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
			mRecyclerView.setItemAnimator(new DefaultItemAnimator());
			mRecyclerView.setHasFixedSize(true);
			mRecyclerView.setAdapter(mMusicRecyclerViewAdapter);
			controller.playlist = dataLists;
		}
		else
		{
			mMusicRecyclerViewAdapter.setNewList(dataLists);
			mMusicRecyclerViewAdapter.notifyDataSetChanged();
			controller.playlist = dataLists;
		}
		//mRecyclerView.getLayoutManager().findViewByPosition(0).getLayoutParams(). = DisplayUntiUtils.dip2px(mainActivity,68);

	}

	@Override
	public void searchError(String error)
	{
		// TODO: Implement this method
		Toast.makeText(mainActivity,error,1);
	}

	@Override
	public void showProgressBar()
	{
		// TODO: Implement this method
		mProgressBar.setVisibility(View.VISIBLE);
		if(mMusicRecyclerViewAdapter != null) mMusicRecyclerViewAdapter.setNewList(new ArrayList<DataList>());
	}

	@Override
	public void hideProgressBar()
	{
		// TODO: Implement this method
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onServiceConnected(ComponentName p1, IBinder p2)
	{
		// TODO: Implement this method
		controller = new MusicMasterController((MusicService.MusicController)p2);
		controller.addListener(this);
		//mMusicPanelDialogFragment = new MusicPanelDialogFragment((MusicService.MusicController)p2);
		DataRepertory.putData(controller, MusicMasterController.class.getName());
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

	//监听器
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId())
		{
			case R.id.fragment_musicpaneldialog__musicPlayBtn:
				if(controller.isPlaying())
				{
					controller.pause();
				}
				else
				{
					controller.play();
				}
				break;
			case R.id.fragment_musicpaneldialog_nextMusicBtn:
				controller.playNext();
				break;
			case R.id.fragment_musicpaneldialog_previousMusicBtn:
				controller.playPrevious();
				break;
			case R.id.fragment_home_tab_songs_btnLocalMusic:
				mainActivity.startActivity(new Intent(mainActivity.getApplicationContext(),LocalMusicActivity.class));
				break;

		}
	}



	@Override
	public void onPreparing(final int index)
	{
		// TODO: Implement this method
		//seekbar.setMax(controller.getMusicDuration());
		mMusicToolBarView.setData(controller.playlist.get(index));
		musicName.setText(controller.playlist.get(index).getMusicName());
		musicAuthor.setText(controller.playlist.get(index).getMusicAuthor());
		BitmapUtils.setBitmapFromNetWork(controller.playlist.get(index).getImageUrl(), artWorkImg, 1);
		
	}

	@Override
	public void onPlayerPlay()
	{
		// TODO: Implement this method
		if(t == null)
		{
			t = new Th();
			t.start();
		}
		musicPlayBtn.setImageResource(R.drawable.ic_pause_circle_filled);
		mMusicToolBarView.musicPlayBtn.setImageResource(R.drawable.ic_pause);
		durationText.setText(DateUtils.format(controller.getMusicDuration()));
	}

	@Override
	public void onPlayerPause()
	{
		// TODO: Implement this method
		musicPlayBtn.setImageResource(R.drawable.ic_play_circle_filled);
		mMusicToolBarView.musicPlayBtn.setImageResource(R.drawable.ic_play_arrow);
	}

	@Override
	public void onPrepared(int index)
	{
		// TODO: Implement this method
	}

	@Override
	public void onCompleted(int index)
	{
		// TODO: Implement this method
	}

	class Th extends Thread
	{

		@Override
		public void run()
		{
			// TODO: Implement this method
			super.run();
			while(true)
			{
				if(controller != null)
				{
					Message msg = new Message();
					msg.what = 2;
					msg.obj = controller.getPosition();
					hd.sendMessage(msg);
				}
				try
				{
					sleep(200);
				}
				catch(InterruptedException e)
				{}
			}
		}

	}

	Handler hd = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			if(msg.what == 2)
			{
				//panelroot.setBackgroundDrawable((Bitmap) msg.obj).);
				seekbar.setMax(controller.getMusicDuration());
				seekbar.setProgress((int) msg.obj);
				
			}
		}

	};

}
