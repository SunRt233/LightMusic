package com.sunrt233.lightmusic.adapter;

import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.adapter.*;
import com.sunrt233.lightmusic.data.*;
import com.sunrt233.lightmusic.utils.*;
import java.util.*;

import android.support.v7.widget.PopupMenu;
import com.sunrt233.lightmusic.ui.activity.*;
import android.util.*;
import android.widget.AbsoluteLayout.*;

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.MyViewHolder>
{
	private ArrayList<DataList> dl;
	private View.OnClickListener mOnClickListener = null;
	private OnItemClickListener mOnItemClickListener;
	private OnItemPopupMenuItemClickListener mOnItemPopupMenuItemClickListener;
	private List<Bitmap> artWorkImages = null;
	private Context mContext;
	private Boolean isFirstTimeToRun = true;

	public MusicRecyclerViewAdapter(ArrayList<DataList> d, View.OnClickListener onClickListener)
	{
		dl = d;
		mOnClickListener = onClickListener;
	}

	public MusicRecyclerViewAdapter(Context context, ArrayList<DataList> d)
	{
		dl = d;
		mContext = context;
	}

	public void setNewList(ArrayList<DataList> d)
	{
		dl = d;
	}

	@Override
	public MusicRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method

		View v = LayoutInflater.from(p1.getContext()).inflate(R.layout.list_music_box, p1, false);  
        return new MyViewHolder(v);  
	}

	@Override
	public void onBindViewHolder(final MusicRecyclerViewAdapter.MyViewHolder holder, final int p2)
	{
		// TODO: Implement this method
		
		/*if(p2 == 0 && isFirstTimeToRun)
		{
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.rootLayout.getLayoutParams());
			lp.setMargins(0,dip2px(mContext,68),0,0);
			holder.rootLayout.setLayoutParams(lp);
			isFirstTimeToRun = false;
		}*/

		holder.musicName.setText(dl.get(p2).getMusicName());
		holder.musicAuthor.setText(dl.get(p2).getMusicAuthor());
		//BitmapUtils.setBitmapFromNetWork(dl.get(p2).getImageUrl(), holder.artWorkImage, null);
		holder.artWorkImage.setVisibility(View.GONE);
		//Toast.makeText(mContext,dl.get(p2).getImageUrl(),Toast.LENGTH_SHORT).show();
		holder.rootLayout.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					int position = holder.getLayoutPosition();
					if(mOnItemClickListener != null)
					{
						mOnItemClickListener.onItemClick(holder.itemView, position, dl.get(p2));
					}
					// TODO: Implement this method
				}
			});

		holder.popupMenu.setOnTouchListener(new AppCompatImageView.OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{
					// TODO: Implement this method
					PopupMenu pmenu = new PopupMenu(mContext,p1);
					pmenu.getMenuInflater().inflate(R.menu.music_searchengine,pmenu.getMenu());
					try
					{
						pmenu.show();
					}
					catch(Throwable e)
					{
						Log.i("log",e.getMessage());
					}
					return false;
				}
			});

//		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
//
//				@Override
//				public boolean onMenuItemClick(MenuItem p1)
//				{
//					// TODO: Implement this method
//					mOnItemPopupMenuItemClickListener.OnItemPopupMenuItemClick(p1,dl.get(p2));
//					return true;
//				}
//			});

	}

	public void setOnItemClickListener(OnItemClickListener o)
	{
		mOnItemClickListener = o;
	}

	public void setOnItemPopupMenuItemClick(OnItemPopupMenuItemClickListener onItemPopupMenuItemClickListener)
	{
		mOnItemPopupMenuItemClickListener = onItemPopupMenuItemClickListener;
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return dl.size();
	}


	public static class MyViewHolder extends RecyclerView.ViewHolder
	{
		public TextView musicName,musicAuthor;
		public RelativeLayout rootLayout;
		public ImageView artWorkImage;
		public AppCompatImageView popupMenu;

		public MyViewHolder(View v)
		{
			super(v);
			musicName = (TextView) v.findViewById(R.id.list_music_boxMusicName);
			musicAuthor = (TextView) v.findViewById(R.id.list_music_boxMusicAuthor);
			rootLayout = (RelativeLayout) v.findViewById(R.id.list_music_boxRelativeLayout);
			artWorkImage  =  (ImageView) v.findViewById(R.id.list_music_boxImageView);
			popupMenu = (AppCompatImageView) v.findViewById(R.id.list_music_boxMenu);
		}
	}

	public interface OnItemClickListener
	{
		public void onItemClick(View view, int position, DataList data);
	}

	public interface OnItemPopupMenuItemClickListener
	{
		public void OnItemPopupMenuItemClick(MenuItem item, DataList data);
	}
	
	public int dpTopx(int dp)
	{
		int px = 0;
		View v = LayoutInflater.from(mContext).inflate(R.layout.test,null,false);
		px = dp * v.getLayoutParams().height;
		return px;
	}
	
	public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    }  
	
}
