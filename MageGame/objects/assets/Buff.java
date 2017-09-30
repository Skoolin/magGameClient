package objects.assets;

import gameObjects.GameObject;
import objects.administration.LivingObjectParameter;
import renderEngine.DisplayManager;

public class Buff extends GameObject {

	private LivingObjectParameter attrib;
	private int value;
	private String name;
	private int buffId;
	private static int buffIdCounter;
	private float lifeLength;
	private float age;
	private boolean unique;

	public Buff(LivingObjectParameter attrib, int value, String name, float lifeLength, boolean unique) {
		super();
		this.buffId = buffIdCounter++;
		this.attrib = attrib;
		this.value = value;
		this.name = name;
		this.lifeLength = lifeLength;
		this.age = 0f;
		this.unique = unique;
	}

	public LivingObjectParameter getAttrib() {
		return attrib;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public int getBuffId() {
		return buffId;
	}

	public boolean getUnique() {
		return unique;
	}

	public void resetAge() {
		age = 0f;
	}

	public float getAge() {
		return age;
	}

	public float getLifeLength() {
		return lifeLength;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void update() {
		age += DisplayManager.getFrameTime();
	}

	@Override
	public void exit() {

	}

}
