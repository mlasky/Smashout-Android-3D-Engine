package com.supernovamobile.smashout;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class SmashoutGLRenderer implements Renderer {

    private SMGroup     mScene;
    private SMModel     mCube;
    private SMModel mCube2;
    private Context mContext;
    
    /** Is light enabled ( NEW ) */
    private boolean light = false;

    /* 
     * The initial light values for ambient and diffuse
     * as well as the light position ( NEW ) 
     */
    private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] lightPosition = {2.0f, 2.0f, 15.0f, 1.0f};
        
    /* The buffers for our light values ( NEW ) */
    private FloatBuffer lightAmbientBuffer;
    private FloatBuffer lightDiffuseBuffer;
    private FloatBuffer lightPositionBuffer;
    
    public SmashoutGLRenderer(Context context) 
        throws IOException, ParserConfigurationException, SAXException 
    {
        mContext = context;
        
        mScene = new SMGroup();
        mCube = new SMModel(R.raw.cube, context, "Cube_1");
        //mCube2 = new Model(R.raw.sub, context, "Cube_2");
        mScene.add(mCube);
        //mScene.add(mCube2);
        mCube.setRx(45.0f);
        
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        lightAmbientBuffer = byteBuf.asFloatBuffer();
        lightAmbientBuffer.put(lightAmbient);
        lightAmbientBuffer.position(0);
        
        byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        lightDiffuseBuffer = byteBuf.asFloatBuffer();
        lightDiffuseBuffer.put(lightDiffuse);
        lightDiffuseBuffer.position(0);
        
        byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        lightPositionBuffer = byteBuf.asFloatBuffer();
        lightPositionBuffer.put(lightPosition);
        lightPositionBuffer.position(0);
    }
     
    @Override
    public void onDrawFrame(GL10 gl) {
        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -7.0f);
        
        //Check if the light flag has been set to enable/disable lighting
        light = true;
        if(light) {
            gl.glEnable(GL10.GL_LIGHTING);
        } else {
            gl.glDisable(GL10.GL_LIGHTING);
        }
        
        gl.glPushMatrix();
        //mCube.setRx(mCube.getRx() + 1);
        mCube.setRy(mCube.getRy() + 5);
        
        //mCube2.setX(5.0f);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        try {
            mScene.draw(gl);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gl.glPopMatrix();
        
        
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        
        //And there'll be light!
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer);      //Setup The Ambient Light ( NEW )
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);      //Setup The Diffuse Light ( NEW )
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer);    //Position The Light ( NEW )
        gl.glEnable(GL10.GL_LIGHT0);                
        
        //Settings
        gl.glDisable(GL10.GL_DITHER);               //Disable dithering ( NEW )
        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping
        
        //Flat shading for now 
        // TODO: Calculate vertex normals and switch to smooth after fixing U/V
        // calculations.
        gl.glShadeModel(GL10.GL_SMOOTH);            	
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    //Black Background
        gl.glClearDepthf(1.0f);                     //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);             //The Type Of Depth Testing To Do
        
        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
        
        try {
            mScene.loadTextures(gl, mContext);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    } 
}
