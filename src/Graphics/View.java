package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGenTextures;
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
	
	private int fboId, texId, dbufId;
	
	protected void unsetRenderTarget()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
	}
	
	protected void setRenderTarget()
	{
		glBindTexture(GL_TEXTURE_2D, 0);//unbind texture
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);//bind fbo
		
		glViewport(0,0,800,600);//set to our texture size. adjust this later depending on the resolution and view size
		
		
		//clear fbo

		glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	}
	
	protected int getTexId()
	{
		return texId;
	}
	
	protected View(Rectangle r, Camera c)
	{
		this.rect = r;
		this.cam = c;
		
		
		//init and bind
		this.fboId = glGenFramebuffers();
		this.texId = glGenTextures();
		this.dbufId = glGenRenderbuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);

		//set up texture
		glBindTexture(GL_TEXTURE_2D, texId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 800, 600, 0,GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null); //subbing a buffer here = black
		
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texId, 0);
		
		
		
		//set up depth buffer
		glBindRenderbuffer(GL_RENDERBUFFER, dbufId);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, 800, 600);
		
		glFramebufferRenderbuffer(GL_DEPTH_BUFFER, GL_DEPTH_ATTACHMENT, dbufId, 0);
		
		
		//unbind fbo
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	
	
}
