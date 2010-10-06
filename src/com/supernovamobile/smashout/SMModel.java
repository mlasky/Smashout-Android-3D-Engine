package com.supernovamobile.smashout;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class SMModel extends SMMesh {
    
    boolean mLoaded = false;
    public boolean mTexLoaded       = false;
    
    public SMModel(int fileResource, Context context, String name) throws IOException, ParserConfigurationException, SAXException {
        mName = mName + ":" + name;
        
        InputStream fis = context.getResources().openRawResource(fileResource);
        
        SAXParserFactory    saxParserFactory    = SAXParserFactory.newInstance();
        SAXParser           saxParser           = saxParserFactory.newSAXParser();
        XMLReader           xmlReader           = saxParser.getXMLReader();
        ModelFormatHandler  modelHandler        = new ModelFormatHandler();
        
        xmlReader.setContentHandler(modelHandler);
        
        InputSource inputSource = new InputSource(fis);
        xmlReader.parse(inputSource);
        
        SMMesh mesh = modelHandler.getModel();
        add(mesh);
        
        mTextureString = mesh.mTextureString;
        mLoaded = true;
    }
    
    @Override
    protected void loadTextures(GL10 gl, Context context) throws IOException {
        if (!mTexLoaded && mTextureString != null) {
            Log.e("Mesh", "Loading texture: " + mTextureString);
            super.loadTextures(gl, context);
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(mTextureString);
            Bitmap bitmap = null;
            
            try { 
                bitmap = BitmapFactory.decodeStream(is);
            } finally {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {}
            }
            
            gl.glGenTextures(1, mTextures, 0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[0]);
            
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
            
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
            mTexLoaded = true;
        }
    }
    
    @Override
    public void setX(float x) {
        super.setX(x);
        int size = size();
        for (int i =  0; i < size; i++) {
            mChildren.get(i).setX(x);
        }
    }
    
    @Override
    public void setY(float y) {
        super.setY(y);
        int size = size();
        for (int i =  0; i < size; i++) {
            mChildren.get(i).setY(y);
        }
    }
    
    
    @Override
    public void setZ(float z) {
        super.setZ(z);
        int size = size();
        for (int i =  0; i < size; i++) {
            mChildren.get(i).setZ(z);
        }
    }
    
    @Override
    public void setRx(float rx) {
        super.setRx(rx);
        int size = size();
        for (int i =  0; i < size; i++) {
            mChildren.get(i).setRx(rx);
        }
    }
    
    @Override
    public void setRy(float ry) {
        super.setRy(ry);
        int size = size();
        for (int i =  0; i < size; i++) {
            mChildren.get(i).setRy(ry);
        }
    }
    
    @Override
    public void setRz(float rz) {
        super.setRz(rz);
        int size = size();
        for (int i =  0; i < size; i++) {
            mChildren.get(i).setRz(rz);
        }
    }
}
