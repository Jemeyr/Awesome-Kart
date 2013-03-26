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
	
	// +  * texture(colTex, vTexCoord).xyz
	vec4 labVal = texture(labTex, vTexCoord);
	vec3 lightAmount = vec3(1,1,1) * (0.2 + 0.8 * dot(texture(normTex, vTexCoord).xyz, normalize(vec3(1,1,-1))));//vec3(max(0.15,labVal.w * labVal.x), max(0.15,labVal.w * labVal.y), max(0.15,labVal.w * labVal.z)); 
	
	//* texture(colTex, vTexCoord).xyz
	outColor = vec4(lightAmount * texture(colTex, vTexCoord).xyz, 1);// * edge;
	
}

