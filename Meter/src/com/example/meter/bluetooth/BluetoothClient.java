package com.example.meter.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 蓝牙连接 Client端 管理
 *
 */
public class BluetoothClient {
	private static final String TAG = "yzh";
	protected static final String CONNECT_FAILED = null;
	protected static final String EXTRA_DEVICE_ADDRESS = "device_address";
	private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private final String lockName = "takee 1";
	private final String LOCK_ADDRESS = "24:76:89:23:A5:FA";
	private BluetoothAdapter mBluetoothAdapter;
	private Context mContext = null;
	public BluetoothDevice device1;
	private BluetoothDevice mBluetoothDevice;
	private BluetoothSocket mBluetoothSocket;
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private boolean mStop;
	private InputStream mmInStream;
	private OutputStream mmOutStream;
	private ConnectThread mConnectThread;

	static BluetoothDevice remoteDevice;
	
	public BluetoothClient(Context context) {
		mContext = context;
	}

	public void search() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.enable();
		}

		// 扫描附近设备
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}
		mBluetoothAdapter.startDiscovery();// 搜索附近设备
		Log.d(TAG, "searching bluetooth...");

		// 注册扫描结果广播
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mContext.registerReceiver(mBluetoothReceiver, filter);
		Log.d(TAG, "register Bluetooth Receiver");

//		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//		mContext.registerReceiver(mBluetoothReceiver, filter);
//		Log.d(TAG, "注册扫描结果广播,ACTION_DISCOVERY_FINISHED");
		// onDestroy();
	}

	public void release() {
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.cancelDiscovery();
		}
		cancelConnect();
		if(mBluetoothSocket != null) {
			mContext.unregisterReceiver(mBluetoothReceiver);
		}
		
		Log.d(TAG, "release--Bluetooth cancelDiscovery & unregister Bluetooth Receiver ");
	}

	// 当发现新蓝牙设备后扫描完成广播
	private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "action  = " + action.toString());
			// 发现设备
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				mBluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.d(TAG, "$$$$$ find BluetoothDevice name = " + mBluetoothDevice.getName() + " address = " + mBluetoothDevice.getAddress());
				// 添加非配对设备
				if (isLock(mBluetoothDevice)) {
					((BluetoothActivity)mContext).updateBluetoothStatue(); 
					mConnectThread = new ConnectThread(); //开启连接线程
					mConnectThread.start();
				}
			}
		}
	};

	private boolean isLock(BluetoothDevice device) {
		boolean isLockName = (lockName.equals(device.getName())) || (LOCK_ADDRESS.equals(device.getAddress())) ;
//		boolean isLockName = (LOCK_ADDRESS.equals(device.getAddress()));
		return isLockName;
	}

	private BluetoothSocket fallbackSocket;
	class ConnectThread extends Thread {
		@Override
		public void run() {
			try {
				Log.d(TAG, "ConnectThread start...");
				mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
				mBluetoothAdapter.cancelDiscovery(); //连接蓝牙服务端时,取消搜索,加快连接成功
//				mBluetoothSocket.connect();
//				((MainActivity)mContext).startReadBluetoochThread();
				
				Class<?> clazz = mBluetoothSocket.getRemoteDevice().getClass();
				Class<?>[] paramTypes = new Class<?>[] {Integer.TYPE};

				Method m;
				try {
					m = clazz.getMethod("createRfcommSocket", paramTypes);
					Object[] params = new Object[] {Integer.valueOf(1)};

					fallbackSocket = (BluetoothSocket) m.invoke(mBluetoothSocket.getRemoteDevice(), params);
					Thread.sleep(500);
					fallbackSocket.connect();
					Log.d(TAG, "BluetoothSocket client connect success!");
					
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				Log.d(TAG,"ConnectThread e =" + e.toString());
				cancelConnect();
			}
			
			try {
				if(mBluetoothSocket != null) {
					mInputStream = mBluetoothSocket.getInputStream();
					mOutputStream = mBluetoothSocket.getOutputStream();
					mOutputStream.write(0);
				}
			} catch (IOException e2) {
				e2.printStackTrace();
				Log.e(TAG, "$$$ ConnectThread e2 =" + e2.toString());
			}
		}
	}
	
	public InputStream getInputStream() {
		return mInputStream;
	}
	
	public OutputStream getOutputStream() {
		return mOutputStream;
	}
	
	public void cancelConnect() {
		if(mBluetoothSocket != null) {
			try {
				mBluetoothSocket.close();
				mBluetoothSocket = null;
				mConnectThread.interrupt();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, "cancelConnect e = " + e.toString());
			}
		}
	}
}
