package com.example.meter.detail.settings;

import com.example.meter.R;
import com.example.meter.detail.SettingsActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsLanguage extends Fragment implements OnCheckedChangeListener{
	
	private RadioGroup mLanguageList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.settings_language,
				((SettingsActivity) getActivity()).getContentView(), false);
		
		mLanguageList =(RadioGroup) content.findViewById(R.id.settings_language_list);
		mLanguageList.removeAllViews();
		String[] optionItems = getResources().getStringArray(
				R.array.settings_languages);

		RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.MATCH_PARENT, (int) getResources()
						.getDimension(R.dimen.settings_left_item_height));
		for (int i = 0; i < optionItems.length; i++) {
			RadioButton radioButton = new RadioButton(getActivity());
			radioButton.setLayoutParams(layoutParams);
			radioButton.setText(optionItems[i]);
			radioButton.setTag(i);

			radioButton
					.setButtonDrawable(R.drawable.settings_left_item_selector);

			radioButton.setOnCheckedChangeListener(this);
			mLanguageList.addView(radioButton);
			if (i == 0) {
				mLanguageList.check(radioButton.getId());
			}
		}
		return content;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

}
