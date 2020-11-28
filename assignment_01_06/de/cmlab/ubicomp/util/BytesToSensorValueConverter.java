package de.cmlab.ubicomp.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BytesToSensorValueConverter {
		
	public static float[] extractAccelData(byte[] receivedData){
		float[] accel = extractingData(0, 11, receivedData,4);
        return accel;
	}
	
	public static float[] extractGravityData(byte[] receivedData){
		float[] gravity = extractingData(12, 23, receivedData,4);
        return gravity;
	}
	
	public static float[] extractRotationData(byte[] receivedData){
		float[] rotation = extractingData(24, 35, receivedData,4);
        return rotation;
	}
	
	public static float[] extractOrientationData(byte[] receivedData){
		float[] orientation = extractingData(36, 47, receivedData,4);
        return orientation;
	}
	
	public static float extractAmbientLightData(byte[] receivedData){
		float[] light = extractingData(56, 59, receivedData,4);
        return light[0];
	}
	
	public static float extractProximityData(byte[] receivedData){
		float[] proximity = extractingData(60, 63, receivedData,4);
        return proximity[0];
	}
	
	public static boolean[] extractTouchedButtons(byte[] receivedData){
		boolean[] TouchedButtons = new boolean[8];
		float[] touch1 = extractingData(64, 67, receivedData,4);
		float[] touch2 = extractingData(68, 71, receivedData,4);
		float[] touch3 = extractingData(72, 75, receivedData,4);
		float[] touch4 = extractingData(76, 79, receivedData,4);
		float[] touch5 = extractingData(80, 83, receivedData,4);
		float[] touch6 = extractingData(84, 87, receivedData,4);
		float[] touch7 = extractingData(88, 91, receivedData,4);
		float[] touch8 = extractingData(92, 95, receivedData,4);
		
		TouchedButtons[0] = (touch1[0] > 0) ? true :false;
		TouchedButtons[1] = (touch2[0] > 0) ? true :false;
		TouchedButtons[2] = (touch3[0] > 0) ? true :false;
		TouchedButtons[3] = (touch4[0] > 0) ? true :false;
		TouchedButtons[4] = (touch5[0] > 0) ? true :false;
		TouchedButtons[5] = (touch6[0] > 0) ? true :false;
		TouchedButtons[6] = (touch7[0] > 0) ? true :false;
		TouchedButtons[7] = (touch8[0] > 0) ? true :false;
        return TouchedButtons;
	}
	
	private static float[] extractingData(int startindex, int endindex, byte[] receivedData, int step )
	{
		int counter = 0;
		int bigCounter = 0;
		byte[] packet = new  byte[4];
		double size = (((float)endindex-(float)startindex)+1)/4;
		int resultsize = (int)Math.ceil(size);
		float[] result = new float[resultsize];
		
		for(int i=startindex; i<endindex+1; i++){
			if(counter < step-1){
				packet[counter] = receivedData[i];
				counter++;
			}else{
				float num = ByteBuffer.wrap(packet).order(ByteOrder.BIG_ENDIAN).getFloat();
				result[bigCounter] = num;
				counter = 0;
				packet = new  byte[4];
				bigCounter++;
			}
		}
		return result;
	}
}
