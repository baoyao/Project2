package com.example.meter.detail;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.meter.R;

public class ModeActivity extends BaseActivity{

	private TextView title,moShiTxt;
	
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
		dangWeiSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int p=seekBar.getProgress();
				if(p>150){
					seekBar.setProgress(200);
					moShiTxt.setText(getResources().getString(R.string.letter_d));
				}else if(p>50){
					seekBar.setProgress(100);
					moShiTxt.setText(getResources().getString(R.string.letter_n));
				}else{
					seekBar.setProgress(0);
					moShiTxt.setText(getResources().getString(R.string.letter_c));
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
