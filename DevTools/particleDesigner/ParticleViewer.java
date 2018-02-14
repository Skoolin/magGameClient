package particleDesigner;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import objects.administration.ModelNames;
import objects.administration.TextureNames;
import particles.ParticleParameters;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Engine;
import renderEngine.Game;

public class ParticleViewer implements Game {

	private ParticleSystem system;
	private String fileName;
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

	public ParticleViewer(String fileName) {
		Engine.addLight(0f, 10f, 20f, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 0f, 0f));
		Mouse.setGrabbed(false);
		system = (ParticleSystem) Engine.addParticleSystem(TextureNames.valueOf(standardTextureName).getFileName(),
				standardNumberOfRows, 0f, 0f, 5f, standardParticlesPerSecond, standardSpeed, standardGravityCompliant,
				standardLifeLength, standardScale, standardLifeError, standardSpeedError, standardScaleError,
				standardRandomizeRotation, standardStartingSpread);
		Engine.setPlayerCamera(system);
		this.fileName = fileName;
	}

	@Override
	public void update() {
		Vector3f directionVector = getDirectionVector();
		String directionError = ParticleParameters.DIRECTION_ERROR.getProperty(fileName);
		if (directionVector != null && directionError != null) {
			system.setDirection(getDirectionVector(), Float.parseFloat(directionError));
		}
		String lifeError = ParticleParameters.LIFE_ERROR.getProperty(fileName);
		if (lifeError != null) {
			system.setLifeError(Float.parseFloat(lifeError));
		}
		String speedError = ParticleParameters.SPEED_ERROR.getProperty(fileName);
		if (speedError != null) {
			system.setSpeedError(Float.parseFloat(speedError));
		}
		String scaleError = ParticleParameters.SCALE_ERROR.getProperty(fileName);
		if (scaleError != null) {
			system.setScaleError(Float.parseFloat(scaleError));
		}
		String averageSpeed = ParticleParameters.SPEED.getProperty(fileName);
		if(averageSpeed != null) {
			system.setAverageSpeed(Float.parseFloat(averageSpeed));
		}
		String averageScale = ParticleParameters.SCALE.getProperty(fileName);
		if (averageScale != null) {
			system.setAverageScale(Float.parseFloat(averageScale));
		}
		String averageLifeLength = ParticleParameters.LIFE_LENGTH.getProperty(fileName);
		if (averageLifeLength != null) {
			system.setAverageLifeLength(Float.parseFloat(averageLifeLength));
		}
		String gravity = ParticleParameters.GRAVITY.getProperty(fileName);
		if (gravity != null) {
			system.setGravityComplient(Float.parseFloat(gravity));
		}
		String randomRot = ParticleParameters.RANDOMIZE_ROTATION.getProperty(fileName);
		if(randomRot != null) {
			system.setRandomRotation(Boolean.parseBoolean(randomRot));
		}
		String pps = ParticleParameters.PARTICLES_PER_SECOND.getProperty(fileName);
		if(pps != null) {
			system.setParticlesPerSecond(Float.parseFloat(pps));
		}
		String startingSpread = ParticleParameters.STARTING_SPREAD.getProperty(fileName);
		if (startingSpread != null) {
			system.setStartingSpread(Float.parseFloat(startingSpread));
		}
		ParticleTexture text = getTexture();
		if (text != null) {
			system.setTexture(text);
		}
		String rotx = ParticleParameters.ROTATION_X.getProperty(fileName);
		if (rotx != null) {
			system.setXRot(Float.parseFloat(rotx));
		}
		String roty = ParticleParameters.ROTATION_Y.getProperty(fileName);
		if (roty != null) {
			system.setYRot(Float.parseFloat(roty));
		}
		String rotz = ParticleParameters.ROTATION_Z.getProperty(fileName);
		if (rotz != null) {
			system.setZRot(Float.parseFloat(rotz));
		}
		Vector3f getIncErrors = getIncErrors();
		if(getIncErrors != null) {
			system.setRotationError(getIncErrors);
		}
		String scaleInc = ParticleParameters.SCALE_INCREASE.getProperty(fileName);
		if(scaleInc != null) {
			system.setScaleIncrease(Float.parseFloat(scaleInc));
		}
		String modelName = ParticleParameters.MODEL.getProperty(fileName);
		if (modelName != null && !modelName.equals("")) {
			String model = ModelNames.valueOf(modelName).getFileName();
			system.setModel(model);
		} else {
			system.setModel(null);
		}
	}

	private Vector3f getIncErrors() {
		String first = ParticleParameters.ROTATION_X_ERROR.getProperty(fileName);
		String firsta = ParticleParameters.ROTATION_Y_ERROR.getProperty(fileName);
		String firstb = ParticleParameters.ROTATION_Z_ERROR.getProperty(fileName);
		if (first != null && firsta != null && firstb != null) {
			return new Vector3f(Float.parseFloat(first), Float.parseFloat(firsta), Float.parseFloat(firstb));
		}
		return null;
	}

	private ParticleTexture getTexture() {
		String textureAdress = ParticleParameters.TEXTURE.getProperty(fileName);
		String textureRowNames = ParticleParameters.TEXTURE_ROWS.getProperty(fileName);
		if (textureAdress != null && textureRowNames != null) {
			return new ParticleTexture(Engine.loadTexure(TextureNames.valueOf(textureAdress).getFileName()),
					Integer.parseInt(textureRowNames));
		} else {
			return null;
		}
	}

	private Vector3f getDirectionVector() {
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

	@Override
	public void exit() {
		Engine.removeParticleSystem(system);
	}

}
