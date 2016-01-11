package com.example.meter.detail;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.meter.R;
import com.example.meter.detail.settings.SettingsCarInfo;
import com.example.meter.detail.settings.SettingsCheckSelf;
import com.example.meter.detail.settings.SettingsEducation;
import com.example.meter.detail.settings.SettingsFeature;
import com.example.meter.detail.settings.SettingsGame;
import com.example.meter.detail.settings.SettingsGeneral;
import com.example.meter.detail.settings.SettingsLanguage;
import com.example.meter.detail.settings.SettingsLock;
import com.example.meter.detail.settings.SettingsMusic;
import com.example.meter.detail.settings.SettingsNetwork;
import com.example.meter.detail.settings.SettingsVideo;

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
		mFragmentManager = getSupportFragmentManager();
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
		FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();
		if (currentFragment != null) {
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

		mFragments.clear();

		FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();

		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsCarInfo);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsGeneral);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsGame);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsEducation);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsNetwork);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsMusic);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsVideo);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsCheckSelf);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsLock);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsLanguage);
		addFragmentToTransaction(fragmentTransaction, R.id.settings_content,
				settingsFeature);
		
		fragmentTransaction.commit();
	}

	private void addFragmentToTransaction(
			FragmentTransaction fragmentTransaction, int containerViewId,
			Fragment fragment) {
		if (!fragment.isAdded()) {
			fragmentTransaction.add(containerViewId, fragment);
			fragmentTransaction.hide(fragment);
		}
		mFragments.add(fragment);
	}

	public ViewGroup getContentView() {
		return mContentLayout;
	}

}
