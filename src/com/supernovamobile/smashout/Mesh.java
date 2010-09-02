package com.supernovamobile.smashout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Mesh extends Group {
	private FloatBuffer mVerticesBuffer = null;
	private FloatBuffer mColorBuffer 	= null;
	private FloatBuffer mTextureBuffer  = null;
	private ShortBuffer mIndicesBuffer 	= null;
	
	private int 	mNumIndices 	= -1;
	private int[] 	mTextures 		= new int[1];
	
	private float[] mRGBA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	
	private String  mTextureString 	= null;
	
	public boolean mTexLoaded 		= false;
	
	@Override
	public void draw(GL10 gl) throws IOException {
		Log.e("Model", "Drawing: " + this);
		Log.e("Model", this + " rx = " + rx);
		super.draw(gl);
		if (mVerticesBuffer != null) {
			
			gl.glFrontFace(GL10.GL_CCW);
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);	
		
			if (mTextureBuffer != null && mTextureString != null) {
				gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[0]);
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			}
			
			gl.glColor4f(mRGBA[0], mRGBA[1], mRGBA[2], mRGBA[3]);
			
			if (mColorBuffer != null) {
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
			}
			
			gl.glPushMatrix();
			
			gl.glTranslatef(x, y, z);
			gl.glRotatef(rx, 1, 0, 0);
			gl.glRotatef(ry, 0, 1, 0);
			gl.glRotatef(rz, 0, 0, 1);
			
			gl.glDrawElements(GL10.GL_TRIANGLES, mNumIndices, GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
			
			gl.glPopMatrix();
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			if (mTextureString != null) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			gl.glDisable(GL10.GL_CULL_FACE);
		}
	}
	
	public void setVertices(float[] vertices) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVerticesBuffer = vbb.asFloatBuffer();
		mVerticesBuffer.put(vertices);
		mVerticesBuffer.position(0);
	}
	
	
	public void setIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndicesBuffer = ibb.asShortBuffer();
		mIndicesBuffer.put(indices);
		mIndicesBuffer.position(0);
		mNumIndices = indices.length;
	}
	
	public void setTextureCoords(float[] coords) {
		ByteBuffer tbb = ByteBuffer.allocateDirect(coords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer = tbb.asFloatBuffer();
		mTextureBuffer.put(coords);
		mTextureBuffer.position(0);
	}
	
	protected void loadTexture(GL10 gl, Context context) throws IOException {
		Log.e("Mesh", "Loading texture: " + mTextureString);
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
		
		mTexLoaded = true;
		
		bitmap.recycle();
	}
	
	protected void setColor(float red, float green, float blue, float alpha) {
		mRGBA[0] = red;
		mRGBA[0] = green;
		mRGBA[0] = blue;
		mRGBA[0] = alpha;
	}
	
	protected void setColors(float[] colors) {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer = cbb.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
	}
	
	public void setTextureStr(String texture) {
		Log.e("mesh", "setTextureString: " + texture);
		mTextureString = texture;
	}
	
	public String getTextureStr() {
		return mTextureString;
	}
}
