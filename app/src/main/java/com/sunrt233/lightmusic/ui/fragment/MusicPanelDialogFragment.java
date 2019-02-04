package com.sunrt233.lightmusic.ui.fragment;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.view.*;
import com.sunrt233.lightmusic.*;
import android.support.v4.widget.*;

public class MusicPanelDialogFragment extends BottomSheetDialogFragment
{
	private BottomSheetBehavior mBehavior;
	private NestedScrollView mNestedScrollView;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
		View view = View.inflate(getContext(),R.layout.fragment_musicpaneldialog,null);
		dialog.setContentView(view);
		mBehavior = BottomSheetBehavior.from((View) view.getParent());
		
		///dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		return dialog;
	}

	@Override
	public void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		final BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT; //可以写入自己想要的高度
			mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
			//mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
		mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){

				@Override
				public void onStateChanged(View p1, int p2)
				{
					// TODO: Implement this method
					if(p2!=BottomSheetBehavior.STATE_EXPANDED&&p2!=BottomSheetBehavior.STATE_DRAGGING)
					{
						//mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
						dialog.dismiss();
					}
				}

				@Override
				public void onSlide(View p1, float p2)
				{
					// TODO: Implement this method
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
