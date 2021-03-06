package com.sunrt233.lightmusic.widget;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;

import android.support.v7.widget.PopupMenu;
import com.sunrt233.lightmusic.base.*;
import android.text.*;

public class MyMusicSearchView extends RelativeLayout
{
	private BaseActivity mBaseActivity;
	private Context mContext;
	private LinearLayout rootlayout;
	private AppCompatImageView imgView,cleanBtn;
	private AppCompatEditText editText;
	private OnSearchStartListener onSearchStartListener;
	private PopupMenu menu;
	private Boolean isFisrtTimeToEnter = true;


	public MyMusicSearchView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		

		LayoutInflater.from(context).inflate(R.layout.layout_musicsearchview, this, true);

		rootlayout = (LinearLayout) findViewById(R.id.layout_musicsearchview_rootlayout);
		imgView = (AppCompatImageView) findViewById(R.id.layout_musicsearchview_imgBtn);
		cleanBtn = (AppCompatImageView) findViewById(R.id.layout_musicsearchview_cleanBtn);
		editText = (AppCompatEditText) findViewById(R.id.layout_musicsearchview_editText);

		//监听软键盘是否显示或隐藏
        rootlayout.getViewTreeObserver().addOnGlobalLayoutListener(
			new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout()
				{
					Rect r = new Rect();
					rootlayout.getWindowVisibleDisplayFrame(r);
					int screenHeight = rootlayout.getRootView().getHeight();
					int heightDifference = screenHeight - (r.bottom);
					
					if (heightDifference > 200)
					{
						//软键盘显示
// changeKeyboardHeight(heightDifference);
						editText.setFocusable(true);
						editText.setFocusableInTouchMode(true);
						editText.requestFocus();
						editText.setCursorVisible(true);
						cleanBtn.setVisibility(View.VISIBLE);
						Toast.makeText(mBaseActivity.getApplicationContext(), "显示", 1).show();
					}
					else
					{
						//软键盘隐藏
						editText.setFocusable(true);
						editText.setFocusableInTouchMode(true);
						editText.clearFocus();
						editText.setCursorVisible(false);
						cleanBtn.setVisibility(View.GONE);
						Toast.makeText(mBaseActivity.getApplicationContext(),"关闭",1).show();
					}
				}

			});

		menu = new PopupMenu(context, imgView);

		menu.getMenuInflater().inflate(R.menu.music_searchengine, menu.getMenu());

		initListeners();
		
	}

	private void initListeners()
	{

		editText.setOnKeyListener(new OnKeyListener(){

				@Override
				public boolean onKey(View p1, int p2, KeyEvent p3)
				{
					// TODO: Implement this method
					if (p2 == KeyEvent.KEYCODE_ENTER && p3.getAction() == KeyEvent.ACTION_DOWN)
					{
						if (onSearchStartListener != null)
						{
							if (!isEmpty(editText.getText().toString()))
							{
								onSearchStartListener.onStartSearch(editText.getText().toString());
							}
							else
							{
								editText.setText("关键字不能为空！");
							}
						}
						editText.setFocusable(true);
						editText.clearFocus();
						if(mBaseActivity != null) mBaseActivity.hideInput();
						
					}

					return false;
				}
			});

		imgView.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					menu.show();
					
				}
			});
			
		cleanBtn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					editText.setText("");
				}
			});
		
	}

	public void setOnSearchStartListener(MyMusicSearchView.OnSearchStartListener mOnSearchStartListener)
	{
		onSearchStartListener = mOnSearchStartListener;
	}

	public interface OnSearchStartListener
	{
		public void onStartSearch(String keyWord);
	}

	public static boolean isEmpty(String string)
	{
		if (string == null || string.trim().length() == 0)
		{
			return true;
		}
		else
		{
			return false;
		}

	}          
	
	public void setBaseActivity(BaseActivity ba)
	{
		mBaseActivity = ba;
	}
	
	public void hideInput()
	{
		try{
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity)mContext).getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			
        }
		}
		catch(Throwable e)
		{
			Toast.makeText(mContext,e.toString(),1).show();
		}
	}
	
	
	public boolean isOpen()
	{
		
			InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			return imm.isActive();
	}
}
