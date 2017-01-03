package net.xaviersala.pantalles;

import java.util.List;

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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Level;
import net.xaviersala.personatges.Marcador;

public class PantallaMenu extends Stage implements Screen {

  final PrincesetaGame joc;

  private final Texture fons;
  private final Texture start;

  public PantallaMenu(PrincesetaGame app) {

    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    joc = app;


    fons = joc.manager.get("fons.png", Texture.class);
    start = joc.manager.get("comensar.png", Texture.class);

    crearMenu();

    Gdx.app.log("Pantalla", "Entrant a Game Menu, level=0");
  }

  private void crearMenu() {



    final Level nivell = new Level(0);
    final List<String> enemics = nivell.obtenirEnemics();

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);


    Table taulaBotons  = new Table();
    taulaBotons.setFillParent(true);
    // taulaBotons.debug();

    final Image botoStart = new Image(start);
    botoStart.addListener(
        new InputListener() {

          @Override
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            botoStart.addAction(Actions.scaleTo(1.1f, 1.1f, .1f));
            return true;
          }

          @Override
          public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            botoStart.addAction(Actions.scaleTo(1f, 1f, .1f));

            joc.setScreen(new PantallaJoc(joc, new Marcador(), enemics, nivell.obtenirTots()));
            Gdx.input.setInputProcessor(null);
          }

        });

    // taulaBotons.left();
    // taulaBotons.add(explicacio).pad(120);
    taulaBotons.row();
    Label resultat = new Label("Mata els enemics\nde color:", joc.skin);
    taulaBotons.add(resultat);
    taulaBotons.row();

    for(String quin: nivell.obtenirEnemics()) {
      Label resultat2 = new Label(quin, joc.skin, "title-"+quin);
      taulaBotons.add(resultat2);

    }
    taulaBotons.row();
    taulaBotons.center();
    taulaBotons.add(botoStart).colspan(enemics.size());
    addActor(taulaBotons);

    taulaBotons.setWidth(Gdx.graphics.getWidth());


  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);

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
