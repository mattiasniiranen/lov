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

package net.niiranen.lov

import android.Manifest
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class LovInstrumentationTest {
    val activity = ActivityTestRule(TestActivity::class.java)
    @Rule fun activityRule() = activity

    lateinit var device: UiDevice

    @Before fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test fun a_refusePermission() {
        val latch = CountDownLatch(1)
        Lov.request(activity.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                        {
                            assertEquals(AndroidPermission(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    false, true), it)
                        },
                        { throw it },
                        { latch.countDown() })
        device.denyCurrentPermission()
        latch.await(30, TimeUnit.SECONDS)
    }

    @Test fun b_showRationale() {
        val latch = CountDownLatch(1)
        Lov.addRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                         PermissionRationale(android.R.string.dialog_alert_title,
                                             android.R.string.ok,
                                             android.R.string.no,
                                             android.R.string.untitled))
        Lov.request(activity.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                        {
                            assertEquals(AndroidPermission(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    false, true), it)
                        },
                        { throw it },
                        { latch.countDown() })
        device.findObject(UiSelector().text(activity.activity.getString(android.R.string.ok)))
                .click()
        device.denyCurrentPermission()
        latch.await(30, TimeUnit.SECONDS)
        // Remove rationale so other tests don't have to deal with it
        Lov.removeRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @Test fun c_acceptPermission() {
        val latch = CountDownLatch(1)
        Lov.request(activity.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                        {
                            assertEquals(AndroidPermission(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    true, false), it)
                        },
                        { throw it },
                        { latch.countDown() })
        device.allowCurrentPermission()
        latch.await(30, TimeUnit.SECONDS)
    }
}