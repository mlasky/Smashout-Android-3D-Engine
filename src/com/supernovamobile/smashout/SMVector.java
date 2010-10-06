package com.supernovamobile.smashout;

public class SMVector {
	
	public float mX;
	public float mY;
	public float mZ;
	
	public SMVector(float x, float y, float z) {
		mX = x;
		mY = y;
		mZ = z;
	}
	
	public static SMVector add(SMVector a, SMVector b) {
		float x = a.mX + b.mX;
		float y = a.mY + b.mY;
		float z = a.mZ + b.mZ;
		
		return new SMVector(x, y, z);
	}
	
	public static SMVector subtract(SMVector a, SMVector b) {
		float x = a.mX + (b.mX * -1);
		float y = a.mY + (b.mY * -1);
		float z = a.mZ + (b.mZ * -1);
		
		return new SMVector(x, y, z);
	}
}