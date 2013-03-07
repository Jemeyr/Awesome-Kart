#version 150

out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D tex;

void main()
{
	vec4 color = texture(tex,vTexCoord);
	
	outColor = color;
}

