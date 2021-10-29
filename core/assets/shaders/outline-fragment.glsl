/*how to make this shader: check if the pixel is transparent, if it is and nearby one is not transparent then set it to color passed to shader*/
//params:
//color - vec4
//outlineWidth - float - width of the sprite outline

//#ifdef GL_ES
//    precision mediump float;
//#endif
//
//uniform vec3 outlineColor;
//varying vec2 v_texCoords;
//uniform sampler2D u_texture;
//
//const float eps = 0.001;
//
//void main() {
//    vec4 pixelColor = texture2D(u_texture, v_texCoords).rgba;
//    if (abs(pixelColor.a - 1.0) <= eps) {
//        gl_FragColor = pixelColor;
//    } else {
//        float leftPixel = texture2D(u_texture, vec2(v_texCoords.x - 1, v_texCoords.y)).a;
//        float rightPixel = texture2D(u_texture, vec2(v_texCoords.x + 1, v_texCoords.y)).a;
//        float upperPixel = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + 1)).a;
//        float lowerPixel = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - 1)).a;
//        if (abs(leftPixel - 1.0) <= eps || abs(rightPixel - 1.0) <= eps || abs(upperPixel - 1.0) <= eps || abs(lowerPixel - 1.0) <= eps) {
//            gl_FragColor = vec4(outlineColor, 1.0);
//        } else {
//            gl_FragColor = pixelColor;
//        }
//    }
//}

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

const float offset = 1.0 / 128.0;//todo: pass texture width or get it from coords somehow
uniform vec3 outlineColor;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
void main()
{
    vec4 col = texture2D(u_texture, v_texCoords);
    if (col.a > 0.5)
    gl_FragColor = col;
    else {
        float a = texture2D(u_texture, vec2(v_texCoords.x + offset, v_texCoords.y)).a +
        texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - offset)).a +
        texture2D(u_texture, vec2(v_texCoords.x - offset, v_texCoords.y)).a +
        texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + offset)).a;//checks whether its a pixel near the edge because the sum of alphas wont be 0
        if (col.a < 1.0 && a > 0.0)//check if its a transparent pixel and if its near the edge
        gl_FragColor = vec4(outlineColor, 1.0);
        else
        gl_FragColor = col;
    }
}