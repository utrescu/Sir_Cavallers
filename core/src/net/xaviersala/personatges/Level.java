package net.xaviersala.personatges;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Level {

  private final int numLevel;

  List<String> enemics;
  List<String> tots;

  public static final List<String> SOLDATS = Arrays.asList(
      "groc", "vermell", "blau", "negre", "verd", "taronja", "blanc", "lila"
      );
  public static final int CANVI_LEVEL = 25;
  public static final int INCREMENTA_ENEMICS = 4;
  private static final int MAXCOLORS = 6;

  public Level(int num) {

    numLevel = num;
    List<String> disorder = SOLDATS;
    Collections.shuffle(disorder);


    enemics = disorder.subList(0, Math.min(MAXCOLORS, num / INCREMENTA_ENEMICS) +1);
    tots = disorder.subList(0, Math.min(enemics.size() + 1, SOLDATS.size()));
  }

  /**
   * @return the numLevel
   */
  public int getNumLevel() {
    return numLevel;
  }

  public List<String> obtenirEnemics() {
    return enemics;
  }

  public List<String> obtenirTots() {
    return tots;
  }
}
