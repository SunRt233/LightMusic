package com.sunrt233.lightmusic.widget;

import android.content.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;

public class MusicToolBarView extends FrameLayout
{
	private Context mContext;
	private FrameLayout rootLayout;
	private AppCompatImageView artWorkImage,previousMusicBtn,musicPlayBtn,nextMusicBtn;
	private TextView musicName,musicAuthor;
	private FunctionListener mFunctionListener;
	
	public MusicToolBarView(Context context, AttributeSet attrs)
	{
		super(context,attrs);
		mContext = context;
		
		LayoutInflater.from(context).inflate(R.layout.layout_musictoolbarview,this,true);
		initChildView();
		initListeners();
		
	}
	
	private void initChildView()
	{
		rootLayout = (FrameLayout) findViewById(R.id.layout_musictoolbarview_rootLayout);
		artWorkImage = (AppCompatImageView) findViewById(R.id.layout_musictoolbarview_artWorkImage);
		musicName = (TextView) findViewById(R.id.layout_musictoolbarview__musicName);
		musicAuthor = (TextView) findViewById(R.id.layout_musictoolbarview__musicAuthor);
		previousMusicBtn = (AppCompatImageView) findViewById(R.id.layout_musictoolbarview_previousMusicBtn);
		musicPlayBtn = (AppCompatImageView) findViewById(R.id.layout_musictoolbarview__musicPlayBtn);
		nextMusicBtn = (AppCompatImageView) findViewById(R.id.layout_musictoolbarview_nextMusicBtn);
	}
	
	public void setFunctionListener(FunctionListener functionListener)
	{
		mFunctionListener = functionListener;
	}
	
	protected void initListeners()
	{
		rootLayout.setOnClickListener(new FrameLayout.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onRootLayoutClick();
				}
			});
			
		previousMusicBtn.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onPreviousMusicBtnClick();
					return false;
				}
			});
			
		musicPlayBtn.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onMusicPlayBtnClick();
					return false;
				}
			});
			
		nextMusicBtn.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onNextMusicBtnClick();
					return false;
				}
			});
	}
	
	public void setMusicName(String name)
	{
		musicName.setText(name);
	}
	
	public void setMusicAuthor(String author)
	{
		musicAuthor.setText(author);
	}
	
	public interface FunctionListener
	{
		public void onRootLayoutClick();
		
		public void onPreviousMusicBtnClick();
		
		public void onMusicPlayBtnClick();
		
		public void onNextMusicBtnClick();
		
	}
	
}
