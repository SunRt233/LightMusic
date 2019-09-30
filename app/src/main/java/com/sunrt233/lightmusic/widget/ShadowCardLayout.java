package com.sunrt233.lightmusic.widget;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.content.res.*;
import com.sunrt233.lightmusic.*;
import android.graphics.*;
import android.graphics.drawable.*;

public class ShadowCardLayout extends FrameLayout {

	private int mShadowColor = Color.TRANSPARENT;
	private float mLayoutRadius = 0;
	private float mLayoutElevation = 0;
	private int mLayoutBackgroundColor = Color.parseColor("#FFFFFF");
	private Drawable background;

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mLayoutBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mChildDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private RectF mRectF = new RectF();
	private RectF mLayoutBackgroundRectF = new RectF();
	private Path mChildDrawPath = new Path();
	private float[] radius = new float[8];//{dip2px(10),dip2px(10),dip2px(10),dip2px(10),dip2px(10),dip2px(10),dip2px(10),dip2px(10)};

	public ShadowCardLayout(Context context) {
		this(context, null);
	}

	public ShadowCardLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ShadowCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO: Implement this method
		super.onLayout(changed, left, top, right, bottom);

		mLayoutBackgroundRectF.left = 0 + getPaddingLeft();
		mLayoutBackgroundRectF.top = 0 + getPaddingTop();
		mLayoutBackgroundRectF.right = this.getWidth() - getPaddingRight();
		mLayoutBackgroundRectF.bottom = this.getHeight() - getPaddingBottom();

		mRectF.left = mLayoutBackgroundRectF.left - dip2px(1);
		mRectF.top = mLayoutBackgroundRectF.top - dip2px(1);
		mRectF.right = mLayoutBackgroundRectF.right + dip2px(1);
		mRectF.bottom = mLayoutBackgroundRectF.bottom + dip2px(1);

		mChildDrawPath.addRoundRect(mLayoutBackgroundRectF, radius, Path.Direction.CW);
		
		//getChildAt(0);
		//optimizeChildView();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO: Implement this method
		super.onDraw(canvas);
		//绘制阴影与背景色
		canvas.drawRoundRect(mRectF, mLayoutRadius, mLayoutRadius, mPaint);
		canvas.drawRoundRect(mLayoutBackgroundRectF, mLayoutRadius, mLayoutRadius, mLayoutBackgroundPaint);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// TODO: Implement this method
		canvas.clipPath(mChildDrawPath);
		return super.drawChild(canvas, child, drawingTime);
	}
	
	/*@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO: Implement this method
		canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, Canvas.ALL_SAVE_FLAG);
		super.dispatchDraw(canvas);
		canvas.drawPath(mChildDrawPath, mChildDrawPaint);
		canvas.restore();
	}*/
	
	protected void optimizeChildView()
	{
		View clickEffect = new View(getContext());
		clickEffect.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		addView(clickEffect);
		
		FrameLayout childRoot = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_shadowcardlayout_childroot, this);
		childRoot.setOnClickListener(new View.OnClickListener(){ 
		
				@Override
				public void onClick(View p1) {
					// TODO: Implement this method
					Toast.makeText(getContext(),"点击ChildRoot",1).show();
					//Toast.makeText(getContext(),"parent:" + getChildCount() + "childroot:" + childRoot.getChildCount(),1).show();
				}
			});
			
		if(getChildCount() > 2)
		{
			View view = getChildAt(0);
			removeViewAt(0);
			childRoot.addView(view);
			Toast.makeText(getContext(),"childroot内容已添加", 1).show();
		}
		
	}

	private void init(AttributeSet attrs) {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		this.setWillNotDraw(false);

		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowCardLayout);
		TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.AppCompatTheme);
		if (typedArray != null) {
			mLayoutRadius = typedArray.getDimension(R.styleable.ShadowCardLayout_layoutRadius, dip2px(0));
			mLayoutElevation = typedArray.getDimension(R.styleable.ShadowCardLayout_layoutElevation, dip2px(0));
			mShadowColor = typedArray.getColor(R.styleable.ShadowCardLayout_shadowColor, Color.parseColor("#99000000"));
			mLayoutBackgroundColor = typedArray.getColor(R.styleable.ShadowCardLayout_layoutBackgroundColor, Color.parseColor("#FFFFFF"));
			//typedArray.getDrawable(R.attr.selectableItemBackgroundBorderless);
			Toast.makeText(getContext(), "获取属性", 1).show();
			for(int i = 0;i < radius.length; i++)
			{
				radius[i] = mLayoutRadius;
			}
		}
		
		if (t != null) background = t.getDrawable(R.styleable.AppCompatTheme_selectableItemBackground);

		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.TRANSPARENT);
		mPaint.setShadowLayer(mLayoutElevation, 0, 0, mShadowColor);
		
		mLayoutBackgroundPaint.setAntiAlias(true);
		mLayoutBackgroundPaint.setColor(mLayoutBackgroundColor);
		mLayoutBackgroundPaint.setStyle(Paint.Style.FILL);
		
		//this.setPadding(0,0,0,0);
		
		mChildDrawPaint.setAntiAlias(true);
		mChildDrawPaint.setColor(Color.WHITE);
		mChildDrawPaint.setStyle(Paint.Style.FILL);
		mChildDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	}

	private float dip2px(float dpValue) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        float scale = dm.density;
        return (dpValue * scale + 0.5F);
    }

	@Deprecated
	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		// TODO: Implement this method
		//super.setPadding(left, top, right, bottom);
		
	}

	@Deprecated
	@Override
	public void setPaddingRelative(int start, int top, int end, int bottom) {
		// TODO: Implement this method
		super.setPaddingRelative(start, top, end, bottom);
	}

}
