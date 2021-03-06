#version 150

uniform sampler2D colTex;
uniform sampler2D normTex;
uniform sampler2D posTex;

uniform float radius;
uniform vec2 screenRect;
uniform vec3 lightColor;

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
	
	if(diff > radius)
	{
		discard;
	}
	
	float incidence = 0.3 + dot(objNorm, normalize(otol));
	
	float scale = max(0.0, 1 - diff /(radius));
	
	//scale = clamp((1-scale) * incidence + scale * scale, 0.0, 1.0);
	scale = incidence * scale;
	
	outColor = vec4(normalize(lightColor), scale);
	
}	

