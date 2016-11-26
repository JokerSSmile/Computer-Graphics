uniform float TWIST;

void main()
{
	float angle = gl_Vertex.x * TWIST;
	float radius = (1.f + sin(angle)) * (1.f + 0.9f * cos(8.f * angle)) * (1.f + 0.1f * cos(24.f * angle));

    vec4 twistedCoord = vec4(
		gl_Vertex.x + radius * cos(angle),
        gl_Vertex.y + radius * sin(angle),
        gl_Vertex.z,
        gl_Vertex.w
    );
    vec4 position = gl_ModelViewProjectionMatrix * twistedCoord;
    // Transform twisted coordinate
    gl_Position = position;
    gl_FrontColor = (position + vec4(1.0)) * 0.5;
}