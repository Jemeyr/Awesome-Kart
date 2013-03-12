#version 150

out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D tex0;
uniform sampler2D tex1;


void main()
{
	vec4 color = texture(tex1,vTexCoord);
	
	float edge = 1.0;
	if (vTexCoord.y < 0.005 || vTexCoord.y > 0.995 ||vTexCoord.x < 0.005 || vTexCoord.x > 0.995 )
	{
		edge = 0.05;
	}
	
	outColor = color * edge;
}

