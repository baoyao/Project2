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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SettingsGeneral extends Fragment {
	private SeekBar yuyindaohang;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.settings_general,
				((SettingsActivity) getActivity()).getContentView(), false);

		yuyindaohang = (SeekBar) content.findViewById(R.id.yuyindaohang);
		yuyindaohang.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int p = seekBar.getProgress();
				if (p > 100) {
					seekBar.setProgress(200);
				}else {
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
		return content;
	}

}
