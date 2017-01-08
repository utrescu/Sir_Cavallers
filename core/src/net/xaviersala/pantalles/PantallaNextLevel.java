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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Level;
import net.xaviersala.personatges.Marcador;

public class PantallaNextLevel extends Stage implements Screen {


  final PrincesetaGame joc;
  private Marcador marcador;

  Level nivell;

  public PantallaNextLevel(PrincesetaGame app) {
    super(new StretchViewport(PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA, new OrthographicCamera()));
    joc = app;

  }

  public void iniciarLevel(Marcador marcador) {
    Gdx.app.log("Pantalla", "Entrant a Game Next Level, level=" + marcador.getMorts() / Level.CANVI_LEVEL);
    this.marcador = marcador;
    nivell = new Level(marcador.getMorts() / Level.CANVI_LEVEL);
    crearPantalla();
  }

  private void crearPantalla() {

    Texture fons = joc.manager.get("fons.png", Texture.class);
    Texture victoria = joc.manager.get("victoria.png",Texture.class);
    Sound bravo = joc.manager.get("bravo.wav", Sound.class);
    I18NBundle texte = joc.manager.get("i18n/sir", I18NBundle.class);


    bravo.play();

    Image bg = new Image(fons);
    bg.setFillParent(true);
    addActor(bg);

    Table taulaBase = new Table().center().pad(10);
    final Image victoriaImage = new Image(victoria);
    taulaBase.add(victoriaImage);
    taulaBase.setFillParent(true);

    Table taulaBotons  = new Table();

    TextButton botoStart = new TextButton(texte.get("continuar"), joc.skin);
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
            joc.pantallaJoc.inicialitza(marcador, nivell);
            joc.setScreen(joc.pantallaJoc);

          }

        });

    taulaBotons.row().colspan(2);
    Label labelNivell = new Label(texte.format("nivell", nivell.getNumLevel()), joc.skin);
    taulaBotons.add(labelNivell);

    taulaBotons.row().colspan(2);
    Label resultat = new Label(texte.get("objectiu"), joc.skin);
    taulaBotons.add(resultat);
    taulaBotons.row().height(100);

    int i=0;
    for(String quin: nivell.obtenirEnemics()) {
      Label resultat2 = new Label(texte.get(quin), joc.skin, "title-"+quin);
      taulaBotons.add(resultat2);
      i++;
      if (i%2==0) {
        taulaBotons.row();
      }
    }

    taulaBotons.row();
    taulaBotons.center();
    taulaBotons.add(botoStart).width(150).height(56);

    taulaBase.add(taulaBotons);
    addActor(taulaBase);

    taulaBotons.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
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
    // pausar

  }
  @Override
  public void resume() {
    // restaurar

  }
  @Override
  public void hide() {
    // called when current screen changes from this to a different screen

  }

  @Override
  public void dispose() {
    super.dispose();
  }

}
