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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Level;
import net.xaviersala.personatges.Marcador;

public class PantallaMenu extends Stage implements Screen {

  final PrincesetaGame joc;

  public PantallaMenu(PrincesetaGame app) {

    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    joc = app;

  }

  private void crearMenu() {

    Texture fons = joc.manager.get("fons.png", Texture.class);
    Texture start = joc.manager.get("comensar.png", Texture.class);
    Texture sortir = joc.manager.get("sortir.png", Texture.class);
    Texture drac = joc.manager.get("drac.png", Texture.class);

    final Level nivell = new Level(0);

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);


    Table taulaBase = new Table().center().pad(10);
    final Image dracImage = new Image(drac);
    taulaBase.add(dracImage);
    taulaBase.setFillParent(true);

    Table taulaBotons  = new Table();

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

            Gdx.input.setInputProcessor(null);
            joc.pantallaJoc.inicialitza(new Marcador(), nivell);
            joc.setScreen(joc.pantallaJoc);
          }

        });

    final Image botoSortir = new Image(sortir);
    botoSortir.addListener(
        new InputListener() {

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
    Label resultat = new Label("Mata els enemics de color:", joc.skin);
    taulaBotons.add(resultat);
    taulaBotons.row().height(100);

    int i=0;
    for(String quin: nivell.obtenirEnemics()) {
      Label resultat2 = new Label(quin, joc.skin, "title-"+quin);
      taulaBotons.add(resultat2);
      i++;
      if (i%2==0) {
        taulaBotons.row();
      }
    }

    taulaBotons.row();
    taulaBotons.center();
    taulaBotons.add(botoSortir).width(150).height(56);
    taulaBotons.add(botoStart).width(150).height(56);

    taulaBase.add(taulaBotons);
    addActor(taulaBase);

    taulaBase.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
