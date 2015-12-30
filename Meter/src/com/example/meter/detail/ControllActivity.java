package com.example.meter.detail;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.meter.R;

public class ControllActivity extends BaseActivity {

	private TextView title,caoKongMoShiTxt;
	private RadioGroup controlRadioGroup;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.controll_screen);
		title = (TextView) findViewById(R.id.title);
		caoKongMoShiTxt = (TextView) findViewById(R.id.caoKongMoShiTxt);
		
		controlRadioGroup=(RadioGroup) findViewById(R.id.controlRadioGroup);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title.setText(getResources().getString(R.string.controll));
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub
		controlRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.controlRadioButton1:
					caoKongMoShiTxt.setText(getResources().getString(R.string.control_shoudong));
					break;
				case R.id.controlRadioButton2:
					caoKongMoShiTxt.setText(getResources().getString(R.string.control_yaokong));
					break;
				case R.id.controlRadioButton3:
					caoKongMoShiTxt.setText(getResources().getString(R.string.control_diannao));
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub

	}

}
