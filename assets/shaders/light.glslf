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
	vec2 samplePos = vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y);
	vec3 objNorm = texture(normTex, samplePos).xyz;
	
	vec4 objPos = texture(posTex, samplePos);
	
	vec4 objCol = texture(colTex, samplePos);
	
	//MRT
	outColor = objCol;// vec4(texture(posTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y)).xyz,1.0);
	
}

