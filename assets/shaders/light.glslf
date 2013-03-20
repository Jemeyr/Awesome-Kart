#version 150

uniform sampler2D colTex;
uniform sampler2D normTex;
uniform sampler2D posTex;


in vec3 posFS;

out vec4 outColor;


void main()
{

	vec3 objNorm = texture(normTex, posFS.xy * 0.001 + vec2(0.5, 0.5) ).xyz;
	
	//MRT
	outColor = 	vec4(objNorm,1.0);
	
}

