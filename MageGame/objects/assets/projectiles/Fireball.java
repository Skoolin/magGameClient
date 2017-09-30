package objects.assets.projectiles;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import objects.Explosion;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import particles.ParticleSystem;
import renderEngine.Engine;

public class Fireball extends Projectile {

	Entity center;

	public Fireball(int team, Vector3f target, Vector3f position) {
		super(Engine.addParticleSystem("fire", 8, position.x, position.y, position.z, 20f, 1f, 0f, 1f, 5f, 0.25f, 0.1f,
				0.1f, true, 0.3f), target, team, position);

		center = Engine.addStaticEntity(movable.getPosition(), new Vector3f(0f, 0f, 0f), 0.2f,
				ModelNames.BARREL.getFileName(), TextureNames.BARREL.getFileName());
	}

	public void update() {
		super.update();
		center.setPosition(movable.getPosition());
	}

	@Override
	public void exit() {
		Engine.removeParticleSystem((ParticleSystem) movable);
		Engine.removeEntity(center);
		new Explosion(movable.getPosition());
	}
}
