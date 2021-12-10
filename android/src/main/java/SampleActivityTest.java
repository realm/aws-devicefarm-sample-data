package fi.aardsoft.devicefarmsample;

import android.util.Log;

import android.os.Bundle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class SampleActivityTest {
    private static final String TAG = "DEVICEFARMTEST";
    private PrintStream logStream;

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(SampleActivity.class);

    @Before
    public void init() {
      Log.v(TAG, "Setting up test assets");

      File storageDir = new File (getApplicationContext().getExternalFilesDir(null).getAbsolutePath());

      if (!storageDir.exists())
        storageDir.mkdir();

      File logFile = new File(storageDir, "output.txt");

      try {
        logStream = new PrintStream(new FileOutputStream(logFile, true));
      } catch (IOException e) {
        e.printStackTrace();
      }

      logStream.println("***** Starting new test");
    }

    @After
    public void done() {
      Log.v(TAG, "Tearing down test assets");
      logStream.println("***** Finishing test");
      logStream.flush();
      logStream.close();
    }

    @Test
    public void logStorageDir() {
      Log.v(TAG, getApplicationContext().getExternalFilesDir(null).getAbsolutePath());
      logStream.println("Storage directory is" + getApplicationContext().getExternalFilesDir(null).getAbsolutePath());
    }

    @Test
    public void dumpExtraArgs() {
      Bundle extras = InstrumentationRegistry.getArguments();
      if (extras == null) {
        Log.v(TAG, "No extra arguments found");
        logStream.println("No extra arguments");
      } else {
        // adb shell am instrument -w -r -e foo bar fi.aardsoft.devicefarmsample
        Log.v(TAG, "Extra arguments found");

        StringBuilder builder = new StringBuilder();
        builder.append("Extra arguments: ");

        for (String key : extras.keySet()) {
          builder.append(key + "=" + extras.get(key).toString());
        }

        logStream.println(builder.toString());
      }
    }

    @Test
    public void logTestStartup() {
      Log.v(TAG, "Test started");
      try {
        logStream.print("Waiting...");
        Thread.sleep(1000);
        logStream.println("done");
      } catch(InterruptedException e) {
        Thread.currentThread().interrupt();
        logStream.println("interrupted");
      }
    }
}
