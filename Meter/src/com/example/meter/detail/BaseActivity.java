package com.example.meter.detail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public abstract class BaseActivity extends Activity {

	public abstract void setContentView();

	public abstract void setTopView();

	public abstract void setCenterView();

	public abstract void setBottomView();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		setTopView();
		setCenterView();
		setBottomView();
	}
	
	public void finishActivity(View v) {
		finish();
	}

}
