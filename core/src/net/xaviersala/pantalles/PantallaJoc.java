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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;

import net.xaviersala.PrincesetaGame;
import net.xaviersala.personatges.Cavaller;
import net.xaviersala.personatges.Foc;
import net.xaviersala.personatges.Level;
import net.xaviersala.personatges.Marcador;

public class PantallaJoc implements Screen {
  private static final int PROVABILITAT_CREAR = 3;

  private final PrincesetaGame joc;
  OrthographicCamera camera;
  private Rectangle pantalla;

  private List<Cavaller> cavallers;
  private List<Foc> focs;
  private List<String> enemics;
  private List<String> imatgesCavallers;

  private Texture fons;
  private Sound dispara;
  private Sound tocat;
  private Sound noTocat;
  private I18NBundle texte;

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
    focs = new ArrayList<Foc>();
    this.marcador = new Marcador();
    preferencies = Gdx.app.getPreferences("sirCavallers");

  }


  @Override
  public void show() {
    dispara = joc.manager.get("dispara.wav",Sound.class);
    tocat = joc.manager.get("foc.wav",Sound.class);
    noTocat = joc.manager.get("tocat-no.wav",Sound.class);
    texte = joc.manager.get("sir", I18NBundle.class);
    fons = joc.manager.get("fons.png",Texture.class);
  }


  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.741f, 0.659f, 0.471f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    creaCavallers();
    moureCavallers(delta);
    moureFocs(delta);
    camera.update();
    joc.batch.setProjectionMatrix(camera.combined);

    joc.batch.begin();
    joc.batch.draw(fons, 0f, 0f);
    for (Cavaller cavaller: cavallers) {
      cavaller.pinta(joc.batch);
    }

    for(Foc foc: focs) {
      foc.pinta(joc.batch);
    }

    CharSequence resultat = texte.format("marcador", marcador.getMorts(), marcador.getEscapats(),
        marcador.getErrors(), record);
    joc.font.draw(joc.batch, resultat, 100, PrincesetaGame.ALTPANTALLA);
    joc.batch.end();


    if(Gdx.input.justTouched()) {
      dispara.play();
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);

      for(int i=cavallers.size()-1; i>=0; i--) {
        if (cavallers.get(i).isTocat(touchPos.x, touchPos.y)) {
          cavallers.get(i).setMort(true);
          int posX = cavallers.get(i).getCentreX();
          int posY = cavallers.get(i).getCentreY();
          focs.add(creaFoc(posX, posY));
          incrementaMarcador(cavallers.get(i).getTipusCavaller());
          break;
        }
      }
   }
  }


  private void moureFocs(float delta) {
    Iterator<Foc> iter = focs.iterator();
    while(iter.hasNext()) {
      Foc foc = iter.next();
      if (foc.isAcabat()) {
        iter.remove();
      } else {
        foc.mou(delta);
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
    }
  }


  private void comprovaSiSAcaba() {
    if (marcador.getFallades() >= 10) {
      joc.pantallaGameOver.setMarcador(marcador);
      joc.setScreen(joc.pantallaGameOver);
    }
  }


  private void moureCavallers(float delta) {

    Iterator<Cavaller> iter = cavallers.iterator();
    while(iter.hasNext()) {
      Cavaller cavaller = iter.next();
      if (cavaller.isMort()) {
        if (cavaller.isRemove()) {
          iter.remove();
        }
      } else {
        cavaller.mou(delta);
        if (foraDePantalla(cavaller)) {
          cavaller.setMort(true);// Comprova si se n'ha anat de la pantalla
          if (enemics.indexOf(cavaller.getTipusCavaller()) != -1) {
            noTocat.play();
            iter.remove();
            marcador.addEscapats();
          }
        }
      }

    }
    comprovaSiSAcaba();
  }


  private boolean foraDePantalla(Cavaller cavaller) {
    if (!pantalla.overlaps(cavaller.getPosicio())) {
      return true;
    }
    return false;
  }

  private Foc creaFoc(int x, int y) {
    ParticleEffect e = joc.manager.get("foc.party", ParticleEffect.class);
    return new Foc(e, x, y);
  }

  private Cavaller creaCavaller(String tipusCavaller, int x, int y) {
    TextureAtlas imatge = joc.manager.get(tipusCavaller + ".atlas", TextureAtlas.class);
    return new Cavaller(imatge, tipusCavaller, x, y);
  }


  private void creaCavallers() {
      int crea = MathUtils.random(0, 100);

      if (crea < PROVABILITAT_CREAR) {

        String quin = imatgesCavallers.get(MathUtils.random(0,numCavallers-1));
        int costat = MathUtils.random(0,1) * PrincesetaGame.AMPLEPANTALLA;
        int fila = (int) MathUtils.random(0,
            pantalla.getHeight() - 84);
        // TODO: Obtenir altura de l'sprite en comptes de posar 84

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
    cavallers.clear();
    focs.clear();

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
