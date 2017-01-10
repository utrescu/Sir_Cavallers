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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
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

    int record = preferencies.getInteger("record", 0);
    Texture derrota = joc.manager.get("derrota.png", Texture.class);
    Texture fons = joc.manager.get("fons-menu.png", Texture.class);
    Sound plora = joc.manager.get("sad.wav", Sound.class);
    I18NBundle texte = joc.manager.get("sir", I18NBundle.class);

    plora.play();

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);

    Table taulaBase = new Table();

    final Image derrotaImage = new Image(derrota);
    taulaBase.add(derrotaImage);

    taulaBase.row();
    Label resultat = new Label(texte.format("resultat", marcador.getMorts()), joc.skin);
    taulaBase.add(resultat).expandX();
    taulaBase.row().height(100);

    final TextButton botoStart = new TextButton(texte.get("jugar"), joc.skin);
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

    final TextButton botoSortir = new TextButton(texte.get("sortir"), joc.skin);
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

    float pos23W = 2 * Gdx.graphics.getWidth() / 3;
    float pos6W = Gdx.graphics.getWidth() / 6;
    float pos10H = Gdx.graphics.getHeight() / 10;
    float pos4H = Gdx.graphics.getHeight() * 0.25f;
    float pos13W = Gdx.graphics.getWidth() / 3;

    taulaBase.setPosition(pos13W, pos4H);
    taulaBase.setSize(pos23W, 3 * pos4H);

    addActor(taulaBase);

    Label millor = new Label(texte.format("record", record), joc.skin, "super");
    millor.setWrap(true);
    millor.setWidth(pos13W);
    millor.setPosition(25,  2 * pos4H);
    addActor(millor);

    botoStart.setSize(PrincesetaGame.BOTOSTARTWIDTH, PrincesetaGame.BOTOHEIGHT);
    botoStart.setPosition(pos23W - PrincesetaGame.BOTOSTARTWIDTH / 2, pos10H);
    addActor(botoStart);

    botoSortir.setSize(PrincesetaGame.BOTOSORTIRWIDTH, PrincesetaGame.BOTOHEIGHT);
    botoSortir.setPosition(pos6W - PrincesetaGame.BOTOSORTIRWIDTH / 2, pos10H);
    addActor(botoSortir);

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
   * @param marcador2:
   *          marcador actual
   */
  public void setMarcador(Marcador marcador2) {
    int record = preferencies.getInteger("record", 0);
    marcador = marcador2;
    if (marcador.getMorts() > record) {
      preferencies.putInteger("record", marcador.getMorts());
      preferencies.flush();
    }
  }

}
