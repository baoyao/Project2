package com.example.meter.detail;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.meter.R;

public class SpeedActivity extends BaseActivity {

	private TextView title,dangWeiTxt;
	/** 档位 */
	private SeekBar dangWeiSeekBar;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.speed_screen);
		title = (TextView) findViewById(R.id.title);
		dangWeiTxt = (TextView) findViewById(R.id.dangWeiTxt);
		dangWeiSeekBar = (SeekBar) findViewById(R.id.dangWeiSeekBar);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title.setText(getResources().getString(R.string.speed));
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
						int p=seekBar.getProgress();
						if(p>350){
							seekBar.setProgress(400);
							dangWeiTxt.setText(5+"");
						}else if(p>250){
							seekBar.setProgress(300);
							dangWeiTxt.setText(4+"");
						}else if(p>150){
							seekBar.setProgress(200);
							dangWeiTxt.setText(3+"");
						}else if(p>50){
							seekBar.setProgress(100);
							dangWeiTxt.setText(2+"");
						}else{
							seekBar.setProgress(0);
							dangWeiTxt.setText(1+"");
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
	}

}