package com.example.meter.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BaseActivity extends FragmentActivity {

	public void setContentView() {

	}

	public void setTopView() {

	}

	public void setCenterView() {

	}

	public void setBottomView() {

	}

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
