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
	
	// + texture(colTex, vTexCoord).xyz
	outColor = vec4(texture(labTex, vTexCoord).xyz, 1);// * edge;
	
	
}

