package net.xaviersala.pantalles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Marcador;

public class PantallaGameOver extends Stage implements Screen {

  final PrincesetaGame joc;
  private Marcador marcador;

  private Texture fons;
  private Texture restart;
  private Sound plora;

  public PantallaGameOver(PrincesetaGame app) {

    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    this.joc = app;
    this.marcador = new Marcador();



  }

  private void crearPantalla() {

    fons = joc.manager.get("preso.png", Texture.class);
    restart = joc.manager.get("comensar.png", Texture.class);
    plora = joc.manager.get("sad.wav", Sound.class);

    plora.play();

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);

    Table taula = new Table();
    taula.center();
    Label resultat = new Label(marcador.getResultat(), joc.skin);
    taula.add(resultat);
    taula.center();
    taula.row();

    taula.row();

    final Image botoRestart = new Image(restart);
    botoRestart.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        botoRestart.addAction(Actions.scaleTo(1.1f, 1.1f, .1f));
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        botoRestart.addAction(Actions.scaleTo(1f, 1f, .1f));
        Gdx.input.setInputProcessor(null);
        joc.setScreen(joc.pantallaMenu);

      }
    });

    taula.add(botoRestart);
    taula.setBounds(0, 0, PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.AMPLEPANTALLA / 3);

    addActor(taula);

  }

  @Override
  public void show() {
    Gdx.app.log("Pantalla", "Entrant a Game Over");
    Gdx.input.setInputProcessor(this);
    crearPantalla();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.act(delta);
    super.draw();

  }

  @Override
  public void resize(int width, int height) {
    getViewport().update(width, height);

  }

  @Override
  public void pause() {
    // Pause?

  }

  @Override
  public void resume() {
    // Resume?

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    super.dispose();
  }

  public void setMarcador(Marcador marcador2) {
    marcador = marcador2;
  }

}
