#version 150

out vec4 outColor;
out vec4 outNormal;
out vec4 outDepth;



in vec2 texCoordFS;
in vec3 normFS;
in vec4 posFS;

uniform sampler2D modelTexture;

void main()
{
	vec4 col = texture(modelTexture, texCoordFS);
	vec3 norm = 0.5 * (vec3(1.0,1.0,1.0) + normalize(normFS));
	float depth = posFS.z / posFS.w;
	
	//MRT
	outColor = 	vec4(col);
	outNormal = vec4(norm, 1.0);
	outDepth = 	vec4(depth, depth, depth, 1.0);
	
}

