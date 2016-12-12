#version 130

bool PointIsOnTheLeft(vec2 p0, vec2 p1, vec2 p)
{
    vec2 p0p1 = p1 - p0;
    vec2 n = vec2(-p0p1.y, p0p1.x);
    return dot(p - p0, n) > 0.0;
}

bool PointIsInsideTriangle(vec2 p0, vec2 p1, vec2 p2, vec2 p)
{
    return PointIsOnTheLeft(p0, p1, p) &&
           PointIsOnTheLeft(p1, p2, p) &&
           PointIsOnTheLeft(p2, p0, p);
}

void main()
{
    vec2 pos = gl_TexCoord[0].xy;

    const vec2 p0 = vec2(2.0, 3.0);
    const vec2 p1 = vec2(2.65, 2.3);
    const vec2 p2 = vec2(2.45, 1.25);
    const vec2 p3 = vec2(1.55, 1.25);
    const vec2 p4 = vec2(1.35, 2.3);

    const vec2 midP0 = vec2(2.3, 1.825);
    const vec2 midP1 = vec2(2.0, 1.6);
    const vec2 midP2 = vec2(1.55, 1.25);

    if (PointIsInsideTriangle(p3, midP0, p0, pos) || PointIsInsideTriangle(midP1, p2, midP0, pos) || PointIsInsideTriangle(p4, midP1, p1, pos))
    {
        gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    }
    else
    {
        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
    }
}