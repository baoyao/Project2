package com.example.meter.util;

public class SendCommandUtil {

	/**包头1*/
	private static final byte CMD_HEAD1 = (byte)0x5a;
	/**包头2*/
	private static final byte CMD_HEAD2 = (byte) 0xa5;

	/**遥控*/
	public static final byte CMD_REMOTE = (byte) 0x01;
	/**手动*/
	public static final byte CMD_MANUAL = (byte) 0x02;

	/**停止*/
	public static final byte CMD_STOP = (byte) 0x00;
	/**前进*/
	public static final byte CMD_FORWARD = (byte) 0x01;
	/**后退*/
	public static final byte CMD_BACK = (byte) 0x02;
	
	private static SendCommandUtil sendCommandUtil;
	
	private SendCommandUtil(){
		
	}
	
	public static SendCommandUtil getInstance(){
		if(sendCommandUtil==null){
			sendCommandUtil =new SendCommandUtil();
		}
		return sendCommandUtil;
	}
	

	/**
	 * 打包指令
	 * @param command[命令]
	 * @param params[参数]
	 * @return
	 */
	private byte[] packCommandData(byte command, byte[] params) {
		if (command == 0 || params == null || params.length == 0) {
			return null;
		}
		int paramSize = params.length;
		byte[] data = new byte[(paramSize + 6)];
		data[0] = CMD_HEAD1;// ①包头
		data[1] = CMD_HEAD2;// ②包头

		data[2] = (byte) (paramSize + 1);// ③命令长度
		data[3] = command;// ④命令
		for (int i = 4, j = 0; j < paramSize; i++, j++) {
			data[i] = params[j];// ⑤命令参数
		}
		
		int checkValue = 0;
		for (int i = 0; i < paramSize + 4; i++) {
			checkValue = checkValue + data[i];
		}
		
		checkValue = checkValue & 0xff;
		checkValue = ~checkValue;// 取反
		// checkValue = checkValue + 1;//加一
		data[paramSize + 4] = (byte) checkValue;// ⑥校验值
		return data;
	}
	
	
	
	public class Remote extends Base{

		/**
		 * 停止指令
		 * [包头,包头,长度,命令,参数1,参数2,校验值]
		 * [0x5a,0xa5,3,0x00,0x01,0x00,校验值]
		 */
		@Override
		public void sendStopCommand(){
			// TODO Auto-generated method stub
			byte[] cmd=packCommandData((byte)0x00, new byte[]{CMD_REMOTE,CMD_STOP});
			super.sendCommand(cmd);
		}

		@Override
		public void sendForwardCommand() {
			// TODO Auto-generated method stub
			byte[] cmd=packCommandData((byte)0x00, new byte[]{CMD_REMOTE,CMD_FORWARD});
			super.sendCommand(cmd);
		}

		@Override
		public void sendBackCommand() {
			// TODO Auto-generated method stub
			byte[] cmd=packCommandData((byte)0x00, new byte[]{CMD_REMOTE,CMD_BACK});
			super.sendCommand(cmd);
		}

	}
	
	public class Manual extends Base{

		@Override
		public void sendStopCommand() {
			// TODO Auto-generated method stub
			byte[] cmd=packCommandData((byte)0x00, new byte[]{CMD_MANUAL,CMD_STOP});
			super.sendCommand(cmd);
		}
		
		@Override
		public void sendForwardCommand() {
			// TODO Auto-generated method stub
			byte[] cmd=packCommandData((byte)0x00, new byte[]{CMD_MANUAL,CMD_STOP});
			super.sendCommand(cmd);
		}

		@Override
		public void sendBackCommand() {
			// TODO Auto-generated method stub
			byte[] cmd=packCommandData((byte)0x00, new byte[]{CMD_MANUAL,CMD_FORWARD});
			super.sendCommand(cmd);
		}
		
	}
	
	
	public abstract class Base implements Standard{
		void sendCommand(byte[] commands){
			//发送指令
		}
	}
	
	interface Standard{
		void sendStopCommand();
		void sendForwardCommand();
		void sendBackCommand();
	}

}
