#version 150

out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D tex;

void main()
{
	vec4 color = texture(tex, vTexCoord);

	
	vec2 mid = 2 * vTexCoord - vec2(1.0, 1.0);
	
	float dist = 1.0 - sqrt(mid.x * mid.x + mid.y * mid.y);
	
	outColor = color * dist;
}

