#version 150

out vec4 outColor;
out vec4 outNormal;
out vec4 outPosition;



in vec2 texCoordFS;
in vec3 normFS;
in vec3 posFS;

uniform sampler2D modelTexture;

void main()
{
	vec4 col = texture(modelTexture, texCoordFS);
	vec3 norm = normalize(normFS);//0.5 * (vec3(1.0,1.0,1.0) + normalize(normFS));
	
	//MRT
	outColor = 	vec4(col);
	outNormal = vec4(norm, 1.0);
	outPosition = vec4(posFS.xyz, 1);
}

