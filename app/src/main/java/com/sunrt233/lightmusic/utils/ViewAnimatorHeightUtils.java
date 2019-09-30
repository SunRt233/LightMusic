package com.sunrt233.lightmusic.utils;

import android.animation.*;
import android.view.*;
import android.widget.*;
import android.widget.FrameLayout.*;

public class ViewAnimatorHeightUtils {

	public static void animateHeight(final View view, int start, int end, AnimationListenr animationListenr) {
		View c;
		ValueAnimator mValueAnimator = ValueAnimator.ofInt(start,end);
		mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

				@Override
				public void onAnimationUpdate(ValueAnimator p1) {
					// TODO: Implement this method
					int h = (Integer) p1.getAnimatedValue();
					view.getLayoutParams().height = h;
					view.requestLayout();
					//view.setLayoutParams(lp);
				}
			});
		if(animationListenr != null) {
			mValueAnimator.addUpdateListener(animationListenr);
			mValueAnimator.addListener(animationListenr);
		}
		mValueAnimator.setDuration(2000);
		mValueAnimator.start();
		
	}

	public class AnimationListenr implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {

		@Override
		public void onAnimationStart(Animator p1) {
			// TODO: Implement this method
		}

		@Override
		public void onAnimationEnd(Animator p1) {
			// TODO: Implement this method
		}

		@Override
		public void onAnimationCancel(Animator p1) {
			// TODO: Implement this method
		}

		@Override
		public void onAnimationRepeat(Animator p1) {
			// TODO: Implement this method
		}

		@Override
		public void onAnimationUpdate(ValueAnimator p1) {
			// TODO: Implement this method
		}

	}

}
