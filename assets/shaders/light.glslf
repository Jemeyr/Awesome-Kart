#version 150

uniform mat4 ivpMatrix;

uniform sampler2D colTex;
uniform sampler2D normTex;
uniform sampler2D posTex;

uniform vec2 screenRect;

out vec4 outColor;

in vec3 posFS;

void main()
{

	vec3 objNorm = texture(normTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y)).xyz;
	
	vec3 depTex = texture(posTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y)).xyz;
	float dep = depTex.x;
	
	vec3 forward = dep * vec3(0,0,1);
	vec3 objPos = vec3(ivpMatrix * vec4(forward.xyz, 1));
	
	
	//MRT
	//outColor = vec4(texture(posTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y)).xyz,1.0);
	outColor = vec4(normalize(objPos),1);
}

