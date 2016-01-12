package com.example.meter.detail.settings;

import com.example.meter.R;
import com.example.meter.detail.SettingsActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsGame extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.settings_game,
				((SettingsActivity) getActivity()).getContentView(), false);
		
		return content;
	}

}
