package com.sunrt233.lightmusic.ui.fragment;

import android.app.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.data.*;
import java.util.*;
import com.sunrt233.lightmusic.ui.activity.*;
import com.sunrt233.lightmusic.service.*;

public class MusicPanelDialogFragment extends BottomSheetDialogFragment
{
	private View view;
	private BottomSheetBehavior mBehavior;
	private NestedScrollView mNestedScrollView;
	private TextView musicName,musicAuthor;
	private AppCompatImageView previousMusicBtn,musicPlayBtn,nextMusicBtn;
	private MusicService.MusicController musicController;
	
	public MusicPanelDialogFragment(MusicService.MusicController mc)
	{
		musicController = mc;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
		view = View.inflate(getContext(), R.layout.fragment_musicpaneldialog, null);
		dialog.setContentView(view);
		mBehavior = BottomSheetBehavior.from((View) view.getParent());

		initView();
		initEvents();
		
		Timer timer = new Timer();
		TimerTask ts = new TimerTask(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				view.post(new Runnable(){

						@Override
						public void run()
						{
							// TODO: Implement this method
							try
							{
								DataList dl = (DataList) DataRepertory.getData("DataList");
								musicName.setText(dl.getMusicName());
								musicAuthor.setText(dl.getMusicAuthor());
							}
							catch (Throwable e)
							{
								//((MainActivity) getActivity()).printToast(e.toString(),1);
							}
						}
					});

			}
		};
		timer.schedule(ts, 0, 300);

		return dialog;
	}

	public void initView()
	{
		musicName = (TextView) view.findViewById(R.id.fragment_musicpaneldialog__musicName);
		musicAuthor = (TextView) view.findViewById(R.id.fragment_musicpaneldialog_musicAuthor);
		previousMusicBtn = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog_previousMusicBtn);
		musicPlayBtn = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog__musicPlayBtn);
		nextMusicBtn = (AppCompatImageView) view.findViewById(R.id.fragment_musicpaneldialog_nextMusicBtn);

	}
	
	public void initEvents()
	{
		musicPlayBtn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(musicController.isPlaying()) 
					{
						musicController.pause();
						musicPlayBtn.setImageResource(R.drawable.ic_play_circle_filled);
					}
					else
					{
						musicController.play();
						musicPlayBtn.setImageResource(R.drawable.ic_pause_circle_filled);
					}
				}
		});
		
	}
	

	@Override
	public void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		final BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null)
		{
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT; //可以写入自己想要的高度
			mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
			//mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
		mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){

				@Override
				public void onStateChanged(View p1, int p2)
				{
					//onStateChanged(p1,p2);
					//TODO: Implement this method
					if (p2 == BottomSheetBehavior.STATE_COLLAPSED)// (p2 != BottomSheetBehavior.STATE_EXPANDED && p2 != BottomSheetBehavior.STATE_DRAGGING)
					{
						//mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
						dialog.dismiss();
					}
				}

				@Override
				public void onSlide(View p1, float p2)
				{
					 //TODO: Implement this method
				}
			});
        /*final View view = getView();
		 view.post(new Runnable() {
		 @Override
		 public void run() {
		 View parent = (View) view.getParent();
		 CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
		 CoordinatorLayout.Behavior behavior = params.getBehavior();
		 BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
		 bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
		 parent.setBackgroundColor(Color.WHITE);
		 }
		 });*/

	}

}
