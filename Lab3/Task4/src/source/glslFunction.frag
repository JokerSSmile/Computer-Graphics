uniform vec2 mandel_pos;
uniform vec2 mandel_size;
uniform float mandel_iterations;

float calculateMandelbrotIterations(float x, float y) {

	float xx = 0.0;
    float yy = 0.0;
    float iter = 0.0;

    while (xx * xx + yy * yy <= 4.0 && iter < mandel_iterations) {

        float temp = xx * xx - yy * yy + x;
        yy = 2.0 * xx * yy + y;
        xx = temp;
        iter++;
    }

    return iter;
}

const vec3 BLUE = vec3( 1.0, 0.5, 1.0 );
const vec3 WHITE = vec3( 1.0, 1.0, 1.0 );
const vec3 YELLOW = vec3( 1.0, 0.5, 0.0 );
const vec3 RED = vec3( 1.0, 0.0, 0.0 );
const float COLOR_RESOLUTION = 16.0;

vec3 getColorByIndex(int index){
	int i = mod(index, 4);

	if (i < 1){
		return BLUE;
	}
	if (i < 2){
		return WHITE;
	}
	if (i < 3){
		return YELLOW;
	}
	return RED;
}

vec4 getColor(float iterations) {

    if (iterations == mandel_iterations){

        return vec4(0.0, 0.0, 0.0, 1.0);
    }

	int colorIndex = 0;
	float iterationsFloat = iterations;
	float colorRes = COLOR_RESOLUTION;

	while (iterationsFloat > colorRes){

		iterationsFloat -= colorRes;
		colorRes = colorRes * 2.0;
		colorIndex++;
	}

	float fraction = iterationsFloat / colorRes;
	vec3 from = getColorByIndex(colorIndex);
	vec3 to = getColorByIndex(colorIndex + 1);
	vec3 res = mix(from, to, fraction);

	return vec4( res.x, res.y, res.z, 1.0 );
}

void main() {

	float x = mandel_pos.x + gl_TexCoord[0].x * mandel_size.x;
	float y = mandel_pos.y + gl_TexCoord[0].y * mandel_size.y;
	float iterations = calculateMandelbrotIterations(x,y);
	gl_FragColor = getColor(iterations);
}
