package com.example.meter.detail;

import com.example.meter.R;
import com.example.meter.view.NinePointLineView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LockActivity extends BaseActivity{

	private TextView title;
	private LinearLayout nineLayer;
	private NinePointLineView ninePointLineView;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.lock_screen);
		title = (TextView) findViewById(R.id.title);
		nineLayer=(LinearLayout) findViewById(R.id.nine_con);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title.setText(getResources().getString(R.string.lock));
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub
		nineLayer.removeAllViews();
		ninePointLineView=new NinePointLineView(this);
		nineLayer.addView(ninePointLineView);
	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub

	}

}
