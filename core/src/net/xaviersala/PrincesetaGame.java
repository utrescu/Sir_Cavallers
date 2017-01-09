package net.xaviersala;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.xaviersala.pantalles.PantallaGameOver;
import net.xaviersala.pantalles.PantallaJoc;
import net.xaviersala.pantalles.PantallaMenu;
import net.xaviersala.pantalles.PantallaNextLevel;
import net.xaviersala.pantalles.PantallaSplash;

public class PrincesetaGame extends Game {

  public static final int ALTPANTALLA = 480;
  public static final int AMPLEPANTALLA = 800;
  public static final int BOTOHEIGHT = 50;
  public static final int BOTOSORTIRWIDTH = 125;
  public static final int BOTOSTARTWIDTH = 200;

  public SpriteBatch batch;
  public BitmapFont font;
  public AssetManager manager;
  public Skin skin;

  public PantallaMenu pantallaMenu;
  public PantallaNextLevel pantallaNext;
  public PantallaJoc pantallaJoc;
  public PantallaGameOver pantallaGameOver;

  Texture img;

  @Override
  public void create () {
    batch = new SpriteBatch();
    font = new BitmapFont();
    manager = new AssetManager();

    manager.load("botons.atlas", TextureAtlas.class);
    manager.finishLoading();
    TextureAtlas atlas = manager.get("botons.atlas", TextureAtlas.class);
    skin = new Skin(Gdx.files.internal("skin.json"), atlas);

    pantallaMenu = new PantallaMenu(this);
    pantallaNext = new PantallaNextLevel(this);
    pantallaGameOver = new PantallaGameOver(this);
    pantallaJoc = new PantallaJoc(this);

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
    manager.clear();
  }


}
