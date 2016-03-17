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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LovTest {
    @Test fun addRationale() {
        Lov.addRationale("test", PermissionRationale(0, 0, 0, 0))
        assertEquals(PermissionRationale(0, 0, 0, 0), Lov.rationales["test"])
    }

    @Test fun removeRationale() {
        Lov.addRationale("test", PermissionRationale(0, 0, 0, 0))
        Lov.removeRationale("test")
        assertNull(Lov.rationales["test"])
    }

    @Test fun removeNonExistingRationale() {
        Lov.removeRationale("test")
        assertNull(Lov.rationales["test"])
    }

    @Test fun overwriteRationale() {
        Lov.addRationale("test", PermissionRationale(0, 0, 0, 0))
        Lov.addRationale("test", PermissionRationale(1, 2, 3, 4))
        assertEquals(PermissionRationale(1, 2, 3, 4), Lov.rationales["test"])
    }
}