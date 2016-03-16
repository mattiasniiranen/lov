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

/**
 * Data class representing a permission status.
 * If [granted] and [showRationale] is both false the user most likely checked 'Never ask again'.
 *
 * @param name The name of the permission.
 * @param granted `true` if the permission vas granted.
 * @param showRationale `true` if permission rationale should be shown.
 */
data class AndroidPermission(val name: String, val granted: Boolean, val showRationale: Boolean)
