package dev.plagarizers.klotski.ios;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import dev.plagarizers.klotski.Klotski;

/**
 * Launches the iOS (RoboVM) application.
 */
public class IOSLauncher extends IOSApplication.Delegate {
  @Override
  protected IOSApplication createApplication() {
    IOSApplicationConfiguration configuration = new IOSApplicationConfiguration();
    return new IOSApplication(new Klotski(), configuration);
  }

  public static void main(String[] argv) {
    NSAutoreleasePool pool = new NSAutoreleasePool();
    UIApplication.main(argv, null, IOSLauncher.class);
    pool.close();
  }
}
