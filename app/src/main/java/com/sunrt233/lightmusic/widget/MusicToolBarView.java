package com.sunrt233.lightmusic.widget;

import android.content.*;
import android.graphics.drawable.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.utils.*;
import com.sunrt233.music.*;

public class MusicToolBarView extends FrameLayout
{
	private Context mContext;
	public FrameLayout rootLayout;
	public AppCompatImageView artWorkImage,previousMusicBtn,musicPlayBtn,nextMusicBtn;
	public TextView musicName,musicAuthor;
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
		musicName.setSelected(true);
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
			
		previousMusicBtn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onPreviousMusicBtnClick();
				}
			});
			
		musicPlayBtn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onMusicPlayBtnClick();
				}
			});
			
		nextMusicBtn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(mFunctionListener != null) mFunctionListener.onNextMusicBtnClick();
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
	
	public void setArtWorkImg(Drawable drawable)
	{
		artWorkImage.setImageDrawable(drawable);
	}
	
	public void setArtWorkImgFromUrl(String url)
	{
		BitmapUtils.setBitmapFromNetWork(url, artWorkImage, 1/4);
	}
	
	public void setData(DataList data)
	{
		if(data != null)
		{
			setMusicName(data.getMusicName());
			setMusicAuthor(data.getMusicAuthor());
			setArtWorkImgFromUrl(data.getImageUrl());
		}
		else
		{
			setMusicName("Error");
			setMusicAuthor("The data can not be null");
		}
	}
	
	public interface FunctionListener
	{
		public void onRootLayoutClick();
		
		public void onPreviousMusicBtnClick();
		
		public void onMusicPlayBtnClick();
		
		public void onNextMusicBtnClick();
		
	}
	
}
