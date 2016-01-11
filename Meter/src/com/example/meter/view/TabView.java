package com.example.meter.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meter.R;

public class TabView extends LinearLayout {

	private OnTabClickListener mOnTabClickListener;

	private List<View> mTabs = new ArrayList<View>();

	public TabView(Context context) {
		this(context, null, 0);
	}

	public TabView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mTabs.clear();
		int count = this.getChildCount();
		for (int i = 0; i < count; i++) {
			this.getChildAt(i).setOnClickListener(tabOnClickListener);
			mTabs.add(this.getChildAt(i));
		}
	}

	private OnClickListener tabOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			for (int i = 0; i < mTabs.size(); i++) {
				View view = mTabs.get(i);
				TextView textView = null;
				if (view instanceof LinearLayout) {
					textView = (TextView) ((LinearLayout) view).getChildAt(1);
				}

				if (v == view) {
					view.setSelected(true);
					if (textView != null) {
						textView.setTextColor(getContext()
								.getResources()
								.getColor(
										R.color.settings_xzsz_bottom_option_selected_color));
					}
					if (mOnTabClickListener != null) {
						mOnTabClickListener.onClick(v, i);
					}
				} else {
					view.setSelected(false);
					if (textView != null) {
						textView.setTextColor(getContext()
								.getResources()
								.getColor(
										R.color.settings_xzsz_bottom_option_selected_normal));
					}
				}
			}
		}
	};

	public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
		mOnTabClickListener = onTabClickListener;
	}

	public interface OnTabClickListener {
		void onClick(View view, int position);
	}

	public void setSelector(int position) {
		for (int i = 0; i < mTabs.size(); i++) {
			if (position == i) {
				View view = mTabs.get(i);
				TextView textView = (TextView) ((LinearLayout) view)
						.getChildAt(1);
				textView.setTextColor(getContext().getResources().getColor(
						R.color.settings_xzsz_bottom_option_selected_color));
				if (mOnTabClickListener != null) {
					mOnTabClickListener.onClick(view, i);
				}
			}
		}
	}

}
