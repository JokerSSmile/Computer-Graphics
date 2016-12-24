uniform vec2 size;
uniform float scale;
uniform vec2 pos;

int x_resolution = 640, y_resolution = 2*x_resolution/3;
int maxiteration = 256;

void main(void) {
	//all of the meat of the mandelbrot set here
	float re = 0.0, im = 0.0;

	int k;
	for(k = 0; k < maxiteration; k++) {
		//_re is a placeholder for when re changes
		float _re = re;

		//gl_FragCoord.x returns between 0 & width, etc.
		re = re*re - im*im + (3 * gl_FragCoord.x - 2*size.x + pos.x) / (x_resolution*scale);
		im = 2*_re*im - (2 * gl_FragCoord.y - size.y + pos.y) / (y_resolution*scale);

		//take magnitude of re and im
		if(re*re + im*im > 4) break;
	}

	gl_FragColor = vec4(0.0, k == maxiteration ? 0.0 : float(k)/100.0 , 0.0, 1.0);
}