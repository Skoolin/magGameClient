
public class PropertyFileCreator {
	public static void main(String[] args) {
		String huhu = "\n" + 
				"    NAME,\n" + 
				"	HP,\n" + 
				"    ATTACK_DAMAGE,\n" + 
				"	HITBOX_RADIUS,\n" + 
				"	ATTACK_RANGE,\n" + 
				"	RUN_SPEED,\n" + 
				"	ATTACK_ANIMATION_TIME,\n" + 
				"	ATTACK_TIME,\n" + 
				"	MAX_HEALTH,";
		String[] split = huhu.split(System.lineSeparator());
		for(String string: split) {
			System.out.println(string + "_IDLE=");
			System.out.println(string + "_WALKING=");
			System.out.println(string + "_ATTACK=");
			System.out.println(string + "_ATTACK2=");
			System.out.println(string + "_SPECIAL_ATTACK=");
			System.out.println(string + "_THROW=");
			System.out.println(string + "_CAST=");
			System.out.println(string + "_EMOTE=");
		}
	}
}
