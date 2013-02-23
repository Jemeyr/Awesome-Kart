package Graphics;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

public class QuaternionHelper {
	
	
	//q[0] = cos(r/2);
	//q[1] = sin(r/2)*x;
	//q[2] = sin(r/2)*y;
	//q[3] = sin(r/2)*z;
	public static Quaternion fromYaw(float yaw)
	{
		Quaternion q = new Quaternion();
		q.w = (float)Math.sin(yaw);
		q.y = (float)Math.cos(yaw);
		return q;
	}
	public static Quaternion fromPitch(float pitch)
	{
		Quaternion q = new Quaternion();
		q.w = (float)Math.sin(pitch);
		q.x = (float)Math.cos(pitch);
		return q;
	}
	public static Quaternion fromRoll(float rololololololol)
	{
		Quaternion q = new Quaternion();
		q.w = (float)Math.sin(rololololololol);
		q.z = (float)Math.cos(rololololololol);
		return q;
	}

	
	//bleh, writing your own quaternion things sucks
	public static Matrix4f quat2mat(Quaternion q)
	{
		Matrix4f ret = new Matrix4f();
		float x = q.x;
		float y = q.y;
		float z = q.z;
		float w = q.w;
		
		ret.m00 = 1 - 2 * (y*y - z*z); 
		ret.m01 = 2 * (x*y + w*z);
		ret.m02 = 2 * (x*z - w*y);
		ret.m03 = 0;
		
		ret.m10 = 2 * (x*y - w*z);
		ret.m11 = 1 - 2 * (x*x - z*z);
		ret.m12 = 2 * (y*z + w*x);
		ret.m13 = 0;
		
		ret.m20 = 2 * (x*z + w*y);
		ret.m21 = 2 * (y*z - w*x);
		ret.m22 = 1 - 2 * (x*x - y*y);
		ret.m23 = 0;
		
		ret.m30 = 0;
		ret.m31 = 0;
		ret.m32 = 0;
		ret.m33 = 1;
		
		
		return ret;
	}
	
	
	
}
