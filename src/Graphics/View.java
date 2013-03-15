package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.FileInputStream;
import java.nio.ByteBuffer;


import org.lwjgl.util.Rectangle;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class View {
	protected Rectangle rect;
	protected Camera cam;
	
	private RenderTarget geometryRenderTarget, lightAccumulationRenderTarget;
	
	protected float screenPos[];
	protected float screenSize[];	
	
	protected View(Rectangle r, Camera c)
	{
		this.rect = r; //this assumes windowsize of 800,600
		this.cam = c;

		this.geometryRenderTarget = new RenderTarget(true);//we want a multichannel (multiple rgb)
		this.lightAccumulationRenderTarget = new RenderTarget(false);//we want this single channel 
		
		
		screenPos = new float[2];
		screenPos[0] = r.getX() / 400.0f - 1.0f;
		screenPos[1] = r.getY() / 300.0f - 1.0f;
		
		screenSize = new float[2];
		screenSize[0] = r.getWidth() / 400.0f;
		screenSize[1] = r.getHeight() / 300.0f;
		
		c.setAspectRatio(30f * 0.011111f * 3.14159f * (float)r.getWidth() / (float)r.getHeight());
		
		
		
	}
	
	protected void setRenderTarget(RenderBufferEnum e)
	{
		if(e == RenderBufferEnum.geometry)
		{
			geometryRenderTarget.set();
		}
		else if(e == RenderBufferEnum.lightAccumulation)
		{
			lightAccumulationRenderTarget.set();
		}
	}
	
	protected void unsetRenderTarget()
	{
		geometryRenderTarget.unset();
	}
	
	protected int getColorTexture()
	{
		return this.geometryRenderTarget.getColId();
	}
	
	protected int getNormalTexture()
	{
		return this.geometryRenderTarget.getNormId();
	}
	
	protected int getPosTexture()
	{
		return this.geometryRenderTarget.getPosId();
	}
	
	protected int getLightTexture()
	{
		return this.lightAccumulationRenderTarget.getColId();
	}
	
	
}
