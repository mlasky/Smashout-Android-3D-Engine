package com.supernovamobile.smashout;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ModelFormatHandler extends DefaultHandler {
	public static final int STATE_UNKNOWN	= 0;
	public static final int STATE_MODEL 	= 1;
	public static final int STATE_TEXTURE 	= 3;
	public static final int STATE_VERTS		= 4;
	public static final int STATE_VERT		= 5;
	public static final int STATE_INDICES	= 6;
	public static final int STATE_INDEX		= 7;
	public static final int STATE_COORDS	= 8;
	public static final int STATE_COORD		= 9;
	
	public static final String TAG_MODEL 	= "model";
	public static final String TAG_TEXTURE 	= "texture";
	public static final String TAG_VERTS	= "verts";
	public static final String TAG_VERT		= "vert";
	public static final String TAG_INDICES 	= "indices";
	public static final String TAG_INDEX	= "index";
	public static final String TAG_COORDS 	= "coords";
	public static final String TAG_COORD	= "coord";
	
	private int 	mState 					= STATE_UNKNOWN;
	private Mesh	mModel					= null;
	
	private ArrayList<Float> mCurrentVerts 		= null;
	private ArrayList<Integer> mCurrentIndices	= null;
	private ArrayList<Float> mCurrentCoords		= null;
	
	@Override
	public void startDocument() throws SAXException {
		
	}
	
	@Override
	public void endDocument() throws SAXException {
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (localName.equalsIgnoreCase(TAG_MODEL)) {
			mState = STATE_MODEL;
			mModel = new Mesh();
		} else if (localName.equalsIgnoreCase(TAG_TEXTURE)) {
			mState = STATE_TEXTURE;
			
		} else if (localName.equalsIgnoreCase(TAG_VERTS)) {
			mState = STATE_VERTS;
			mCurrentVerts = new ArrayList<Float>();
		
		} else if (localName.equalsIgnoreCase(TAG_VERT)) {
			mState = STATE_VERT;
		
		} else if (localName.equalsIgnoreCase(TAG_INDICES)) {
			mState = STATE_INDICES;
			mCurrentIndices = new ArrayList<Integer>();
		
		} else if (localName.equalsIgnoreCase(TAG_INDEX)) {
			mState = STATE_INDEX;
		
		} else if (localName.equalsIgnoreCase(TAG_COORDS)) {
			mState = STATE_COORDS;
			mCurrentCoords = new ArrayList<Float>();
		
		} else if (localName.equalsIgnoreCase(TAG_COORD)) {
			mState = STATE_COORD;
		
		} else {
			mState = STATE_UNKNOWN;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (localName.equalsIgnoreCase(TAG_VERTS)) {
			float[] verts = new float[mCurrentVerts.size()];
			for (int i = 0; i < verts.length; i++) {
				verts[i] = mCurrentVerts.get(i).floatValue();
			}
		
			mModel.setVertices(verts);
			mCurrentVerts = null;
			
		} else if (localName.equalsIgnoreCase(TAG_INDICES)) {
			short[] indices = new short[mCurrentIndices.size()];
			for (int i = 0; i < indices.length; i++) {
				indices[i] = mCurrentIndices.get(i).shortValue();
			}
			
			mModel.setIndices(indices);
			mCurrentIndices = null;
		
		} else if (localName.equalsIgnoreCase(TAG_COORDS)) {
			float[] coords = new float[mCurrentCoords.size()];
			for (int i = 0; i < coords.length; i++) {
				coords[i] = mCurrentCoords.get(i).floatValue();
			}
			
			mModel.setTextureCoords(coords);
			mCurrentCoords = null;
		
		} 
		mState = STATE_UNKNOWN;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String strCharacters = new String(ch, start, length);
		if (mState == STATE_TEXTURE) {
			mModel.setTextureStr(strCharacters);
		} else if (mState == STATE_VERT) {
			mCurrentVerts.add(Float.valueOf(strCharacters.trim()));
		} else if (mState == STATE_INDEX) {
			mCurrentIndices.add(Integer.valueOf(strCharacters.trim()));
		} else if (mState == STATE_COORD) {
			mCurrentCoords.add(Float.valueOf(strCharacters.trim()));
		}
	}
	
	public Mesh getModel() {
		return mModel;
	}
}