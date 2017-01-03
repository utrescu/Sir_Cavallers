package net.xaviersala;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import net.xaviersala.PrincesetaGame;

public class AndroidLauncher extends AndroidApplication {
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    // Estalvi energètic
    config.useAccelerometer = false;
    config.useCompass = false;
    initialize(new PrincesetaGame(), config);
  }
}
