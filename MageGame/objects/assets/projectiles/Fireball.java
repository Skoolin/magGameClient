package objects.assets.projectiles;

import loaders.PTKLoader;
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
		super(PTKLoader.loadPTKFile("fireball", position.x, position.y + 3, position.z), target, team, position);

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
