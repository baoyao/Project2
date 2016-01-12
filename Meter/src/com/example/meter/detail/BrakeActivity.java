package com.example.meter.detail;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.ConfigurationUtil;

public class BrakeActivity extends BaseActivity {

	private TextView title;
	private SeekBar brakeModeSeekBar;

	@Override
	public void setContentView() {
		setContentView(R.layout.brake_screen);
		title = (TextView) findViewById(R.id.title);
		brakeModeSeekBar=(SeekBar) findViewById(R.id.brakeModeSeekBar);
	}

	@Override
	public void setTopView() {
		title.setText(getResources().getString(R.string.brake));
		
	}

	@Override
	public void setCenterView() {

	}

	@Override
	public void setBottomView() {
		brakeModeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(seekBar.getProgress()>50){
					setSeekBarProgress(ConfigurationUtil.BRAKE_AUTO);
				}else{
					setSeekBarProgress(ConfigurationUtil.BRAKE_MANUAL);
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
			}
		});
		setSeekBarProgress(ConfigurationUtil.getInstance(this)
				.getConfigurationForInt(ConfigurationUtil.SHARED_KEY_BRAKE));
	}

	private void setSeekBarProgress(int position) {
		brakeModeSeekBar.setProgress((position - 1) * 100);
		ConfigurationUtil.getInstance(this).saveConfiguration(
				ConfigurationUtil.SHARED_KEY_BRAKE, position);
	}

}
