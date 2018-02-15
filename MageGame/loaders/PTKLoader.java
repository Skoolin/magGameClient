package loaders;

import objects.administration.ModelNames;
import objects.administration.TextureNames;
import org.lwjgl.util.vector.Vector3f;
import particles.ParticleParameters;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Engine;
import statics.Const;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by skolin on 15.02.18.
 */
public class PTKLoader {

    private static final float standardParticlesPerSecond = 10f;
    private static final float standardSpeedError = 0f;
    private static final String standardTextureName = "RED";
    private static final int standardNumberOfRows = 8;
    private static final float standardScale = 1f;
    private static final float standardSpeed = 1f;
    private static final float standardGravityCompliant = 0f;
    private static final float standardLifeLength = 2f;
    private static final float standardLifeError = 0f;
    private static final float standardScaleError = 0f;
    private static final boolean standardRandomizeRotation = false;
    private static final float standardStartingSpread = 0f;

    public static ParticleSystem loadPTKFile(String file, float pos_x, float pos_y, float pos_z) {
        ParticleSystem system = Engine.addParticleSystem(TextureNames.valueOf(standardTextureName).getFileName(),
                standardNumberOfRows, pos_x, pos_y, pos_z, standardParticlesPerSecond, standardSpeed, standardGravityCompliant,
                standardLifeLength, standardScale, standardLifeError, standardSpeedError, standardScaleError,
                standardRandomizeRotation, standardStartingSpread);
        File f = new File(Const.RESSOURCES_FOLDER + "ptk/" + file + ".ptk");

        // showInputDialog method returns null if the dialog is exited
        // without an option being chosen
        if (file == null || file.equals("")) {
            System.err.println("WARNING: No particle File specified");
        } else if (f.exists() && !f.isDirectory()) {
            Vector3f directionVector = getDirectionVector(file);
            String directionError = ParticleParameters.DIRECTION_ERROR.getProperty(file);
            if (directionVector != null && directionError != null) {
                system.setDirection(getDirectionVector(file), Float.parseFloat(directionError));
            }
            String lifeError = ParticleParameters.LIFE_ERROR.getProperty(file);
            if (lifeError != null) {
                system.setLifeError(Float.parseFloat(lifeError));
            }
            String speedError = ParticleParameters.SPEED_ERROR.getProperty(file);
            if (speedError != null) {
                system.setSpeedError(Float.parseFloat(speedError));
            }
            String scaleError = ParticleParameters.SCALE_ERROR.getProperty(file);
            if (scaleError != null) {
                system.setScaleError(Float.parseFloat(scaleError));
            }
            String averageSpeed = ParticleParameters.SPEED.getProperty(file);
            if(averageSpeed != null) {
                system.setAverageSpeed(Float.parseFloat(averageSpeed));
            }
            String averageScale = ParticleParameters.SCALE.getProperty(file);
            if (averageScale != null) {
                system.setAverageScale(Float.parseFloat(averageScale));
            }
            String averageLifeLength = ParticleParameters.LIFE_LENGTH.getProperty(file);
            if (averageLifeLength != null) {
                system.setAverageLifeLength(Float.parseFloat(averageLifeLength));
            }
            String gravity = ParticleParameters.GRAVITY.getProperty(file);
            if (gravity != null) {
                system.setGravityComplient(Float.parseFloat(gravity));
            }
            String randomRot = ParticleParameters.RANDOMIZE_ROTATION.getProperty(file);
            if(randomRot != null) {
                system.setRandomRotation(Boolean.parseBoolean(randomRot));
            }
            String pps = ParticleParameters.PARTICLES_PER_SECOND.getProperty(file);
            if(pps != null) {
                system.setParticlesPerSecond(Float.parseFloat(pps));
            }
            String startingSpread = ParticleParameters.STARTING_SPREAD.getProperty(file);
            if (startingSpread != null) {
                system.setStartingSpread(Float.parseFloat(startingSpread));
            }
            ParticleTexture text = getTexture(file);
            if (text != null) {
                system.setTexture(text);
            }
            String rotx = ParticleParameters.ROTATION_X.getProperty(file);
            if (rotx != null) {
                system.setXRot(Float.parseFloat(rotx));
            }
            String roty = ParticleParameters.ROTATION_Y.getProperty(file);
            if (roty != null) {
                system.setYRot(Float.parseFloat(roty));
            }
            String rotz = ParticleParameters.ROTATION_Z.getProperty(file);
            if (rotz != null) {
                system.setZRot(Float.parseFloat(rotz));
            }
            Vector3f getIncErrors = getIncErrors(file);
            if(getIncErrors != null) {
                system.setRotationError(getIncErrors);
            }
            String scaleInc = ParticleParameters.SCALE_INCREASE.getProperty(file);
            if(scaleInc != null) {
                system.setScaleIncrease(Float.parseFloat(scaleInc));
            }
            String modelName = ParticleParameters.MODEL.getProperty(file);
            if (modelName != null && !modelName.equals("")) {
                String model = ModelNames.valueOf(modelName).getFileName();
                system.setModel(model);
            } else {
                system.setModel(null);
            }
        } else {
            System.err.println("WARNING: could not find .ptk file: " + file);
        }

        return system;
    }

    private static Vector3f getIncErrors(String fileName) {
        String first = ParticleParameters.ROTATION_X_ERROR.getProperty(fileName);
        String firsta = ParticleParameters.ROTATION_Y_ERROR.getProperty(fileName);
        String firstb = ParticleParameters.ROTATION_Z_ERROR.getProperty(fileName);
        if (first != null && firsta != null && firstb != null) {
            return new Vector3f(Float.parseFloat(first), Float.parseFloat(firsta), Float.parseFloat(firstb));
        }
        return null;
    }

    private static ParticleTexture getTexture(String fileName) {
        String textureAdress = ParticleParameters.TEXTURE.getProperty(fileName);
        String textureRowNames = ParticleParameters.TEXTURE_ROWS.getProperty(fileName);
        if (textureAdress != null && textureRowNames != null) {
            return new ParticleTexture(Engine.loadTexure(TextureNames.valueOf(textureAdress).getFileName()),
                    Integer.parseInt(textureRowNames));
        } else {
            return null;
        }
    }

    private static Vector3f getDirectionVector(String fileName) {
        String xPos = ParticleParameters.DIRECTION_X.getProperty(fileName);
        String yPos = ParticleParameters.DIRECTION_Y.getProperty(fileName);
        String zPos = ParticleParameters.DIRECTION_Z.getProperty(fileName);
        if (xPos != null && yPos != null && zPos != null) {
            Vector3f vector = new Vector3f();
            vector.x = Float.parseFloat(xPos);
            vector.y = Float.parseFloat(yPos);
            vector.z = Float.parseFloat(zPos);
            return vector;
        } else {
            return null;
        }
    }
}
