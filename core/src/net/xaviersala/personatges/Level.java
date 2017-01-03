package net.xaviersala.personatges;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;

public class Level {

  List<String> enemics;
  List<String> tots;

  public static final List<String> SOLDATS = Arrays.asList(
      "groc", "vermell", "blau", "negre", "verd", "taronja", "blanc"
      );
  public static final int CANVI_LEVEL = 25;
  public static final int INCREMENTA_ENEMICS = 5;

  public Level(int num) {

    List<String> disorder = SOLDATS;
    Collections.shuffle(disorder);


    enemics = disorder.subList(0, Math.min(SOLDATS.size() - 1, num / INCREMENTA_ENEMICS) +1);
    tots = disorder.subList(0, Math.min(enemics.size() + 1, SOLDATS.size()));
  }

  public List<String> obtenirEnemics() {
    return enemics;
  }

  public List<String> obtenirTots() {
    return tots;
  }
}
