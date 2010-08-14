package com.supernovamobile.smashout;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Group extends Mesh {
	protected Vector<Mesh> mChildren = new Vector<Mesh>();
	
	@Override
	public void draw(GL10 gl) {
		int size = mChildren.size();
		for (int i = 0; i < size; i++) {
			mChildren.get(i).draw(gl);
		}
	}
	
	public void add(int location, Mesh object) {
		mChildren.add(location, object);
	}
	
	public boolean add(Mesh object) {
		return mChildren.add(object);
	}
	
	public void clear() {
		mChildren.clear();
	}
	
	public Mesh get(int location) {
		return mChildren.get(location);
	}
	
	public Mesh remove(int location) {
		return mChildren.remove(location);
	}
	
	public boolean remove(Object object) {
		return mChildren.remove(object);
	}
	
	public int size() {
		return mChildren.size();
	}
}
