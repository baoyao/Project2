package com.example.meter.detail;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.ConfigurationUtil;

public class ModeActivity extends BaseActivity {

	private TextView title, moShiTxt;

	private SeekBar dangWeiSeekBar;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.mode_screen);
		title = (TextView) findViewById(R.id.title);
		moShiTxt = (TextView) findViewById(R.id.moShiTxt);
		dangWeiSeekBar = (SeekBar) findViewById(R.id.moShiSeekBar);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title.setText(getResources().getString(R.string.mode));
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub
		dangWeiSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						int p = seekBar.getProgress();
						if (p > 150) {
							setSeekBarProgress(ConfigurationUtil.MODE_3_D);
						} else if (p > 50) {
							setSeekBarProgress(ConfigurationUtil.MODE_2_N);
						} else {
							setSeekBarProgress(ConfigurationUtil.MODE_1_C);
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
					}
				});

		setSeekBarProgress(ConfigurationUtil.getInstance(this)
				.getConfigurationForInt(ConfigurationUtil.SHARED_KEY_MODE));
	}

	private void setSeekBarProgress(int position) {
		dangWeiSeekBar.setProgress((position - 1) * 100);
		ConfigurationUtil.getInstance(this).saveConfiguration(
				ConfigurationUtil.SHARED_KEY_MODE, position);
		switch (position) {
		case ConfigurationUtil.MODE_1_C:
			moShiTxt.setText(getResources().getString(R.string.letter_c));
			break;
		case ConfigurationUtil.MODE_2_N:
			moShiTxt.setText(getResources().getString(R.string.letter_n));
			break;
		case ConfigurationUtil.MODE_3_D:
			moShiTxt.setText(getResources().getString(R.string.letter_d));
			break;
		default:
			break;
		}
	}

}
