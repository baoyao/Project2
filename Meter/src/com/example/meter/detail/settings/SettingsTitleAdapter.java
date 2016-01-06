package com.example.meter.detail.settings;

import com.example.meter.R;
import com.example.meter.detail.SettingsActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsTitleAdapter extends BaseAdapter {

	private SettingsActivity activity;
	
	private Context mContext;
	
	private String[] mTitles;
	

	public SettingsTitleAdapter(Context context,String[] titles) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mTitles=titles;
		this.activity=(SettingsActivity) context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitles.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTitles[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class ViewHold {
		ImageView selectImageView;
		TextView titleTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHold hold = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.settings_title_list_item,
					null);
			hold = new ViewHold();
			hold.selectImageView = (ImageView) convertView
					.findViewById(R.id.select);
			hold.titleTextView = (TextView) convertView
					.findViewById(R.id.title);
			convertView.setTag(hold);
		} else {
			hold = (ViewHold) convertView.getTag();
		}
		
		if(position == activity.selectIndex){
			convertView.setSelected(true);
			Log.v("tt", "getView position: "+position);
		}else if(convertView.isSelected()){
			convertView.setSelected(false);
		}
		
		hold.titleTextView.setText(mTitles[position]);

		return convertView;
	}

}
