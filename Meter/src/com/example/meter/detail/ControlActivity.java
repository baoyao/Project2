package com.example.meter.detail;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.ConfigurationUtil;

public class ControlActivity extends BaseActivity {

	private TextView title, caoKongMoShiTxt;
	private RadioGroup controlRadioGroup;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.controll_screen);
		title = (TextView) findViewById(R.id.title);
		caoKongMoShiTxt = (TextView) findViewById(R.id.caoKongMoShiTxt);

		controlRadioGroup = (RadioGroup) findViewById(R.id.controlRadioGroup);
	}

	@Override
	public void setTopView() {
		// TODO Auto-generated method stub
		title.setText(getResources().getString(R.string.controll));
	}

	@Override
	public void setCenterView() {
		// TODO Auto-generated method stub
		controlRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.controlRadioButton1:
							setControl(ConfigurationUtil.CONTROL_1_MANUAL);
							break;
						case R.id.controlRadioButton2:
							setControl(ConfigurationUtil.CONTROL_2_REMOTE);
							break;
						case R.id.controlRadioButton3:
							setControl(ConfigurationUtil.CONTROL_3_PC);
							break;
						default:
							break;
						}
					}
				});
		
		controlRadioGroup.check(controlRadioGroup.getChildAt(
				ConfigurationUtil.getInstance(this).getConfigurationForInt(
						ConfigurationUtil.SHARED_KEY_CONTROL) - 1).getId());
	}

	@Override
	public void setBottomView() {
		// TODO Auto-generated method stub

	}

	private void setControl(int position) {
		ConfigurationUtil.getInstance(this).saveConfiguration(
				ConfigurationUtil.SHARED_KEY_CONTROL, position);
		switch (position) {
		case ConfigurationUtil.CONTROL_1_MANUAL:
			caoKongMoShiTxt.setText(getResources().getString(
					R.string.control_shoudong));
			break;
		case ConfigurationUtil.CONTROL_2_REMOTE:
			caoKongMoShiTxt.setText(getResources().getString(
					R.string.control_yaokong));
			break;
		case ConfigurationUtil.CONTROL_3_PC:
			caoKongMoShiTxt.setText(getResources().getString(
					R.string.control_diannao));
			break;
		default:
			break;
		}
	}
}
