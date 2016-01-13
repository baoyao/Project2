package com.example.meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.meter.detail.BrakeActivity;
import com.example.meter.detail.ControlActivity;
import com.example.meter.detail.LightActivity;
import com.example.meter.detail.LockActivity;
import com.example.meter.detail.ModeActivity;
import com.example.meter.detail.MusicActivity;
import com.example.meter.detail.SettingsActivity;
import com.example.meter.detail.SpeedActivity;
import com.example.meter.view.AbCircleProgressBar;

public class MainActivity extends Activity {

	private AbCircleProgressBar abCircleProgressBar = null;

	private boolean action = false;

	private int progress = 0;

	private final static int ADDCYCLIE = 0x01;

	private final static int DELCYCLIE = 0x02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.abCircleProgressBar = ((AbCircleProgressBar) findViewById(R.id.circleProgressBar));
		this.abCircleProgressBar.setMax(22);
		this.abCircleProgressBar.setProgress(4);
	}

	public void btnOnclick(View view) {
		Intent intent = null;
		if(view.getId()!=R.id.btn_6){
			intent = new Intent();
		}
		
		switch (view.getId()) {
		case R.id.btn_1:
			intent.setClass(this, SpeedActivity.class);
			break;
		case R.id.btn_2:
			intent.setClass(this, ModeActivity.class);
			break;
		case R.id.btn_3:
			intent.setClass(this, ControlActivity.class);
			break;
		case R.id.btn_4:
			intent.setClass(this, BrakeActivity.class);
			break;
		case R.id.btn_5:
			intent.setClass(this, LightActivity.class);
			break;
		case R.id.btn_6:
			showLockDialog();
			return;
		case R.id.btn_7:
			intent.setClass(this, MusicActivity.class);
			break;
		case R.id.btn_8:
			intent.setClass(this, SettingsActivity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
	}

	public void leftAndRightClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
			mHandler.sendEmptyMessage(DELCYCLIE);
			break;
		case R.id.btn_right:
			mHandler.sendEmptyMessage(ADDCYCLIE);
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			default:
			case 1:
				MainActivity.this.progress = MainActivity.this.progress + 2;
				break;
			case 2:
				MainActivity.this.progress = MainActivity.this.progress - 2;
				break;
			}

			if (MainActivity.this.progress < 0) {
				MainActivity.this.progress = 14;
			}

			if (MainActivity.this.progress > 14) {
				MainActivity.this.progress = 0;
				MainActivity.this.abCircleProgressBar.reset();
			}

			MainActivity.this.abCircleProgressBar
					.setProgress(MainActivity.this.progress);
			// while (true) {
			//
			// int i = MainActivity.this.progress % 22;
			//
			// return;
			// MainActivity.this.progress = (2 + MainActivity.this.progress);
			// continue;
			// if (MainActivity.this.progress == 0)
			// continue;
			// MainActivity localMainActivity1 = MainActivity.this;
			// localMainActivity1.progress = (-2 +
			// localMainActivity1.progress);
			// }
		}
	};
	
	private void showLockDialog() {
		 AlertDialog dialog = null;
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("BROON");
	        builder.setMessage("Do you want to lock?");
	        builder.setPositiveButton("YES", new OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	startActivity(new Intent(MainActivity.this, LockActivity.class));
	            }
	        });
	        builder.setNegativeButton("NO", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (dialog != null) {
	                    dialog.dismiss();
	                }
				}
			});
	        dialog = builder.create();
	        dialog.show();
	}

}
