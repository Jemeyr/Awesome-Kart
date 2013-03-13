package Graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;


public class DebugMesh {
	
	protected String id;
	protected int texId;
	
	protected int vao;
	protected int vbo_v,vbo_t,vbo_n,elem;
	protected int elementCount;
	
	protected int diffTexId;
	protected int normTexId;
	
	private GeometryShader shader;
	
	protected DebugMesh(String s, GeometryShader shader)
	{

		this.shader = shader;
		
		//set id
		id = s;
		
		
		//generate a new vertex array (note, this is the mesh, not a component, so this should be instantiated once per load, not per instance
        vao = glGenVertexArrays();

        glBindVertexArray(vao);

		
        //create vbos here when loading model
        load("assets/graphics/" + s + "/object.obj");


        

        //diffTexId= glGenTextures();
        //loadTex(s,"diffuse",diffTexId);
        texId = glGenTextures();
        loadTex(s,"debug",texId);
	}
	
	private void loadTex(String s, String texName, int tex)
	{
		//Let's load a texture here.
        InputStream is = null;
        ByteBuffer buf = null;
        
        try{
        	is = new FileInputStream("assets/graphics/" + s + "/" + texName + ".png");
        	PNGDecoder pd = new PNGDecoder(is);
        	
        	buf = ByteBuffer.allocateDirect(4*pd.getWidth()*pd.getHeight());
        	pd.decode(buf, pd.getWidth()*4, Format.RGBA);
        	buf.flip();

        	
        }catch (Exception e)
        {
        	System.out.println("error " + e);
        }
        
        
        glBindTexture(GL_TEXTURE_2D, tex);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        
        
        glTexImage2D( GL_TEXTURE_2D, 0, GL_RGBA, 1024, 1024, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        
        glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private void load(String fileName)
	{
		System.out.println("Loading " + fileName);
		int indexCount = 0;
		
		float[] vertices;
		int[] elements;
		float[] normals;
		float[] texCoords;
		
		List<Vector3f> init_vertices = new ArrayList<Vector3f>();
		List<Vector3f> init_normals = new ArrayList<Vector3f>();
		List<Vector2f> init_texCoords = new ArrayList<Vector2f>();
		
		List<Vert[]> init_faces = new ArrayList<Vert[]>();
		
		List<Vert> unique_verts = new ArrayList<Vert>();
		List<Integer> unique_indices = new ArrayList<Integer>();
		
		List<Vector3f> output_vertices = new ArrayList<Vector3f>();
		List<Vector3f> output_normals = new ArrayList<Vector3f>();
		List<Vector2f> output_texCoords = new ArrayList<Vector2f>();
		
		String[] tokens = new String[4];
		
		Scanner fileScanner = null;
		
		
		try
		{
			fileScanner = new Scanner(new File(fileName));
		}catch (Exception e){System.out.println("File Error");}
		
		if(fileScanner == null)
		{
			System.out.println("File Error");
			return;
		}

		String currLine = "";
	
		

		while(fileScanner.hasNext())
		{
			currLine = fileScanner.nextLine();

			tokens = currLine.split(" ");
			
			float x,y,z;
				
			if(currLine.startsWith("v "))
			{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				z = Float.parseFloat(tokens[3]);
				init_vertices.add(new Vector3f(x,y,z));
			}
			else if (currLine.startsWith("vn "))
			{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				z = Float.parseFloat(tokens[3]);
				init_normals.add(new Vector3f(x,y,z));
			}
			else if (currLine.startsWith("vt "))
			{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				init_texCoords.add(new Vector2f(x,y));
			}
			else if (currLine.startsWith("f "))
			{
				Vert[] face = new Vert[3];
				
				int j = 0;
				for(int i = 1; i < 4; i++)
				{
					String[] toks = tokens[i].split("/");
					face[j] = new Vert(indexCount++, toks[0], toks[1], toks[2]);
					j++;
				}
				init_faces.add(face);
			}
		}
		
		int uid = 0;
		
		//iterate over here and add it
		for(Vert[] f : init_faces)
		{
			for(int j = 0; j < 3; j++)
			{
				Vert v = f[j];
				boolean flag = true;
				int index = -1;
				
				for(Vert uvert : unique_verts)
				{
					if(uvert.vertexIndex == v.vertexIndex && uvert.textureIndex == v.textureIndex && 
							uvert.normalIndex == v.normalIndex)
					{
						index = uvert.index;
						flag = false;
						break;
					}
				}
				
				if(flag)
				{
					v.index = uid++;
					unique_verts.add(v);
					index = v.index;

					output_vertices.add(init_vertices.get(v.vertexIndex));
					output_texCoords.add(init_texCoords.get(v.textureIndex));
					output_normals.add(init_normals.get(v.normalIndex));

				}
				
				unique_indices.add(index);
	
				
			}
			
		}	
		
		elements = new int[unique_indices.size()];
		vertices = new float[3 * output_vertices.size()];
		texCoords = new float[2 * output_texCoords.size()];
		normals = new float[3 * output_normals.size()];
		
		
		int counter = 0;
		for(int i = 0; i < unique_indices.size(); i++)
		{
			elements[counter++] = unique_indices.get(i);
		}
		
		counter = 0;
		for(Vector3f v : output_vertices)
		{
			vertices[counter++] = v.x;
			vertices[counter++] = v.y;
			vertices[counter++] = v.z;	
		}
		
		counter = 0;
		for(Vector2f v : output_texCoords)
		{
			texCoords[counter++] = v.x;
			texCoords[counter++] = 1.0f - v.y;	
		}
		
		counter = 0;
		for(Vector3f v : output_normals)
		{
			normals[counter++] = v.x;
			normals[counter++] = v.y;
			normals[counter++] = v.z;	
		}
		
		
		//create floatbuffers from these arrays
		FloatBuffer vbuff = genFloatBuffer(vertices);
		FloatBuffer tcbuff = genFloatBuffer(texCoords);
        FloatBuffer nbuff = genFloatBuffer(normals);
        
        IntBuffer ebuff = BufferUtils.createIntBuffer(elements.length);
        ebuff.put(elements);
        ebuff.rewind();
             
        vbo_v = glGenBuffers();
        vbo_t = glGenBuffers();
        vbo_n = glGenBuffers();
        elem = glGenBuffers();
        
        
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glBufferData(GL_ARRAY_BUFFER, vbuff , GL_STATIC_DRAW);

        
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glBufferData(GL_ARRAY_BUFFER, tcbuff, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glBufferData(GL_ARRAY_BUFFER, nbuff , GL_STATIC_DRAW);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elem);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ebuff, GL_STATIC_DRAW);
        
        
        ebuff.flip();
        elementCount = ebuff.capacity();



        int position_attr = shader.position_attr;
        int normal_attr = shader.normal_attr;
        int texCoord_attr = shader.texCoord_attr;
        

		if(position_attr != -1)
		{
	        glBindBuffer(GL_ARRAY_BUFFER, 	vbo_v);								//These three and their order
	        glVertexAttribPointer( position_attr, 3, GL_FLOAT, false, 0, 0);		//
	        glEnableVertexAttribArray(position_attr);								//
		}
        
        if(texCoord_attr != -1)//some things aren't textured
        {
	        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);//URGENT glerror happens here
	        glVertexAttribPointer( texCoord_attr, 2, GL_FLOAT, false, 0, 0);
	        glEnableVertexAttribArray(texCoord_attr);
        }
        
        if(normal_attr != -1)
        {
	        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
	        glVertexAttribPointer( normal_attr, 3, GL_FLOAT, false, 0, 0);
	        glEnableVertexAttribArray(normal_attr);
        }
        
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        
        //unbind vbo
        glBindBuffer(GL_ARRAY_BUFFER,0);

	}
	
	

	protected class Vert {
		public int vertexIndex, normalIndex, textureIndex, index;
		
		
		public Vert(int index, int v , int t, int n)
		{
			this.index = index;
			this.vertexIndex = v - 1;
			this.textureIndex = t - 1;
			this.normalIndex = n - 1;
		}
		
		public Vert(int index, String v, String n, String t)
		{
			this(index, Integer.parseInt(v), Integer.parseInt(n), Integer.parseInt(t));
		}
	}
	
	private FloatBuffer genFloatBuffer(float[] input)
	{
		
		FloatBuffer fbuff = null;
		try{
			fbuff = BufferUtils.createFloatBuffer(input.length);
			fbuff.put(input);
		
			fbuff.rewind();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
		return fbuff;
	}

	public void destroy() {
		glDeleteBuffers(vbo_v);
		glDeleteBuffers(vbo_t);
		glDeleteBuffers(vbo_n);
		glDeleteBuffers(elem);
		
		glDeleteBuffers(diffTexId);
		
	}
}
