package com.supernovamobile.smashout;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Group {

	protected Vector<Group> mChildren = new Vector<Group>();
	protected String mName = "" + this.hashCode();
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	
	public float rx = 0;
	public float ry = 0;
	public float rz = 0;
	
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
}