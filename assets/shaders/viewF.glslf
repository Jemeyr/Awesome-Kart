#version 150

out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D colTex;
uniform sampler2D normTex;
uniform sampler2D posTex;
uniform sampler2D labTex;


void main()
{
	vec4 color1 = texture(colTex,vTexCoord);
	vec4 color2 = texture(normTex,vTexCoord);
	vec4 color3 = texture(posTex,vTexCoord);
	
	float edge = 1.0;
	if (vTexCoord.y < 0.005 || vTexCoord.y > 0.995 ||vTexCoord.x < 0.005 || vTexCoord.x > 0.995 )
	{
		edge = 0.05;
	}
	
	/*
	if(vTexCoord.x < .33)
	{
		outColor = color1 * edge;
	}
	else if(vTexCoord.x > .66)
	{
		outColor = color2 * edge;
	}
	else
	{
		outColor =  color3 * edge;
	}*/
	
	/*
	vec3 lightDir = normalize(vec3(0,2,1));
	vec3 norm = color2.xyz * 2 - vec3(1,1,1);
	norm = normalize(norm);
	*/
	
	//
	outColor = vec4(texture(colTex, vTexCoord).xyz, 1);//* edge;
	
	//color1 * dot(color2.xyz, vec3(1,0,1)) * edge;
	
}

