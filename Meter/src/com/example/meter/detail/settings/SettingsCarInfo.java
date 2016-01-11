package com.example.meter.detail.settings;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.meter.R;
import com.example.meter.detail.SettingsActivity;
import com.example.meter.view.TabView;
import com.example.meter.view.TabView.OnTabClickListener;

@SuppressLint("NewApi")
public class SettingsCarInfo extends Fragment {

	private LinearLayout mCarinfoContent;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private Fragment mCurrentFragment;
	
	private TabView mTabView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.settings_carinfo,
				((SettingsActivity) getActivity()).getContentView(), false);

		mCarinfoContent = (LinearLayout) content
				.findViewById(R.id.settings_carinfo_content);
		initTab();

		mTabView = (TabView) content
				.findViewById(R.id.setting_carinfo_tab_view);
		mTabView.setOnTabClickListener(new OnTabClickListener() {
			@Override
			public void onClick(View view, int position) {
				switchFragment(position);
			}
		});

		mTabView.setSelector(3);
		return content;
	}

	private void switchFragment(int position) {
		FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		if (mCurrentFragment != null) {
			fragmentTransaction.hide(mCurrentFragment);
		}
		fragmentTransaction.show(mFragments.get(position));
		fragmentTransaction.commit();
		mCurrentFragment = mFragments.get(position);
	}

	private void initTab() {
		XiTongWenDu xiTongWenDu = new XiTongWenDu();
		DianYaDianChi dianYaDianChi = new DianYaDianChi();
		XingShiLiCheng xingShiLiCheng = new XingShiLiCheng();
		XianZaiSheZhi xianZaiSheZhi = new XianZaiSheZhi();

		mFragments.clear();

		FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		int containerViewId = R.id.settings_carinfo_content;
		addFragmentToTransaction(fragmentTransaction, containerViewId,
				xiTongWenDu);
		addFragmentToTransaction(fragmentTransaction, containerViewId,
				dianYaDianChi);
		addFragmentToTransaction(fragmentTransaction, containerViewId,
				xingShiLiCheng);
		addFragmentToTransaction(fragmentTransaction, containerViewId,
				xianZaiSheZhi);
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

	class XiTongWenDu extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View content = inflater.inflate(R.layout.settings_carinfo_xtwd,
					mCarinfoContent, false);
			return content;
		}
	}

	class DianYaDianChi extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View content = inflater.inflate(R.layout.settings_carinfo_dy,
					mCarinfoContent, false);
			return content;
		}
	}

	class XingShiLiCheng extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View content = inflater.inflate(R.layout.settings_carinfo_xslc,
					mCarinfoContent, false);
			return content;
		}
	}

	class XianZaiSheZhi extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View content = inflater.inflate(R.layout.settings_carinfo_xzsz,
					mCarinfoContent, false);
			return content;
		}
	}

}
