package particles;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import statics.Const;

public enum ParticleParameters {

	PARTICLES_PER_SECOND("10"), TEXTURE("RED"), TEXTURE_ROWS("8"), SCALE("1"), SPEED("5"), GRAVITY("0"), LIFE_LENGTH(
			"1"), LIFE_ERROR("0"), SPEED_ERROR("0"), SCALE_ERROR("0"), RANDOMIZE_ROTATION("false"), STARTING_SPREAD(
					"0"), DIRECTION_X("0"), DIRECTION_Y("0"), DIRECTION_Z("0"), DIRECTION_ERROR("0"), ROTATION_X(
							"0"), ROTATION_Y("0"), ROTATION_Z("0"), ROTATION_X_ERROR("0"), ROTATION_Y_ERROR(
									"0"), ROTATION_Z_ERROR("0"), MODEL(null), SCALE_INCREASE("0"),;

	private String defaultProperty;

	ParticleParameters(String defaultProperty) {
		this.defaultProperty = defaultProperty;
	}

	public String getProperty(String fileName) {
		Properties prop = new Properties();
		try {
			File f = new File(System.getProperty("user.dir"));
			f = new File(Const.RESSOURCES_FOLDER + "/ptk/" + fileName + ".ptk");
			prop.load(new FileInputStream(f));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(this.name());
	}

	public String getDefaultProperty() {
		return defaultProperty;
	}

}
