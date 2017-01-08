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

  String tipusCavaller;

  private float angle;

  boolean esMort;
  private boolean remove;

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
    remove = false;

  }

  /**
   * @return the remove
   */
  public boolean isRemove() {
    return remove;
  }

  public Rectangle getPosicio() {
    return imatge.getBoundingRectangle();
  }

  public int getCentreX() {
    return (int) (imatge.getBoundingRectangle().x + imatge.getBoundingRectangle().width * 0.5);
  }

  public int getCentreY() {
    return (int) (imatge.getBoundingRectangle().y + imatge.getBoundingRectangle().height * 0.5);
  }

  public void mou(float delta) {
    float v = velocitat * delta;
    imatge.translate(-MathUtils.cosDeg(angle) * v, -MathUtils.sinDeg(angle) * v);
    // Canviar la rotació?
  }

  public void pinta(SpriteBatch batch) {
    if (esMort) {
      alpha -= 0.02f;
      if (alpha > 0) {
        alpha = 0f;
        remove = true;
      }
    }
    imatge.draw(batch, alpha);
  }

  public boolean isMort() {
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
