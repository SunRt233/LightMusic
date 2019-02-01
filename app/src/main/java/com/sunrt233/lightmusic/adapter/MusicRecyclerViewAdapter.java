package com.sunrt233.lightmusic.adapter;

import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.data.*;
import com.sunrt233.lightmusic.utils.*;
import java.util.*;

import android.support.v7.widget.PopupMenu;

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.MyViewHolder>
{
	private ArrayList<DataList> dl;
	private View.OnClickListener mOnClickListener = null;
	private OnItemClickListener mOnItemClickListener;
	private OnItemPopupMenuItemClickListener mOnItemPopupMenuItemClickListener;
	private List<Bitmap> artWorkImages = null;
	private Context mContext;

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

		holder.musicName.setText(dl.get(p2).getMusicName());
		holder.musicAuthor.setText(dl.get(p2).getMusicAuthor());
		BitmapUtils.setBitmapFromNetWork(dl.get(p2).getImageUrl(), holder.artWorkImage, null);
		//final PopupMenu menu = new PopupMenu(mContext,holder.popupMenu);
		//menu.getMenuInflater().inflate(R.menu.list_music_box_popupmenu,menu.getMenu());

		holder.rootLayout.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					int position = holder.getLayoutPosition();
					mOnItemClickListener.onItemClick(holder.itemView, position, dl.get(p2));
					// TODO: Implement this method
				}
			});

		holder.popupMenu.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					PopupMenu menu = new PopupMenu(mContext, p1);
					menu.getMenuInflater().inflate(R.menu.list_music_box_popupmenu, menu.getMenu());
					menu.show();
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
		public ImageView artWorkImage,popupMenu;

		public MyViewHolder(View v)
		{
			super(v);
			musicName = (TextView) v.findViewById(R.id.list_music_boxMusicName);
			musicAuthor = (TextView) v.findViewById(R.id.list_music_boxMusicAuthor);
			rootLayout = (RelativeLayout) v.findViewById(R.id.list_music_boxRelativeLayout);
			artWorkImage  =  (ImageView) v.findViewById(R.id.list_music_boxImageView);
			popupMenu = (ImageView) v.findViewById(R.id.list_music_boxMenu);
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
}
