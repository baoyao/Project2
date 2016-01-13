package com.example.meter.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

public class BluetoothLeClient {
	private final String LOCK_ADDRESS = "24:76:89:23:A5:FA";
	private Context mContext;
	private BluetoothLeService mBluetoothLeService;
	private BluetoothLeService.LocalBinder mLocatBinder;
	final BluetoothManager bluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;

	private Handler mHandler;
	private Boolean mScanning;
	

	private int SCAN_PERIOD = 10000;

	public BluetoothLeClient(Context context) {
		mContext = context;
		bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		mHandler = new Handler();
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mLocatBinder = null;
			YLog.d("onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mLocatBinder = (BluetoothLeService.LocalBinder)service;
			mBluetoothLeService = mLocatBinder.getService();
			mBluetoothLeService.initialize();
			scanLeDevice(true);
			YLog.d("onServiceConnected");
		}
	};
	//绑定服务
	public void bindService() {
		Intent intent = new Intent(mContext,BluetoothLeService.class);
		mContext.bindService(intent, connection, Service.BIND_AUTO_CREATE);
		YLog.d("bind Service");
	}
	//解除绑定
	public void unbindService() {
		mContext.unbindService(connection);
	}
	
	public BluetoothLeService getBluetoothLeService() {
		return mBluetoothLeService;
	}
	
	public void scanLeDevice(final boolean enable) {
		if (enable) {
			// 经过预定扫描期后停止扫描
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
				}
			}, SCAN_PERIOD);

			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			//device.connectGatt(mContext, false, mGattCallback);
			YLog.d("name = " + device.getName());
			YLog.d("address = " + device.getAddress());
			YLog.d("type = " + device.getType());
			if(device.getAddress().equals(LOCK_ADDRESS)) {
				mBluetoothLeService.connect(LOCK_ADDRESS);
				YLog.d("connect");
			}
		}
	};
	
	public void getConnectStatus() {
		if(mBluetoothAdapter != null) {
			int ret = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);
			YLog.d("ret = " + ret);
		}
	}
	
}
