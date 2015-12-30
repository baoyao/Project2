package com.example.meter.detail;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.meter.R;

public class BrakeActivity extends BaseActivity {

	private TextView title;
	private SeekBar brakeModeSeekBar;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.brake_screen);
		title = (TextView) findViewById(R.id.title);
		brakeModeSeekBar=(SeekBar) findViewById(R.id.brakeModeSeekBar);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title.setText(getResources().getString(R.string.brake));
		
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub
		brakeModeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(seekBar.getProgress()>50){
					seekBar.setProgress(100);
				}else{
					seekBar.setProgress(0);
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
