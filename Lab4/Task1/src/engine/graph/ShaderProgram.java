package engine.graph;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.*;

class ShaderProgram {

    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    private final Map<String, UniformData> uniforms;

    ShaderProgram() throws Exception {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
        uniforms = new HashMap<>();
    }

    void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, new UniformData(uniformLocation));
    }

    void createPointLightListUniform(String uniformName, int size) throws Exception {
        for (int i = 0; i < size; i++) {
            createPointLightUniform(uniformName + "[" + i + "]");
        }
    }

    private void createPointLightUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".position");
        createUniform(uniformName + ".intensity");
        createUniform(uniformName + ".att.constant");
        createUniform(uniformName + ".att.linear");
        createUniform(uniformName + ".att.exponent");
    }

    void createSpotLightListUniform(String uniformName, int size) throws Exception {
        for (int i = 0; i < size; i++) {
            createSpotLightUniform(uniformName + "[" + i + "]");
        }
    }

    private void createSpotLightUniform(String uniformName) throws Exception {
        createPointLightUniform(uniformName + ".pl");
        createUniform(uniformName + ".conedir");
        createUniform(uniformName + ".cutoff");
    }

    void createDirectionalLightUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".direction");
        createUniform(uniformName + ".intensity");
    }

    void createMaterialUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".hasTexture");
        createUniform(uniformName + ".reflectance");
    }

    void setUniform(String uniformName, Matrix4f value) {
        UniformData uniformData = uniforms.get(uniformName);
        if (uniformData == null) {
            throw new RuntimeException("Uniform [" + uniformName + "] has nor been created");
        }
        // Check if float buffer has been created
        FloatBuffer fb = uniformData.getFloatBuffer();
        if (fb == null) {
            fb = BufferUtils.createFloatBuffer(16);
            uniformData.setFloatBuffer(fb);
        }
        // Dump the matrix into a float buffer
        value.get(fb);
        glUniformMatrix4fv(uniformData.getUniformLocation(), false, fb);
    }

    void setUniform(String uniformName, int value) {
        UniformData uniformData = uniforms.get(uniformName);
        if (uniformData == null) {
            throw new RuntimeException("Uniform [" + uniformName + "] has nor been created");
        }
        glUniform1i(uniformData.getUniformLocation(), value);
    }

    void setUniform(String uniformName, float value) {
        UniformData uniformData = uniforms.get(uniformName);
        if (uniformData == null) {
            throw new RuntimeException("Uniform [" + uniformName + "] has nor been created");
        }
        glUniform1f(uniformData.getUniformLocation(), value);
    }

    void setUniform(String uniformName, Vector3f value) {
        UniformData uniformData = uniforms.get(uniformName);
        if (uniformData == null) {
            throw new RuntimeException("Uniform [" + uniformName + "] has nor been created");
        }
        glUniform3f(uniformData.getUniformLocation(), value.x, value.y, value.z);
    }

    void setUniform(String uniformName, DirectionalLight dirLight) {
        setUniform(uniformName + ".colour", dirLight.getColor());
        setUniform(uniformName + ".direction", dirLight.getDirection());
        setUniform(uniformName + ".intensity", dirLight.getIntensity());
    }

    void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".colour", material.getColour());
        setUniform(uniformName + ".hasTexture", material.isTextured() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }

    void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Code: " + shaderId);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    void bind() {
        glUseProgram(programId);
    }

    void unbind() {
        glUseProgram(0);
    }

    void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
