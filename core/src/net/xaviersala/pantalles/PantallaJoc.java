package net.xaviersala.pantalles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Cavaller;
import net.xaviersala.personatges.Level;
import net.xaviersala.personatges.Marcador;

public class PantallaJoc implements Screen {
  private static final int PROVABILITAT_CREAR = 3;

  private final PrincesetaGame joc;
  OrthographicCamera camera;
  private Rectangle pantalla;

  private List<Cavaller> cavallers;
  private List<String> enemics;
  private List<String> imatgesCavallers;

  private Sound dispara;
  private Sound tocat;
  private Sound noTocat;

  Marcador marcador;
  Preferences preferencies;

  private int numCavallers;

  private int record;

  public PantallaJoc(PrincesetaGame app) {
    this.joc = app;

    camera = new OrthographicCamera();
    pantalla = new Rectangle(0, 0, PrincesetaGame.AMPLEPANTALLA, PrincesetaGame.ALTPANTALLA);
    camera.setToOrtho(false, pantalla.getWidth(), pantalla.getHeight());

    cavallers = new ArrayList<Cavaller>();
    this.marcador = new Marcador();
    preferencies = Gdx.app.getPreferences("sirCavallers");

  }


  @Override
  public void show() {
    dispara = joc.manager.get("dispara.wav",Sound.class);
    tocat = joc.manager.get("tocat.wav",Sound.class);
    noTocat = joc.manager.get("tocat-no.wav",Sound.class);
  }


  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.741f, 0.659f, 0.471f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    creaCavallers();
    moureCavallers(delta);
    camera.update();
    joc.batch.setProjectionMatrix(camera.combined);

    joc.batch.begin();

    for (Cavaller cavaller: cavallers) {
      cavaller.pinta(joc.batch);
    }


    joc.font.draw(joc.batch, marcador.getText() + " - Record:" + record, 100, PrincesetaGame.ALTPANTALLA);
    joc.batch.end();


    if(Gdx.input.justTouched()) {
      dispara.play();
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);

      for(int i=cavallers.size()-1; i>=0; i--) {
        if (cavallers.get(i).isTocat(touchPos.x, touchPos.y)) {
          cavallers.get(i).setMort(true);
          incrementaMarcador(cavallers.get(i).getTipusCavaller());
          break;
        }
      }
   }
  }


  private void incrementaMarcador(String tipusCavaller) {
    if (enemics.indexOf(tipusCavaller) != -1) {
      tocat.play();
      marcador.addMort();
      if ((marcador.getMorts() % Level.CANVI_LEVEL) == 0) {
        joc.pantallaNext.iniciarLevel(marcador);
        joc.setScreen(joc.pantallaNext);
      }
    } else {
      noTocat.play();
      marcador.addErrors();
      comprovaSiSAcaba();
    }
  }


  private void comprovaSiSAcaba() {
    if (marcador.fallades() == 10) {
      joc.pantallaGameOver.setMarcador(marcador);
      joc.setScreen(joc.pantallaGameOver);
    }
  }


  private void moureCavallers(float delta) {

    Iterator<Cavaller> iter = cavallers.iterator();
    while(iter.hasNext()) {
      Cavaller cavaller = iter.next();
      if (cavaller.isMort()) {
        iter.remove();
      } else {
        cavaller.mou(delta);
        if (foraDePantalla(cavaller)) {
          cavaller.setMort(true);// Comprova si se n'ha anat de la pantalla
          if (enemics.indexOf(cavaller.getTipusCavaller()) != -1) {
            noTocat.play();
            marcador.addEscapats();
            comprovaSiSAcaba();
          }
        }
      }

    }
  }


  private boolean foraDePantalla(Cavaller cavaller) {
    if (!pantalla.overlaps(cavaller.getPosicio())) {
      return true;
    }
    return false;
  }


  private Cavaller creaCavaller(String tipusCavaller, int x, int y) {
    Texture imatge = joc.manager.get(tipusCavaller + ".png", Texture.class);
    return new Cavaller(imatge, tipusCavaller, x, y);
  }


  private void creaCavallers() {
      int crea = MathUtils.random(0, 100);

      if (crea < PROVABILITAT_CREAR) {

        String quin = imatgesCavallers.get(MathUtils.random(0,numCavallers-1));
        int costat = MathUtils.random(0,1) * PrincesetaGame.AMPLEPANTALLA;
        int fila = (int) MathUtils.random(0,
            pantalla.getHeight() - joc.manager.get(quin+".png",Texture.class).getHeight());

        Cavaller c = creaCavaller(quin, costat, fila);
        cavallers.add(0, c);
        Collections.sort(cavallers);
      }
  }


  @Override
  public void resize(int width, int height) {
    // resize?

  }

  @Override
  public void pause() {
    // pause

  }

  @Override
  public void resume() {
    // resume?

  }

  @Override
  public void hide() {
    // hide?

  }

  @Override
  public void dispose() {
    cavallers.clear();
  }


  public void inicialitza(Marcador marcador2, Level nivell) {
    marcador = marcador2;
    this.enemics = nivell.obtenirEnemics();
    imatgesCavallers = nivell.obtenirTots();
    numCavallers = imatgesCavallers.size();
    record = preferencies.getInteger("record",0);
    cavallers.clear();
  }

}
