package com.example.meter.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import java.util.List;
import java.util.UUID;

public class BluetoothLeService extends Service {
	public static final String ACTION_DATA_NOTIFY = "ti.android.ble.common.ACTION_DATA_NOTIFY";
	public static final String ACTION_DATA_READ = "ti.android.ble.common.ACTION_DATA_READ";
	public static final String ACTION_DATA_WRITE = "ti.android.ble.common.ACTION_DATA_WRITE";
	public static final String ACTION_GATT_CONNECTED = "ti.android.ble.common.ACTION_GATT_CONNECTED";
	public static final String ACTION_GATT_DISCONNECTED = "ti.android.ble.common.ACTION_GATT_DISCONNECTED";
	public static final String ACTION_GATT_SERVICES_DISCOVERED = "ti.android.ble.common.ACTION_GATT_SERVICES_DISCOVERED";
	public static final String EXTRA_ADDRESS = "ti.android.ble.common.EXTRA_ADDRESS";
	public static final String EXTRA_DATA = "ti.android.ble.common.EXTRA_DATA";
	public static final String EXTRA_STATUS = "ti.android.ble.common.EXTRA_STATUS";
	public static final String EXTRA_UUID = "ti.android.ble.common.EXTRA_UUID";
	static final String TAG = "BluetoothLeService";
	private static BluetoothLeService mThis = null;
	private final IBinder binder = new LocalBinder();
	private String mBluetoothDeviceAddress;
	private BluetoothGatt mBluetoothGatt = null;
	private BluetoothManager mBluetoothManager = null;
	private BluetoothAdapter mBtAdapter = null;
	private volatile boolean mBusy = false;
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) { 
		Log.i("BluetoothLeService", "Received start id " + startId + ": "	+ intent);
		return 1;
	}
	
    @Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		close();
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("BluetoothLeService", "onDestroy() called");
		if (mBluetoothGatt != null) {
			mBluetoothGatt.close();
			mBluetoothGatt = null;
		}
	}
	
	public boolean initialize() {
		Log.d("BluetoothLeService", "initialize");
		mThis = this;
		if (mBluetoothManager == null) {
			mBluetoothManager = ((BluetoothManager) getSystemService("bluetooth"));
			if (mBluetoothManager == null) {
				Log.e("BluetoothLeService", "Unable to initialize BluetoothManager.");
				return false;
			}
		}
		mBtAdapter = mBluetoothManager.getAdapter();
		if (mBtAdapter == null) {
			Log.e("BluetoothLeService", "Unable to obtain a BluetoothAdapter.");
			return false;
		}
		return true;
	}
	
	private BluetoothGattCallback mGattCallbacks = new BluetoothGattCallback() {

		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			if (gatt == null) {
				Log.e("BluetoothLeService", "mBluetoothGatt not created!");
				return;
			}
			String str = gatt.getDevice().getAddress(); 
			Log.d("BluetoothLeService", "onConnectionStateChange (" + str + ") " + " newState: " + newState + " status: " + status);
			switch (newState) {
			case BluetoothProfile.STATE_CONNECTING:
				broadcastUpdate(ACTION_GATT_CONNECTED, str, status);
				break;
				
			case BluetoothProfile.STATE_CONNECTED:
				// Attempts to discover services after successful connection.
				Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
				broadcastUpdate(ACTION_GATT_CONNECTED, str, status);
				break;
				
			case BluetoothProfile.STATE_DISCONNECTED:
				broadcastUpdate(ACTION_GATT_DISCONNECTED, str, status);
				break;
				
			default:
				try {
					Log.e("BluetoothLeService", "New state not processed: "	+ newState);
					return;
				} catch (NullPointerException localNullPointerException) {
					localNullPointerException.printStackTrace();
					return;
				}
			}
		}
		
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			Log.i("BluetoothLeService", "onServicesDiscovered status :" + status);
			BluetoothDevice localBluetoothDevice = gatt.getDevice();
			broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED, localBluetoothDevice.getAddress(), status);
		}
		
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
	                Log.e(TAG,"----->>char uuid:"+characteristic.getUuid());
	                
	                int permission = characteristic.getPermissions();
	                Log.e(TAG,"----->>char permission:"+Utils.getCharPermission(permission));
	                
	                int property = characteristic.getProperties();
	                Log.e(TAG,"----->>char property:"+Utils.getCharPropertie(property));

	                byte[] data = characteristic.getValue();
	        		if (data != null && data.length > 0) {
	        			Log.e(TAG,"----->>char value:" + new String(data));
	        		}else {
	        			Log.e(TAG,"----->>char value: data = " + data);
	        		}
			broadcastUpdate(ACTION_DATA_READ, characteristic, status);
			Log.i("BluetoothLeService", "onCharacteristicRead");
		}
		
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			broadcastUpdate(ACTION_DATA_WRITE, characteristic, status);
			Log.i("BluetoothLeService", "onCharacteristicWrite");
		}
		
		
		public void onCharacteristicChanged(BluetoothGatt gatt,	BluetoothGattCharacteristic characteristic) {
			broadcastUpdate(ACTION_DATA_NOTIFY,	characteristic, 0);
			Log.i("BluetoothLeService", "onCharacteristicChanged");
		}

		public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor characteristic, int status) {
			Log.i("BluetoothLeService", "onDescriptorRead");
		}

		public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor characteristic, int status) {
			Log.i("BluetoothLeService", "onDescriptorWrite");
		}
		
		public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
			Log.i("BluetoothLeService", "onReliableWriteCompleted");
	    }
		
	};

	private void broadcastUpdate(String action,	BluetoothGattCharacteristic characteristic, int status) {
		Intent localIntent = new Intent(action);
		localIntent.putExtra(EXTRA_UUID, characteristic.getUuid().toString());
		localIntent.putExtra(EXTRA_DATA, characteristic.getValue());
		localIntent.putExtra(EXTRA_STATUS, status);
		sendBroadcast(localIntent);
		this.mBusy = false;
	}

	private void broadcastUpdate(String action, String address, int status) {
		Intent localIntent = new Intent(action);
		localIntent.putExtra(EXTRA_ADDRESS, address);
		localIntent.putExtra(EXTRA_STATUS, status);
		sendBroadcast(localIntent);
		this.mBusy = false;
	}

	private boolean checkGatt() {
		if (mBtAdapter == null) {
			Log.w("BluetoothLeService", "BluetoothAdapter not initialized");
			return false;
		}
		if (mBluetoothGatt == null) {
			Log.w("BluetoothLeService", "BluetoothGatt not initialized");
			return false;
		}
//		if (mBusy) {
//			Log.w("BluetoothLeService", "LeService busy");
//			return false;
//		}
		return true;
	}

	public static BluetoothGatt getBtGatt() {
		return mThis.mBluetoothGatt;
	}

	public static BluetoothManager getBtManager() {
		return mThis.mBluetoothManager;
	}

	public static BluetoothLeService getInstance() {
		return mThis;
	}
	
	public boolean connect(String address) {
		if (mBtAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBtAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallbacks);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        return true;
	}
	
	public void disconnect(String paramString) {
		int i;
		if (mBtAdapter == null) {
			Log.w("BluetoothLeService",	"disconnect: BluetoothAdapter not initialized");
			return;
		}
		
		BluetoothDevice localBluetoothDevice = mBtAdapter.getRemoteDevice(paramString);
		i = mBluetoothManager.getConnectionState(localBluetoothDevice,	BluetoothProfile.GATT);
		Log.i("BluetoothLeService", "disconnect");
		if (i != 0) {
			mBluetoothGatt.disconnect();
			mBluetoothGatt = null;
			return;
		}
		Log.w("BluetoothLeService", "Attempt to disconnect in state: " + i);
	}
	
	/**关闭gatt连接*/
	public void close() {
		if (mBluetoothGatt != null) {
			Log.i("BluetoothLeService", "close");
			mBluetoothGatt.close();
			mBluetoothGatt = null;
		}
	}

	public int getNumServices() {
		if (mBluetoothGatt == null)
			return 0;
		return mBluetoothGatt.getServices().size();
	}

	public List<BluetoothGattService> getSupportedGattServices() {
		if (mBluetoothGatt == null)
			return null;
		return mBluetoothGatt.getServices();
	}

	public static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
	public boolean isNotificationEnabled(BluetoothGattCharacteristic characteristic) {
		
		BluetoothGattDescriptor localBluetoothGattDescriptor;
		if (!(checkGatt())) {
			return false;
		}
		
		localBluetoothGattDescriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
		if ((localBluetoothGattDescriptor == null) || (localBluetoothGattDescriptor.getValue() != BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)){
			return true;
		}
		return false;
	}

	public int numConnectedDevices() {
		BluetoothGatt localBluetoothGatt = mBluetoothGatt;
		int i = 0;
		if (localBluetoothGatt != null)
			i = mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT).size();
		return i;
	}
	
	

	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (!(checkGatt())) {
			return;
		}
		mBusy = true;
		mBluetoothGatt.readCharacteristic(characteristic);
	}

	public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic,boolean enabled) {
		BluetoothGattDescriptor localBluetoothGattDescriptor;
		if (!(checkGatt())) {
			return false;
		}
		
		if (!(mBluetoothGatt.setCharacteristicNotification(characteristic, enabled))) {
			Log.w("BluetoothLeService",	"setCharacteristicNotification failed");
			return false;
		}
		localBluetoothGattDescriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
		if(localBluetoothGattDescriptor != null) {
			if (enabled) {
				Log.i("BluetoothLeService", "enable notification");
				mBusy = true;
				localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
				return mBluetoothGatt.writeDescriptor(localBluetoothGattDescriptor);
			}else {
				mBusy = true;
				localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
				return mBluetoothGatt.writeDescriptor(localBluetoothGattDescriptor);
			}
			
		}
		return false;
	}

//	public boolean waitIdle(int paramInt) {
//		int i = paramInt / 10;
//		if (--i <= 0) {
//			return false;
//		}
//			
//		do {
//			if (i <= 0)
//				return false;
//			
//			try {
//				Thread.sleep(10L);
//			} catch (InterruptedException localInterruptedException) {
//				localInterruptedException.printStackTrace();
//			}
//			return true;
//		} while (!mBusy);
//		
//	}

	public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (!(checkGatt()))
			return false;
		mBusy = true;
		return mBluetoothGatt.writeCharacteristic(characteristic);
	}

	public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic,byte paramByte) {
		if (!(checkGatt()))
			return false;
		characteristic.setValue(new byte[] { paramByte });
		mBusy = true;
		return mBluetoothGatt.writeCharacteristic(characteristic);
	}

	public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic, boolean paramBoolean) {
		int i;
		if (!(checkGatt()))
			return false;
		byte[] arrayOfByte = new byte[1];
		if (paramBoolean) {
			i = 1;
			arrayOfByte[0] = (byte) i;
			characteristic.setValue(arrayOfByte);
			mBusy = true;
			return mBluetoothGatt.writeCharacteristic(characteristic);
			
		}else {
			i = 0;
			arrayOfByte[0] = (byte) i;
			characteristic.setValue(arrayOfByte);
			mBusy = true;
			return mBluetoothGatt.writeCharacteristic(characteristic);
		}
	}

	class LocalBinder extends Binder {
		public BluetoothLeService getService() {
			return BluetoothLeService.this;
		}
	}
}