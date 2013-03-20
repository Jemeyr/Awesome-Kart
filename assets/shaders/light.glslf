#version 150

uniform sampler2D colTex;
uniform sampler2D normTex;
uniform sampler2D posTex;

uniform vec2 screenRect;

out vec4 outColor;

in vec3 posFS;

void main()
{

	vec3 objNorm = texture(normTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y)).xyz;
	//vec3 lightDir = posFS - 
	
	
	//MRT
	outColor = 	vec4(objNorm,1.0);
	
}

