package net.xaviersala.personatges;

public class Marcador {

  int morts;
  int escapats;
  int errors;

  public Marcador() {
    morts = 0;
    escapats = 0;
    errors = 0;
  }

  public void addMort() {
    morts++;
  }

  public void addEscapats() {
    escapats++;
  }

  public void addErrors() {
    errors++;
  }

  /**
   * Retorna el número de fallades total
   * @return escapats més errors
   */
  public int fallades() {
    return errors + escapats;
  }

  /**
   * @return the escapats
   */
  public int getEscapats() {
    return escapats;
  }

  /**
   * @return the errors
   */
  public int getErrors() {
    return errors;
  }

  public int getMorts() {
    return morts;
  }

}
