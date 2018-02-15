package objects.assets.effects;

import gameObjects.GameObject;
import loaders.PTKLoader;
import org.lwjgl.util.vector.Vector3f;
import particles.ParticleSystem;
import renderEngine.DisplayManager;
import renderEngine.Engine;

/**
 * Created by skolin on 15.02.18.
 */
public class Sparkles extends GameObject {

    private float timer;
    private static final float lifeLength = 0.3f;

    public Sparkles(Vector3f position) {
        super(PTKLoader.loadPTKFile("sparkles", position.x, position.y + 3f, position.z));
        timer = 0f;
    }

    @Override
    public void update() {
        if (timer > lifeLength) {
            destroy();
        } else {
            timer += DisplayManager.getFrameTime();
        }
    }

    @Override
    public void exit() {
        Engine.removeParticleSystem((ParticleSystem) movable);
    }

}