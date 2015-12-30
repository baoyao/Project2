package com.example.meter.detail;

import com.example.meter.R;

import android.os.Bundle;
import android.widget.TextView;

public class LightActivity extends BaseActivity {

	private TextView title;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.light_screen);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		title.setText(getResources().getString(R.string.light));
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub

	}

}
