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

import android.support.annotation.StringRes

/**
 * Defines how to show a rationale for a permission.
 *
 * @property [title] The title of the dialog.
 * @property [positive] The text on the positive button.
 * @property [negative] The text on the negative button.
 * @property [message] The body text of the dialog, should contain the rationale.
 */
data class PermissionRationale(@StringRes val title: Int, @StringRes val positive: Int,
                               @StringRes val negative: Int, @StringRes val message: Int)
