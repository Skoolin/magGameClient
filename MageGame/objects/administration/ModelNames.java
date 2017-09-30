package objects.administration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import statics.Const;

public enum ModelNames {

	MAGE1, MAGE2, MAGE3, BARREL, TROLL, GOBLIN, FIRE_ELEMENTAL, AIR_ELEMENTAL, EARTH_ELEMENTAL, WATER_ELEMENTAL, SKELETON, SNAKE_PERSON, FISH_FROG_MAN, LIZZARD, RAT, BLOB, GHOST, ROCK, TREE1, TREE2, TREE3, LOG, WALL, HOUSE1, HOUSE2, HOUSE3, HUT1, HUT2, HUT3, BRIDGE, MOVING_SHIELD, PLANE;

	private String getProperty() {
		Properties prop = new Properties();
		try {
			File f = new File(System.getProperty("user.dir"));
			f = new File(Const.RESSOURCES_FOLDER + "fileNames.properties");
			prop.load(new FileInputStream(f));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(this.name());
	}

	public String getFileName() {
		return getProperty();
	}
}
