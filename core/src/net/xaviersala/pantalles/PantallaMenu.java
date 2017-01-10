package net.xaviersala.pantalles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import net.xaviersala.personatges.Level;
import net.xaviersala.personatges.Marcador;

public class PantallaMenu extends Stage implements Screen {

  private static final float CENTRARDRAC = 0.25f;
  private static final int PADDING = 10;
  private static final int MARGEDRAC = 25;
  private static final float DOSTERSOS = 0.66f;
  private static final float UNTERS = 0.33f;

  final PrincesetaGame joc;

  public PantallaMenu(PrincesetaGame app) {

    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    joc = app;

  }

  private void crearMenu() {

    Texture fons = joc.manager.get("fons-menu.png", Texture.class);
    Texture drac = joc.manager.get("drac.png", Texture.class);
    I18NBundle texte = joc.manager.get("sir", I18NBundle.class);

    final Level nivell = new Level(0);

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);

    Table taulaBase = new Table().center().pad(PADDING);

    final Image dracImage = new Image(drac);

    dracImage.setPosition(MARGEDRAC, Gdx.graphics.getHeight() * CENTRARDRAC);
    addActor(dracImage);

    Table taulaBotons = new Table();

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
        joc.pantallaJoc.inicialitza(new Marcador(), nivell);
        joc.setScreen(joc.pantallaJoc);
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

    taulaBotons.row().colspan(2);
    Label resultat = new Label(texte.get("objectiu"), joc.skin);
    taulaBotons.add(resultat);
    taulaBotons.row().height(100);

    int i = 0;
    for (String quin : nivell.obtenirEnemics()) {
      Label resultat2 = new Label(texte.get(quin), joc.skin, "title-" + quin);
      taulaBotons.add(resultat2);
      i++;
      if (i % 2 == 0) {
        taulaBotons.row();
      }
    }

    float pos23W = Gdx.graphics.getWidth() * DOSTERSOS;
    float pos6W = Gdx.graphics.getWidth() / 6;
    float pos10H = Gdx.graphics.getHeight() / 10;
    float pos4H = Gdx.graphics.getHeight() / 4;

    taulaBase.add(taulaBotons).width(pos23W);
    taulaBase.setPosition(Gdx.graphics.getWidth() * UNTERS, pos4H);
    taulaBase.setSize(pos23W, Gdx.graphics.getHeight());
    addActor(taulaBase);

    botoStart.setSize(PrincesetaGame.BOTOSTARTWIDTH, PrincesetaGame.BOTOHEIGHT);
    botoStart.setPosition(pos23W - PrincesetaGame.BOTOSTARTWIDTH / 2, pos10H);
    addActor(botoStart);

    botoSortir.setSize(PrincesetaGame.BOTOSORTIRWIDTH, PrincesetaGame.BOTOHEIGHT);
    botoSortir.setPosition(pos6W - PrincesetaGame.BOTOSORTIRWIDTH / 2, pos10H);
    addActor(botoSortir);

  }

  @Override
  public void show() {
    Gdx.app.log("Pantalla", "Entrant a Game Menu, level=0");
    Gdx.input.setInputProcessor(this);
    crearMenu();

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
    // Què fer al pausar

  }

  @Override
  public void resume() {
    // Què fer al continuar

  }

  @Override
  public void hide() {
    // Què fer quan s'amaga

  }

  @Override
  public void dispose() {
    super.dispose();
  }

}
