package net.xaviersala.pantalles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
  Preferences preferencies;

  public PantallaGameOver(PrincesetaGame app) {

    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    this.joc = app;
    this.marcador = new Marcador();
    preferencies = Gdx.app.getPreferences("sirCavallers");
  }

  private void crearPantalla() {

    Texture derrota = joc.manager.get("derrota.png", Texture.class);
    Texture fons = joc.manager.get("fons.png", Texture.class);
    Texture restart = joc.manager.get("comensar.png", Texture.class);
    Texture sortir = joc.manager.get("sortir.png", Texture.class);
    Sound plora = joc.manager.get("sad.wav", Sound.class);

    plora.play();

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);

    Table taulaBase = new Table().center().pad(10);

    final Image derrotaImage = new Image(derrota);
    taulaBase.add(derrotaImage).colspan(2);
    taulaBase.setFillParent(true);

    taulaBase.row().colspan(2);
    Label resultat = new Label(marcador.getResultat(), joc.skin);
    taulaBase.add(resultat);
    taulaBase.row().height(100);

    final Image botoStart = new Image(restart);
    botoStart.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        botoStart.addAction(Actions.scaleTo(1.1f, 1.1f, .1f));
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        botoStart.addAction(Actions.scaleTo(1f, 1f, .1f));

        Gdx.input.setInputProcessor(null);
        joc.setScreen(joc.pantallaMenu);
      }

    });

    final Image botoSortir = new Image(sortir);
    botoSortir.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        botoSortir.addAction(Actions.scaleTo(1.1f, 1.1f, .1f));
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        botoSortir.addAction(Actions.scaleTo(1f, 1f, .1f));

        Gdx.input.setInputProcessor(null);
        Gdx.app.exit();
      }

    }

    );

    // Record?

    taulaBase.row();
    taulaBase.center();
    taulaBase.add(botoSortir).width(150).height(56);
    taulaBase.add(botoStart).width(150).height(56);

    addActor(taulaBase);

    taulaBase.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
    // En principi no cal guardar res. S'ha acabat la partida
  }

  @Override
  public void resume() {
    // No cal restaurar res...
  }

  @Override
  public void hide() {
    // He de guardar alguna cosa?
  }

  /**
   * Posa el marcador a la pantalla GameOver.
   *
   * @param marcador2: marcador actual
   */
  public void setMarcador(Marcador marcador2) {
    marcador = marcador2;
    int record = preferencies.getInteger("record", 0);
    if (marcador.getMorts() > record) {
      preferencies.putInteger("record", marcador.getMorts());
      preferencies.flush();
    }
  }

}
