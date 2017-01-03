package net.xaviersala.personatges;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Cavaller implements Comparable<Cavaller> {
  Sprite imatge;

  int velocitat;
  float alpha;
  boolean esMort;
  String tipusCavaller;

  private float angle;

  public Cavaller(Texture imatge, String tipusCavaller, float x, float y) {

    this.imatge = new Sprite(imatge);
    this.tipusCavaller = tipusCavaller;

    this.imatge.setPosition(x, y);
    alpha = 1.0f;

    velocitat = 125 + MathUtils.random(0, 50);

    if (x < 1) {
      angle = 180;
    } else {
      angle = 0;
      this.imatge.flip(true, false);
    }

    this.imatge.setOriginCenter();

    esMort = false;

  }

  public Rectangle getPosicio() {
    return imatge.getBoundingRectangle();
  }

  public void mou(float delta) {
    float v = velocitat * delta;
    imatge.translate(-MathUtils.cosDeg(angle) * v, -MathUtils.sinDeg(angle) * v);
    // Canviar la rotació?
  }

  public void pinta(SpriteBatch batch) {
    // batch.draw(imatge, posicio.x, posicio.y);
    if (esMort) {
      alpha -= 0.02f;
      if (alpha < 0) {
        alpha = 0f;
      }
    }
    imatge.draw(batch, alpha);
  }

  public boolean isMort() {
    // return (esMort && alpha == 0f);
    return esMort;
  }

  public void setMort(boolean siONo) {
    esMort = siONo;

  }

  public boolean isTocat(float x, float y) {
    return getPosicio().contains(x, y);
  }

  /**
   * @return the tipusCavaller
   */
  public String getTipusCavaller() {
    return tipusCavaller;
  }

  @Override
  public int compareTo(Cavaller o) {
    return (int) (o.getPosicio().y - getPosicio().y);
  }

}
