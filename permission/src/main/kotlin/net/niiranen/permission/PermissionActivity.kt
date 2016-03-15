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

package net.niiranen.permission

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

class PermissionActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 0

    companion object {
        internal val PERMISSIONS_KEY = "permissions"
    }

    private var requestPermissions: Array<out String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions = intent?.extras?.getStringArray(PERMISSIONS_KEY)
        if (requestPermissions == null) {
            // Nothing for us to do
            finish()
        }
        ActivityCompat.requestPermissions(this, requestPermissions!!, PERMISSION_REQUEST_CODE)
    }

    override fun onDestroy() {
        requestPermissions?.forEach {
            Permission.permissionSubjects.remove(it)?.onCompleted()
        }
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return
        }
        for (i in 0..permissions.size - 1) {
            Permission.permissionSubjects.remove(permissions[i])?.apply {
                onNext(AndroidPermission(permissions[i],
                                         grantResults[i] == PackageManager.PERMISSION_GRANTED,
                                         ActivityCompat.shouldShowRequestPermissionRationale(
                                                 this@PermissionActivity, permissions[i])
                                        ))
                onCompleted()
            }
        }
        finish()
    }
}
