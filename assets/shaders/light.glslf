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
	
	float diff = distance(posFS, objPos.xyz);
	
	//MRT
	if(diff > 50)
	{
		outColor = vec4(0,0,0,0);
	}
	else
	{
		float scale = 1 - (diff / 50);
		scale = 0.5 * (scale + scale * scale);//polynomial falloff
		outColor = vec4(scale, scale, scale,1);
	}
	
	
}	

