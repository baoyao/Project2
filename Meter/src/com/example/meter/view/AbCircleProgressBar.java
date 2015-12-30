package com.example.meter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class AbCircleProgressBar extends View {
	private int[] arcColors = { -65536, -16711936, -16776961, -65536 };
	float blur = 3.5F;
	float[] direction = { 1.0F, 1.0F, 1.0F };
	private EmbossMaskFilter emboss = null;
	private Paint fillArcPaint = null;
	private int height;
	float light = 0.8F;

	private BlurMaskFilter mBlur = null;
	private int max = 100;
	private RectF oval;
	private int pathBorderColor = 16513096;
	private int pathColor = 15789791;
	private Paint pathPaint = null;
	private int pathWidth = 25;
	private int progress = 0;
	private int radius = 120;
	private boolean reset = false;
	private int[] shadowsColors = { -15658735, 11184810, 11184810 };
	float specular = 6.0F;
	private int width;

	public AbCircleProgressBar(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.pathPaint = new Paint();
		this.pathPaint.setAntiAlias(true);
		this.pathPaint.setFlags(1);
		this.pathPaint.setStyle(Paint.Style.STROKE);
		this.pathPaint.setDither(true);
		this.pathPaint.setStrokeJoin(Paint.Join.ROUND);
		this.fillArcPaint = new Paint();
		this.fillArcPaint.setAntiAlias(true);
		this.fillArcPaint.setFlags(1);
		this.fillArcPaint.setStyle(Paint.Style.STROKE);
		this.fillArcPaint.setDither(true);
		this.fillArcPaint.setStrokeJoin(Paint.Join.ROUND);
		this.oval = new RectF();
		this.emboss = new EmbossMaskFilter(this.direction, this.light,
				this.specular, this.blur);
		this.mBlur = new BlurMaskFilter(20.0F, BlurMaskFilter.Blur.NORMAL);
	}

	public AbOnProgressListener getAbOnProgressListener() {
		return this.mAbOnProgressListener;
	}

	public int getMax() {
		return this.max;
	}

	public int getProgress() {
		return this.progress;
	}

	public int getRadius() {
		return this.radius;
	}

	@SuppressLint({ "DrawAllocation" })
	protected void onDraw(Canvas paramCanvas) {
		super.onDraw(paramCanvas);
		Log.d("liudong", "" + this.progress + "   this.reset=" + this.reset);

		if (this.progress == 0) {
			paramCanvas.drawColor(0);
			this.reset = false;
		}
		this.width = getMeasuredWidth();
		this.height = getMeasuredHeight();
		this.radius = (getMeasuredWidth() / 2 - this.pathWidth);
		this.pathPaint.setColor(this.pathColor);
		this.pathPaint.setStrokeWidth(this.pathWidth);
		this.pathPaint.setMaskFilter(this.emboss);
		paramCanvas.drawCircle(this.width / 2, this.height / 2, this.radius,
				this.pathPaint);
		this.pathPaint.setStrokeWidth(0.5F);
		this.pathPaint.setColor(Color.YELLOW);
		paramCanvas.drawCircle(this.width / 2, this.height / 2,
				0.5F + (this.radius + this.pathWidth / 2), this.pathPaint);
		paramCanvas.drawCircle(this.width / 2, this.height / 2, this.radius
				- this.pathWidth / 2 - 0.5F, this.pathPaint);
		int[] arrayOfInt = new int[3];
		arrayOfInt[0] = Color.WHITE;
		arrayOfInt[1] = Color.RED;
		arrayOfInt[2] = Color.BLACK;

		RadialGradient localRadialGradient = new RadialGradient(this.width / 2,
				this.height / 2, 110.0F, arrayOfInt, null,
				Shader.TileMode.REPEAT);
		this.fillArcPaint.setShader(localRadialGradient);
		this.fillArcPaint.setMaskFilter(this.mBlur);
		this.fillArcPaint.setStrokeCap(Paint.Cap.ROUND);
		this.fillArcPaint.setShadowLayer(8.0F, 6.0F, 5.0F, Color.YELLOW);
		this.fillArcPaint.setStrokeWidth(this.pathWidth);
		this.oval.set(this.width / 2 - this.radius, this.height / 2
				- this.radius, this.width / 2 + this.radius, this.height / 2
				+ this.radius);
		float count = 360.0f * this.progress / this.max;
		Log.d("liudong", "count = " + count);
		paramCanvas
				.drawArc(this.oval, -210.0f, count, false, this.fillArcPaint);

	}

	protected void onMeasure(int paramInt1, int paramInt2) {
		int i = View.MeasureSpec.getSize(paramInt2);
		setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), i);
	}

	public void reset() {
		this.reset = true;
		this.progress = 0;
		invalidate();
	}

	public void setAbOnProgressListener(
			AbOnProgressListener paramAbOnProgressListener) {
		this.mAbOnProgressListener = paramAbOnProgressListener;
	}

	public void setMax(int paramInt) {
		this.max = paramInt;
	}

	public void setProgress(int paramInt) {
		this.progress = paramInt;
		invalidate();
		if (this.mAbOnProgressListener != null) {
			if (this.max <= this.progress)
				this.mAbOnProgressListener.onComplete();
		} else
			return;
		this.mAbOnProgressListener.onProgress(paramInt);
	}

	public void setRadius(int paramInt) {
		this.radius = paramInt;
	}

	private AbOnProgressListener mAbOnProgressListener = new AbOnProgressListener() {

		@Override
		public void onProgress(int paramInt) {
			// TODO Auto-generated method stub
			AbCircleProgressBar.this.progress = paramInt;

			AbCircleProgressBar.this.invalidate();
		}

		@Override
		public void onComplete() {
			// TODO Auto-generated method stub

		}
	};

	public static abstract interface AbOnProgressListener {
		public abstract void onComplete();

		public abstract void onProgress(int paramInt);
	}
}