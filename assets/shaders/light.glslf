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
	
	vec4 objPos = texture(posTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y));
	
	//MRT
	//outColor = vec4(texture(posTex, vec2(gl_FragCoord.x * screenRect.x, gl_FragCoord.y * screenRect.y)).xyz,1.0);
	if(objPos.x > 0)
	{
		outColor = vec4(0,1,0,1);
	}
	else
	{
		outColor = vec4(1,0,0,1);
	}	
	//outColor = vec4(objPos,1);
}

