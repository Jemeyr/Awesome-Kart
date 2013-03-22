#version 150

uniform sampler2D colTex;
uniform sampler2D normTex;
uniform sampler2D posTex;

uniform float radius;
uniform vec2 screenRect;

out vec4 outColor;

in vec3 posFS;

void main()
{
	vec2 samplePos = vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y);
	vec3 objNorm = texture(normTex, samplePos).xyz;
	
	vec4 objPos = texture(posTex, samplePos);
	
	vec4 objCol = texture(colTex, samplePos);
	
	vec3 otol = (posFS - objPos.xyz);
	float diff = length(otol);
	
	float scale = max(0.15, 1 - diff * 0.02);
	
//	outColor = vec4(scale, scale, scale, 1);

	outColor = vec4(objPos.xyz,1);
	
	
}	
