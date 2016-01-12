package com.example.meter.detail;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.ConfigurationUtil;

public class SpeedActivity extends BaseActivity {

	private TextView title, dangWeiTxt;
	/** 档位 */
	private SeekBar dangWeiSeekBar;

	@Override
	public void setContentView() {
		setContentView(R.layout.speed_screen);
		title = (TextView) findViewById(R.id.title);
		dangWeiTxt = (TextView) findViewById(R.id.dangWeiTxt);
		dangWeiSeekBar = (SeekBar) findViewById(R.id.dangWeiSeekBar);
	}

	@Override
	public void setTopView() {
		title.setText(getResources().getString(R.string.speed));
	}

	@Override
	public void setBottomView() {
		dangWeiSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						int p = seekBar.getProgress();
						if (p > (((5 - 2) * 100) + 50)) {
							setSeekBarProgress(5);
						} else if (p > (((4 - 2) * 100) + 50)) {
							setSeekBarProgress(4);
						} else if (p > (((3 - 2) * 100) + 50)) {
							setSeekBarProgress(3);
						} else if (p > (((2 - 2) * 100) + 50)) {
							setSeekBarProgress(2);
						} else {
							setSeekBarProgress(1);
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

		int speed = ConfigurationUtil.getInstance(this).getConfigurationForInt(
				ConfigurationUtil.SHARED_KEY_SPEED);
		setSeekBarProgress(speed);
	}

	private void setSeekBarProgress(int position) {
		dangWeiSeekBar.setProgress((position - 1) * 100);
		dangWeiTxt.setText(position + "");
		ConfigurationUtil.getInstance(this).saveConfiguration(
				ConfigurationUtil.SHARED_KEY_SPEED, position);
	}

}