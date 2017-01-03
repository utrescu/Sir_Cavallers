package net.xaviersala;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.xaviersala.pantalles.PantallaSplash;

public class PrincesetaGame extends Game {

  public static final int ALTPANTALLA = 480;
  public static final int AMPLEPANTALLA = 800;

  public SpriteBatch batch;
  public BitmapFont font;
  public AssetManager manager;
  public Skin skin;

  Texture img;

  @Override
  public void create () {
    batch = new SpriteBatch();
    font = new BitmapFont();
    manager = new AssetManager();
    skin = new Skin(Gdx.files.internal("skin.json"));
    this.setScreen(new PantallaSplash(this));
  }

  @Override
  public void render () {
    super.render();
  }

  @Override
  public void dispose () {
    batch.dispose();
    font.dispose();
  }
}
