package com.supernovamobile.smashout;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;

public class Group {

	protected Vector<Group> mChildren = new Vector<Group>();
	protected String mName = "" + this.hashCode();
	
	protected float mX = 0;
	protected float mY = 0;
	protected float mZ = 0;
	
	protected float mRx = 0;
	protected float mRy = 0;
	protected float mRz = 0;
	
	public void draw(GL10 gl) throws IOException {
		int size = mChildren.size();
		for (int i = 0; i < size; i++) {
			mChildren.get(i).draw(gl);
		}
	}
	
	public void add(int location, Group group) {
		mChildren.add(location, group);
	}
	
	public boolean add(Mesh mesh) {
		return mChildren.add(mesh);
	}
	
	public boolean add(Group group) {
		return mChildren.add(group);
	}
	
	public void clear() {
		mChildren.clear(); 
 	}
	
	public Group get(int location) {
		return mChildren.get(location);
	}
		
	public Group remove(int location) {
		return mChildren.remove(location);
	}
	
	public boolean remove(Group group) {
		return mChildren.remove(group);
	}
	
	public int size() {
		return mChildren.size();
	}
	
	@Override
	public String toString() {
		return mName;
	}

	protected void loadTextures(GL10 gl, Context context) throws IOException {
		int size = mChildren.size();
		for (int i = 0; i < size; i++) {
			mChildren.get(i).loadTextures(gl, context);
		}
	}
	
	public void setX(float x) {
		mX = x;
	}
	
	public float getX() {
		return mX;
	}
	
	public void setY(float y) {
		mY = y;
	}
	
	public float getY() {
		return mY;
	}
	
	public void setZ(float z) {
		mZ = z;
	}
	
	public float getZ() {
		return mZ;
	}
	
	public void setRx(float rx) {
		mRx = rx;
	}
	
	public float getRx() {
		return mRx;
	}
	
	public void setRy(float ry) {
		mRy = ry;
	}
	
	public float getRy() {
		return mRy;
	}
	
	public void setRz(float rz) {
		mRz = rz;
	}
	
	public float getRz() {
		return mRz;
	}
}