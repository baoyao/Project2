package com.example.meter.detail;

import android.widget.ListView;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.SettingsTitleAdapter;

public class SettingsActivity extends BaseActivity {

	private TextView title;
	
	private ListView mListView;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.settings_screen);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		title.setText(getResources().getString(R.string.settings));
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub
		mListView=(ListView)findViewById(R.id.title_list);
		String[] titles=getResources().getStringArray(R.array.settings_title);
		mListView.setAdapter(new SettingsTitleAdapter(this, titles));

	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub

	}

}
