package net.xaviersala.pantalles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Level;

public class PantallaSplash extends Stage implements Screen {

  PrincesetaGame joc;
  private boolean timerIsOn;
  private Texture splsh;

  private static final String[] NOM_IMATGES = { "fons", "comensar", "mes", "preso" };
  private static final String[] SONS = { "dispara", "tocat", "tocat-no", "bravo", "sad" };

  public PantallaSplash(PrincesetaGame game) {

    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    joc = game;
    splsh = new Texture(Gdx.files.internal("sir.png"));
    Image bg = new Image(splsh);
    bg.setFillParent(true);

    addActor(bg);

    for(String imatge: NOM_IMATGES) {
      joc.manager.load(imatge + ".png", Texture.class);
    }

    for(String imatge: Level.SOLDATS) {
      joc.manager.load(imatge + ".png", Texture.class);
    }

    for(String so: SONS) {
      joc.manager.load(so + ".wav", Sound.class);
    }
    joc.manager.finishLoading();


  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    super.act(delta);
    super.draw();

    if (!timerIsOn) {
      timerIsOn = true;

      Timer.schedule(new Task() {

        @Override
        public void run() {
          comensarPartida();
        }

      }, 2);

    } else if (Gdx.input.isTouched()) {
      // Remove the task so we don't call changeScreen twice:
      Timer.instance().clear();
      comensarPartida();
    }

  }

  private void comensarPartida() {
    joc.setScreen(new PantallaMenu(joc));
  }

  @Override
  public void resize(int width, int height) {
    getViewport().update(width, height);
  }

  @Override
  public void pause() {
    // pause

  }

  @Override
  public void resume() {
    // resume

  }

  @Override
  public void hide() {
    // Hide
  }

  @Override
  public void dispose() {
    splsh.dispose();

  }

}
