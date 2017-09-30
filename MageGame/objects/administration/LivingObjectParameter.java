package objects.administration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import statics.Const;

public enum LivingObjectParameter {

	NAME, ATTACK_DAMAGE, HITBOX_RADIUS, ATTACK_RANGE, RUN_SPEED, ATTACK_ANIMATION_TIME, ATTACK_TIME, MAX_HEALTH, DAMAGE_SHIELD, DAMAGE_MULTIPLIER, HAS_ANIMATION,;

	public String getProperty(String fileName) {
		Properties prop = new Properties();
		try {
			File f = new File(System.getProperty("user.dir"));
			f = new File(Const.RESSOURCES_FOLDER + "lgo/" + fileName + ".lgo");
			prop.load(new FileInputStream(f));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(this.name());
	}
}
