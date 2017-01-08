package net.xaviersala.personatges;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Foc {
  ParticleEffect efecte;

  public Foc(ParticleEffect efect, int x, int y) {
    efecte = efect;
    efecte.getEmitters().first().setPosition(x, y);
    efecte.start();
  }

  public void mou(float delta) {
    efecte.update(delta);
  }

  public void pinta(SpriteBatch batch) {
    efecte.draw(batch);
  }

  public boolean isAcabat() {
    return efecte.isComplete();
  }

}
