package de.cmlab.ubicomp.lib.model;

public class AndroidSensor {
	private float[] accel;
	private float[] gravity;
	private float[] rotation;
	private float[] orientation;
	private float ambientlight;
	private float proximity;
	private boolean[] touchedButtons;
	String sensorId;
	
	public AndroidSensor(String sensorId){
		accel = new float[3];
		gravity = new float[3];
		rotation = new float[3];
		orientation = new float[3];
		ambientlight = 0;
		proximity = 0;
		touchedButtons = new boolean[8];
		this.sensorId = sensorId;
	}

	/**
	 * @return the accel
	 */
	public float[] getAcceleration() {
		return accel;
	}

	/**
	 * @param accel the accel to set
	 */
	public void setAcceleration(float[] accel) {
		this.accel = accel;
	}

	/**
	 * @return the gravity
	 */
	public float[] getGravity() {
		return gravity;
	}

	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(float[] gravity) {
		this.gravity = gravity;
	}

	/**
	 * @return the rotation
	 */
	public float[] getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float[] rotation) {
		this.rotation = rotation;
	}

	/**
	 * @return the orientation
	 */
	public float[] getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(float[] orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the ambientlight
	 */
	public float getAmbientlight() {
		return ambientlight;
	}

	/**
	 * @param ambientlight the ambientlight to set
	 */
	public void setAmbientlight(float ambientlight) {
		this.ambientlight = ambientlight;
	}

	/**
	 * @return the proximity
	 */
	public float getProximity() {
		return proximity;
	}

	/**
	 * @param proximity the proximity to set
	 */
	public void setProximity(float proximity) {
		this.proximity = proximity;
	}

	/**
	 * @return the touchedButtons
	 */
	public boolean[] getTouchedButtons() {
		return touchedButtons;
	}

	/**
	 * @param touchedButtons the touchedButtons to set
	 */
	public void setTouchedButtons(boolean[] touchedButtons) {
		this.touchedButtons = touchedButtons;
	}
	
	public String toString(){
		String IDString = String.format("Sensor ID: %s",sensorId);
		String AccelString = String.format("Acceleration: X:%f,Y:%f,Z:%f", accel[0],accel[1],accel[2]);
		String GravityString = String.format("Gravity: X:%f,Y:%f,Z:%f", gravity[0],gravity[1],gravity[2]);
		String RotationString = String.format("Rotation: X:%f,Y:%f,Z:%f", rotation[0],rotation[1],rotation[2]);
		String OrientationString = String.format("Orientation: X:%f,Y:%f,Z:%f", orientation[0],orientation[1],orientation[2]);
		String AmbientString = String.format("AmbientLight: %f", ambientlight);
		String ProximityString = String.format("Proximity: %f", proximity);
		String TouchedButtonsString = String.format("TouchedButtons 1:%s, 2:%s, 3:%s, 4:%s, 5:%s,"
				+ " 6:%s, 7:%s, 8:%s", touchedButtons[0], touchedButtons[1],touchedButtons[2]
						,touchedButtons[3],touchedButtons[4],touchedButtons[5],touchedButtons[6],
						touchedButtons[7]);
		return IDString+"\n"+AccelString+"\n"+GravityString+"\n"+RotationString+"\n"+OrientationString+"\n"+
				AmbientString +"\n"+ProximityString+"\n"+TouchedButtonsString;
	}
	
	

}
