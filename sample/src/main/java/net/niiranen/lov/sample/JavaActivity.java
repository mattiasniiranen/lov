package net.niiranen.lov.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.niiranen.lov.AndroidPermission;
import net.niiranen.lov.Lov;
import net.niiranen.lov.PermissionRationale;

import rx.functions.Action1;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Button button = (Button) findViewById(R.id.button);
        assert button != null;


        Lov.INSTANCE.addRationale(Manifest.permission.CAMERA,
                                  new PermissionRationale(R.string.camera_rationale_title,
                                                          R.string.rationale_ok,
                                                          R.string.rationale_cancel,
                                                          R.string.camera_rationale_message));
        Lov.INSTANCE.addRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                  new PermissionRationale(R.string.storage_rationale_title,
                                                          R.string.rationale_ok,
                                                          R.string.rationale_cancel,
                                                          R.string.storage_rationale_message));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lov.INSTANCE.request(JavaActivity.this,
                                     Manifest.permission.CAMERA,
                                     Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(
                    new Action1<AndroidPermission>() {
                        @Override
                        public void call(AndroidPermission permission) {
                            if (permission.getGranted()) {
                                Toast.makeText(JavaActivity.this,
                                               "Got permission for " + permission.getName(),
                                               Toast.LENGTH_LONG)
                                     .show();
                            } else if (permission.getShowRationale()) {
                                Toast.makeText(JavaActivity.this,
                                               "Show rationale for " + permission.getName(),
                                               Toast.LENGTH_LONG)
                                     .show();
                            } else {
                                Toast.makeText(JavaActivity.this,
                                               permission.getName() + " was denied",
                                               Toast.LENGTH_LONG)
                                     .show();
                            }
                        }
                    }
                );
            }
        });
    }
}
