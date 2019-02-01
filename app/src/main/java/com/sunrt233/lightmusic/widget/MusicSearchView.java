package com.sunrt233.lightmusic.widget;

import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;

public class MusicSearchView extends RelativeLayout
{
	private LinearLayout rootlayout;
	private AppCompatImageView imgView;
	private AppCompatEditText editText;

	public MusicSearchView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		LayoutInflater.from(context).inflate(R.layout.layout_musicsearchview, this, true);

		rootlayout = (LinearLayout) findViewById(R.id.layout_musicsearchview_rootlayout);
		imgView = (AppCompatImageView) findViewById(R.id.layout_musicsearchview_imgBtn);
		editText = (AppCompatEditText) findViewById(R.id.layout_musicsearchview_editText);

		//监听软键盘是否显示或隐藏
        rootlayout.getViewTreeObserver().addOnGlobalLayoutListener(
			new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout()
				{
					Rect r = new Rect();
					rootlayout.getWindowVisibleDisplayFrame(r);
					int screenHeight = rootlayout.getRootView()
						.getHeight();
					int heightDifference = screenHeight - (r.bottom);
					if (heightDifference > 200)
					{
						//软键盘显示
// changeKeyboardHeight(heightDifference);
					}
					else
					{
						//软键盘隐藏
						editText.setFocusable(true);
						editText.clearFocus();
					}
				}

			});

	}


}
