/*
 * Copyright 2016 Mattias Niiranen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.niiranen.permission.sample

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sample.*
import net.niiranen.permission.AndroidPermission
import net.niiranen.permission.Permission
import net.niiranen.permission.PermissionRationale
import net.niiranen.permission.requestPermissions

class SampleActivity : AppCompatActivity() {
    fun onPermissionResult(permission: AndroidPermission): Unit {
        if (permission.granted) {
            Toast.makeText(this, "Got permission ${permission.name}", Toast.LENGTH_LONG).show()
        } else if (permission.showRationale) {
            Toast.makeText(this, "Show rationale for ${permission.name}", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "${permission.name} was denied", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        Permission.addRationale(Manifest.permission.CAMERA,
                                PermissionRationale(R.string.camera_rationale_title,
                                                    R.string.rationale_ok,
                                                    R.string.rationale_cancel,
                                                    R.string.camera_rationale_message))
        Permission.addRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                PermissionRationale(R.string.storage_rationale_title,
                                                    R.string.rationale_ok,
                                                    R.string.rationale_cancel,
                                                    R.string.storage_rationale_message))

        fab.setOnClickListener({ view ->
                                   requestPermissions(Manifest.permission.CAMERA,
                                                      Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                           .subscribe({ onPermissionResult(it) },
                                                      { Log.e("Sample", "Error $it", it) })
                               })
    }
}
