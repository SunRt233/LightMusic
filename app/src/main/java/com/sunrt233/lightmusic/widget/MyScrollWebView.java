package com.sunrt233.lightmusic.widget;

import android.content.*;
import android.util.*;
import android.view.*;
import android.webkit.*;

public class MyScrollWebView extends WebView
{
    private static final String TAG = MyScrollWebView.class.getSimpleName();
    public MyScrollWebViewOnScrollListener listener;
	public MyScrollWebView MyScrollWebView;

	private float startX;
	private float startY;
	private MyViewScrollListener mListener;
	private static final int HIDE_DISTANCE = 30;
	private boolean isToolbarVisible = true;
	public String homeUrl = null;

    public MyScrollWebView(Context context)
	{
        super(context);
    }

    public MyScrollWebView(Context context, AttributeSet attrs)
	{
        super(context, attrs);
    }

    public MyScrollWebView(Context context, AttributeSet attrs, int defStyleAttr)
	{
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
        super.onScrollChanged(l, t, oldl, oldt);

        if (listener != null)
		{
            if (t - oldt <= 2)
			{
                listener.onScrollDown(l, t, oldl, oldt);
            }
            if (oldt - t >= 2)
			{
                listener.onScrollUp(l, t, oldl, oldt);
            }
			listener.onScrollChange(l, t, oldl, oldt);
        }
    }



    public void setListener(MyScrollWebViewOnScrollListener listener)
	{
        this.listener = listener;
    }

	public void goToHomePage(MyScrollWebView v)
	{
		v.loadUrl(homeUrl);
	}

	public void setHomePage(String homeUrl)
	{
		this.homeUrl = homeUrl;
	}

    public interface MyScrollWebViewOnScrollListener
	{
        void onScrollUp(int l, int t, int oldl, int oldt);//上滑
        void onScrollDown(int l, int t, int oldl, int oldt);//下滑
		void onScrollChange(int l, int t, int oldl, int oldt);
		
    }


	public void setListener(MyViewScrollListener mListener)
	{
        this.mListener = mListener;
    }



	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			startX = ev.getX();
			startY = ev.getY();
		}
		else if (ev.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (mListener == null)
			{
				return super.onTouchEvent(ev);
			}
			onScrolled(startX - ev.getX(), startY - ev.getY());
		}
		return super.onTouchEvent(ev);
	}

	private void onScrolled(float dx, float dy)
	{
		if (dy > HIDE_DISTANCE && isToolbarVisible)
		{
			mListener.onHide();
			isToolbarVisible = false;
		}
		else if (dy < 0 && !isToolbarVisible)
		{
			mListener.onShow();
			isToolbarVisible = true;
		}
	}

	public interface MyViewScrollListener
	{
		public void onHide();
		public void onShow();
	}

}


