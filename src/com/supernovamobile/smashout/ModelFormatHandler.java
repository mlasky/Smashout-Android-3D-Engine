package com.supernovamobile.smashout;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ModelFormatHandler extends DefaultHandler {
    public static final int STATE_UNKNOWN   = 0;
    public static final int STATE_MODEL     = 1;
    public static final int STATE_TEXTURE   = 3;
    public static final int STATE_FACES     = 4;
    public static final int STATE_FACE      = 5;
    public static final int STATE_VERTS     = 6;
    public static final int STATE_VERT      = 7;
    public static final int STATE_VX        = 8;
    public static final int STATE_VY        = 9;
    public static final int STATE_VZ        = 10;
    public static final int STATE_VNX        = 11;
    public static final int STATE_VNY        = 12;
    public static final int STATE_VNZ        = 13;
    public static final int STATE_INDEX     = 19;
    public static final int STATE_UV        = 20;
    public static final int STATE_U         = 21;
    public static final int STATE_V         = 22;
    
    
    public static final String TAG_MODEL    = "model";
    public static final String TAG_TEXTURE  = "tex";
    public static final String TAG_FACES    = "faces";
    public static final String TAG_FACE     = "face";
    public static final String TAG_VERTS    = "verts";
    public static final String TAG_VERT     = "vert";
    public static final String TAG_VX       = "vx";
    public static final String TAG_VY       = "vy";
    public static final String TAG_VZ       = "vz";
    public static final String TAG_VNX      = "vnx";
    public static final String TAG_VNY      = "vny";
    public static final String TAG_VNZ      = "vnz";
    public static final String TAG_INDEX    = "i";
    public static final String TAG_UV       = "uv";
    public static final String TAG_U        = "u";
    public static final String TAG_V        = "v";
    
    private int         mState                  = STATE_UNKNOWN;
    private SMMesh      mModel                  = null;
    
    private ArrayList<Float> mCurrentVerts      = null;
    private ArrayList<Integer> mCurrentIndices  = null;
    private ArrayList<Float> mCurrentCoords     = null;
    private ArrayList<Float> mCurrentVNormals   = null;
    public ModelFormatHandler() {
    }
    
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
            mModel = new SMMesh();
            mCurrentVerts = new ArrayList<Float>();
            mCurrentIndices = new ArrayList<Integer>();
            mCurrentCoords = new ArrayList<Float>();
            mCurrentVNormals = new ArrayList<Float>();
            
        } else if (localName.equalsIgnoreCase(TAG_TEXTURE)) {
            mState = STATE_TEXTURE;
            
        } else if (localName.equalsIgnoreCase(TAG_VERTS)) {
            mState = STATE_VERTS;
        
        } else if (localName.equalsIgnoreCase(TAG_VERT)) { 
            mState = STATE_VERT;
        
        } else if (localName.equalsIgnoreCase(TAG_VX)) {
            mState = STATE_VX;
        
        } else if (localName.equalsIgnoreCase(TAG_VY)) {
            mState = STATE_VY;
        
        } else if (localName.equalsIgnoreCase(TAG_VZ)) {
            mState = STATE_VZ;
        
        }else if (localName.equalsIgnoreCase(TAG_VNX)) {
            mState = STATE_VNX;
            
        } else if (localName.equalsIgnoreCase(TAG_VNY)) {
            mState = STATE_VNY;
            
        } else if (localName.equalsIgnoreCase(TAG_VNZ)) {
            mState = STATE_VNZ;
            
        } else if (localName.equalsIgnoreCase(TAG_INDEX)) {
        	mState = STATE_INDEX;
        
        } else if (localName.equalsIgnoreCase(TAG_UV)) {
            mState = STATE_UV;
        
        } else if (localName.equalsIgnoreCase(TAG_U)) {
            mState = STATE_U;
        
        } else if (localName.equalsIgnoreCase(TAG_V)) {
            mState = STATE_V;
        
        } else {
            mState = STATE_UNKNOWN;
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        
        if (localName.equalsIgnoreCase(TAG_MODEL)) {
            // Set Verts
            float[] verts = new float[mCurrentVerts.size()];
            for (int i = 0; i < verts.length; i++) {
                verts[i] = mCurrentVerts.get(i).floatValue();
            }
        
            mModel.setVertices(verts);
            mCurrentVerts = null;
            
            // Set Indices
            short[] indices = new short[mCurrentIndices.size()];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = mCurrentIndices.get(i).shortValue();
            }
            
            mModel.setIndices(indices);
            mCurrentIndices = null;
            
            // Set Vertex Normals
            float[] vnormals = new float[mCurrentVNormals.size()];
            for (int i = 0; i < vnormals.length; i++) {
            	vnormals[i] = mCurrentVNormals.get(i).floatValue();
            }
        
            mModel.setVNormals(vnormals);
            mCurrentVNormals = null;
        
            // Set UV Coords
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
        } else if (mState == STATE_VX) {
            mCurrentVerts.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_VY) {
            mCurrentVerts.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_VZ) {
            mCurrentVerts.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_VNX) {
            mCurrentVNormals.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_VNY) {
            mCurrentVNormals.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_VNZ) {
            mCurrentVNormals.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_INDEX) {
            mCurrentIndices.add(Integer.valueOf(strCharacters.trim()));
        } else if (mState == STATE_U) {
            mCurrentCoords.add(Float.valueOf(strCharacters.trim()));
        } else if (mState == STATE_V) {
            mCurrentCoords.add(Float.valueOf(strCharacters.trim()));
        }
    }
    
    public SMMesh getModel() {
        return mModel;
    }
}