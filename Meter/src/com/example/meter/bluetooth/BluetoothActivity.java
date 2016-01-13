package com.example.meter.bluetooth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meter.R;

public class BluetoothActivity extends Activity {
	public static final String TAG = "yzh";
	
	private final static String UUID_KEY_DATA = "00002a00-0000-1000-8000-00805f9b34fb";
	
	private TextView mTVTile;
	private EditText mETCMD;
	private Button mBtnGetService;
	private Button mBTNSend;
	private Button mButtonGetStatus;
	private Button mBtnReceive;
	private TextView mTVReceive;
	
	private BluetoothClient mBluetoothClient;
	
	private BluetoothLeClient mBluetoothLeClient;
	private BluetoothLeService mBluetoothLeService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blue_tooth_activity);
		
		mTVTile = (TextView) findViewById(R.id.tv_title);
		mETCMD = (EditText) findViewById(R.id.et_cmd);
		mBtnGetService = (Button) findViewById(R.id.btn_getService);
		mBTNSend = (Button) findViewById(R.id.btn_send);
		mBtnReceive = (Button)findViewById(R.id.btn_receive);
		mTVReceive = (TextView) findViewById(R.id.tv_receive);
		mButtonGetStatus = (Button) findViewById(R.id.btn_getstatus);
		
		setViewListener();
		
//		mBluetoothClient = new BluetoothClient(this);
//		mBluetoothClient.search();
		
		mBluetoothLeClient = new BluetoothLeClient(this);
		mBluetoothLeClient.bindService();
		
		YLog.i("*****onCreate*****");
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mReadBluetoothThreadFlag = true;
		YLog.i("*****onResume*****");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mReadBluetoothThreadFlag = false; // 读取蓝牙输入线程退出
//		mBluetoothClient.release();
		YLog.i("*****onPause*****");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBluetoothLeClient.unbindService();
		mBluetoothLeClient = null;
		
		YLog.i("*****onDestroy*****");
	}
	
	List<BluetoothGattService> gattServices;
	
	BluetoothGattCharacteristic characteristic_r; //
	BluetoothGattCharacteristic characteristic_w;
	
	private void setViewListener() {
		
		mBtnGetService.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBluetoothLeService = mBluetoothLeClient.getBluetoothLeService();
				gattServices = mBluetoothLeService.getSupportedGattServices();
				displayGattServices(gattServices);
			}
		});
		
		mBTNSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				String cmd = mETCMD.getText().toString();
//				byte[] buffer = cmd.getBytes();
//				try {
//					if(mBluetoothClient != null && mBluetoothClient.getOutputStream() != null) {
//						mBluetoothClient.getOutputStream().write(buffer);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				if(characteristic_w != null) {
					//接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
        			mBluetoothLeService.setCharacteristicNotification(characteristic_w, true);
        			//设置数据内容
        			characteristic_w.setValue("abcdef");
        			//往蓝牙模块写入数据
        			mBluetoothLeService.writeCharacteristic(characteristic_w);
				}
			}
		}); 
		

		//00001c01-d102-11e1-9b23-000efb0000a6
		
		mBtnReceive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 
				mBluetoothLeService = mBluetoothLeClient.getBluetoothLeService();
				mBluetoothLeService.readCharacteristic(characteristic_r);
				mTVReceive.setText("TRUSTED-LINK-DATA-002");
				YLog.d("receive button");
			}
		});
		
		mButtonGetStatus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBluetoothLeClient.getConnectStatus();
			}
		});
	}
	
	
	private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;

        for (BluetoothGattService gattService : gattServices) {
        	//-----Service的字段信息-----//
        	int type = gattService.getType();
            Log.e(TAG,"*->service type:"+Utils.getServiceType(type));
            Log.e(TAG,"*->includedServices size:"+gattService.getIncludedServices().size());
            Log.e(TAG,"*->service uuid:"+gattService.getUuid());
            Log.e(TAG,"*->include Characteristic:" + gattService.getCharacteristics().size());
            
            //-----Characteristics的字段信息-----//
            List<BluetoothGattCharacteristic> gattCharacteristics =gattService.getCharacteristics();
            for (final BluetoothGattCharacteristic  gattCharacteristic: gattCharacteristics) {
                Log.e(TAG,"----->>char uuid:"+gattCharacteristic.getUuid());
                
                int permission = gattCharacteristic.getPermissions();
                Log.e(TAG,"----->>char permission:"+Utils.getCharPermission(permission));
                
                int property = gattCharacteristic.getProperties();
                Log.e(TAG,"----->>char property:"+Utils.getCharPropertie(property));

                byte[] data = gattCharacteristic.getValue();
        		if (data != null && data.length > 0) {
        			Log.e(TAG,"----->>char value:" + new String(data));
        		}else {
        			Log.e(TAG,"----->>char value: data = " + data);
        		}

        		/*
        		//UUID_KEY_DATA是可以跟蓝牙模块串口通信的Characteristic
        		if(gattCharacteristic.getUuid().toString().equals(UUID_KEY_DATA)){        			
        			//测试读取当前Characteristic数据，会触发mOnDataAvailable.onCharacteristicRead()
        			mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        	mBluetoothLeService.readCharacteristic(gattCharacteristic);
                        }
                    }, 500);
        			
        			//接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
        			mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
        			//设置数据内容
        			gattCharacteristic.setValue("send data->");
        			//往蓝牙模块写入数据
        			mBluetoothLeService.writeCharacteristic(gattCharacteristic);
        		}
        		// */
        		
        		if(gattCharacteristic.getUuid().toString().equals("00002a25-0000-1000-8000-00805f9b34fb")) {
        			characteristic_r = gattCharacteristic;
        			YLog.d("get characteristic---");
        		} 
        		
        		if(gattCharacteristic.getUuid().toString().equals("00001c01-d102-11e1-9b23-000efb0000a6")) {
        			characteristic_w = gattCharacteristic;
        			YLog.d("get characteristic---");
        		}
        		
        		Log.e(TAG,"----->>include Descriptors: " + gattCharacteristic.getDescriptors().size());
        		//-----Descriptors的字段信息-----//
				List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic.getDescriptors();
				for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
					Log.e(TAG, "************>>>desc uuid:" + gattDescriptor.getUuid());
					int descPermission = gattDescriptor.getPermissions();
					Log.e(TAG,"************>>>desc permission:"+ Utils.getDescPermission(descPermission));
					
					byte[] desData = gattDescriptor.getValue();
					if (desData != null && desData.length > 0) {
						Log.e(TAG, "************>>>desc value:"+ new String(desData));
					}
        		 }
            }
            
            YLog.i("=========================================================================================================");
            YLog.i("=========================================================================================================");
        }//

    }
	
	
	
	/** 更新蓝牙连接状态 */
	public void updateBluetoothStatue() {
		if (mTVTile != null) {
			mTVTile.setText("蓝牙通讯: ----蓝牙已连接----");
		}
	}
	
	/** 蓝牙读取线程标记 */
	private boolean mReadBluetoothThreadFlag = true;
	private char mReadData = 'T';
	private byte[] mReadDatas = new byte[20];

	/** 蓝牙读取线程 */
	class ReadBluetoochThread extends Thread {
		@Override
		public void run() {
			Log.d(TAG, " ReadBluetoothThread start ... mBluetoothClient = " + mBluetoothClient
					+ " mBluetoothClient.getInputStream() = "	+ mBluetoothClient.getInputStream());
			while (mReadBluetoothThreadFlag) {
				if (mBluetoothClient != null && mBluetoothClient.getInputStream() != null) {
					try {
						Log.d(TAG, " Read Begin.... ");
//						mReadData = (char) mBluetoothClient.getInputStream().read(); mReadDatas
						mBluetoothClient.getInputStream().read(mReadDatas,0,mReadDatas.length);
						Log.d(TAG, "===" + Arrays.toString(mReadDatas));
						mHandler.sendMessage(mHandler.obtainMessage(1, mReadDatas));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void startReadBluetoochThread() {
		new ReadBluetoochThread().start();
	}
	
	private Handler mHandler = new Handler() {
		private byte[] readDatas = new byte[10];
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				readDatas = (byte[])msg.obj; 
				mTVReceive.setText(Arrays.toString(mReadDatas));
				break;

			default:
				break;
			}
		}
	};
}
