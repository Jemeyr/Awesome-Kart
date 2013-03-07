#version 150

out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D tex;

void main()
{
	outColor = texture(tex, vTexCoord);// + vec4(0.5, 0.0, 0.0, 1.0);
}

