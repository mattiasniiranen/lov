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

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.util.Log
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*

object Permission {
    internal val permissionSubjects = HashMap<String, PublishSubject<AndroidPermission>>()
    internal val rationales = HashMap<String, PermissionRationale>()

    fun addRationale(permission: String, rationale: PermissionRationale) {
        rationales[permission] = rationale
    }

    /**
     * Requests permissions to be granted to [context].
     * If a permission is already granted it will not be requested.
     *
     * @param context The context to request permission in.
     * @param permissions The permissions to ask for.
     */
    fun request(context: Context, vararg permissions: String): Observable<AndroidPermission> {
        val results = mutableListOf<Observable<AndroidPermission>>()
        val unset = permissions.partition {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }.run {
            first.forEach {
                // Add all already granted permissions
                results.add(Observable.just(AndroidPermission(it, true, false)))
            }
            // Return all permissions that need to be requested
            return@run second
        }

        // Filter out ongoing requests
        val unrequested = unset.filter {
            permissionSubjects[it] == null
        }

        // Create/restart subjects
        unset.forEach {
            results.add(permissionSubjects.run {
                var subject = getOrPut(it, { PublishSubject.create<AndroidPermission>() })
                if (subject.hasCompleted()) {
                    subject = PublishSubject.create<AndroidPermission>()
                    put(it, subject)
                }
                return@run subject
            })
        }

        if (unrequested.isNotEmpty()) {
            val intent = Intent(context, PermissionActivity::class.java)
                    .putExtra(PermissionActivity.PERMISSIONS_KEY, unrequested.toTypedArray())
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.applicationContext.startActivity(intent)
        }

        return Observable.concat<AndroidPermission>(Observable.from(results))
    }

    /**
     * Runs [block] if all permissions are granted.
     */
    fun onPermissions(context: Context, vararg permissions: String, block: () -> Unit) {
        Permission.request(context, *permissions).all { it.granted }
                .subscribe({
                               if (it) {
                                   block()
                               }
                           },
                           {
                               Log.e("Permission", "Error requesting permission(s) $it", it)
                           })
    }
}

/**
 * Requests permissions to be granted to `this`.
 * If a permission is already granted it will not be requested.
 *
 * @param permissions The permissions to ask for.
 */
fun <T : Context> T.requestPermissions(vararg permissions: String): Observable<AndroidPermission> {
    return Permission.request(this, *permissions)
}

/**
 * Runs [block] if all permissions are granted.
 */
fun <T : Context> T.onPermissions(vararg permissions: String, block: () -> Unit) {
    Permission.onPermissions(this, *permissions, block = block)
}
