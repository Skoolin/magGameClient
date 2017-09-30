package objects.administration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import statics.Const;

public enum TextureNames {

	MAGE1, MAGE1_SPECIAL, MAGE1_ARCHON, MAGE2, MAGE2_SPECIAL, MAGE2_ARCHON, MAGE3, MAGE3_SPECIAL, MAGE3_ARCHON, BARREL, TROLL, GOBLIN, GOBLIN_SPECIAL, FIRE_ELEMENTAL, FIRE_ELEMENTAL_SPECIAL, AIR_ELEMENTAL, AIR_ELEMENTAL_SPECIAL, WATER_ELEMENTAL, WATER_ELEMENTAL_SPECIAL, EARTH_ELEMENTAL, EARTH_ELEMENTAL_SPECIAL, SKELETON, SKELETON_ARCHON, SNAKE_PERSON, FISH_FROG_MAN, LIZZARD, RAT, RAT_SPECIAL, BLOB, GHOST, ROCK, TREE1, TREE2, TREE3, LOG, WALL, HOUSE1, HOUSE2, HOUSE3, HUT1, HUT2, HUT3, BRIDGE, MOVING_SHIELD, RED, FIRE, HEALTH, GREY, GREEN,;

	private String getProperty() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(Const.RESSOURCES_FOLDER + "textureNames.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(this.name());
	}

	public String getFileName() {
		return getProperty();
	}
}
