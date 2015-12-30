package com.example.meter.detail;

import android.content.Intent;
import android.widget.TextView;

import com.example.meter.R;

public class MusicActivity extends BaseActivity {

	private TextView title;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.music_screen);

		Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
		startActivity(intent);
		this.finish();
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		title.setText(getResources().getString(R.string.music));
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
