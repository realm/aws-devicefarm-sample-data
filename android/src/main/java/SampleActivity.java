package fi.aardsoft.devicefarmsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

public class SampleActivity extends Activity
{
    private static final String TAG = "DEVICEFARMSAMPLE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      TextView label = new TextView(this);

      Bundle extras = getIntent().getExtras();

      if (extras == null) {
        Log.v(TAG, "No extra arguments found");
        label.setText("No extra arguments");
      } else {
        // adb shell am start -e foo bar -n fi.aardsoft.devicefarmsample/.SampleActivity
        Log.v(TAG, "Extra arguments found");

        StringBuilder builder = new StringBuilder();
        builder.append("Extra arguments: ");

        for (String key : extras.keySet()) {
          builder.append(key + "=" + extras.get(key).toString());
        }

        label.setText(builder.toString());
      }

      setContentView(label);
    }
}
