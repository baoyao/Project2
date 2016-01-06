package com.example.meter.detail;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.*;

public class SettingsActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private TextView title;
	private RadioGroup mLeftOptionRadioGroup;
	private LinearLayout mContentLayout;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	private FragmentManager mFragmentManager;
	private Fragment currentFragment;

	public int selectIndex = 0;

	@Override
	public void setContentView() {
		setContentView(R.layout.settings_screen);
	}

	@Override
	public void setTopView() {
		title = (TextView) findViewById(R.id.title);
		title.setText(getResources().getString(R.string.settings));
		mContentLayout = (LinearLayout) findViewById(R.id.settings_content);
		mFragmentManager = getFragmentManager();
		initSettingsItem();
		setLeftOptionItem();
	}

	@Override
	public void setCenterView() {

	}

	@Override
	public void setBottomView() {

	}

	@SuppressLint("NewApi")
	private void setLeftOptionItem() {
		mLeftOptionRadioGroup = (RadioGroup) findViewById(R.id.settings_left_options);
		mLeftOptionRadioGroup.removeAllViews();
		String[] optionItems = getResources().getStringArray(
				R.array.settings_title);

		RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.MATCH_PARENT, (int) getResources()
						.getDimension(R.dimen.settings_left_item_height));
		for (int i = 0; i < optionItems.length; i++) {
			RadioButton radioButton = new RadioButton(this);
			radioButton.setLayoutParams(layoutParams);
			radioButton.setText(optionItems[i]);
			radioButton.setTag(i);

			radioButton
					.setButtonDrawable(R.drawable.settings_left_item_selector);

			radioButton.setOnCheckedChangeListener(this);
			mLeftOptionRadioGroup.addView(radioButton);
			if (i == 0) {
				mLeftOptionRadioGroup.check(radioButton.getId());
			}
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (!isChecked) {
			return;
		}
		int position = (Integer) buttonView.getTag();
		FragmentTransaction fragmentTransaction= mFragmentManager.beginTransaction();
		if(currentFragment!=null){
			fragmentTransaction.hide(currentFragment);
		}
		fragmentTransaction.show(mFragments.get(position)).commit();
		currentFragment = mFragments.get(position);
	}

	private void initSettingsItem() {
		SettingsCarInfo settingsCarInfo = new SettingsCarInfo();
		SettingsGeneral settingsGeneral = new SettingsGeneral();
		SettingsGame settingsGame = new SettingsGame();
		SettingsEducation settingsEducation = new SettingsEducation();
		SettingsNetwork settingsNetwork = new SettingsNetwork();
		SettingsMusic settingsMusic = new SettingsMusic();
		SettingsVideo settingsVideo = new SettingsVideo();
		SettingsCheckSelf settingsCheckSelf = new SettingsCheckSelf();
		SettingsLock settingsLock = new SettingsLock();
		SettingsLanguage settingsLanguage = new SettingsLanguage();
		SettingsFeature settingsFeature = new SettingsFeature();

		mFragmentManager
				.beginTransaction()
				.add(R.id.settings_content, settingsCarInfo, "SettingsCarInfo")
				.add(R.id.settings_content, settingsGeneral, "SettingsGeneral")
				.add(R.id.settings_content, settingsGame, "SettingsGame")
				.add(R.id.settings_content, settingsEducation,
						"SettingsEducation")
				.add(R.id.settings_content, settingsNetwork, "SettingsNetwork")
				.add(R.id.settings_content, settingsMusic, "SettingsMusic")
				.add(R.id.settings_content, settingsVideo, "SettingsVideo")
				.add(R.id.settings_content, settingsCheckSelf,
						"SettingsCheckSelf")
				.add(R.id.settings_content, settingsLock, "SettingsLock")
				.add(R.id.settings_content, settingsLanguage,
						"SettingsLanguage")
				.add(R.id.settings_content, settingsFeature, "SettingsFeature")
				.commit();

		mFragments.clear();
		mFragments.add(settingsCarInfo);
		mFragments.add(settingsGeneral);
		mFragments.add(settingsGame);
		mFragments.add(settingsEducation);
		mFragments.add(settingsNetwork);
		mFragments.add(settingsMusic);
		mFragments.add(settingsVideo);
		mFragments.add(settingsCheckSelf);
		mFragments.add(settingsLock);
		mFragments.add(settingsLanguage);
		mFragments.add(settingsFeature);
	}
	
	public ViewGroup getContentView() {
		return mContentLayout;
	}

}
