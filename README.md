Permission
==========
A reactive extension permission requester for Android, written with Kotlin.

Usage
=====
Using the object directly:
```kotlin
Permission.requestPermissions(context, permissions)
                   .subscribe({ if (it.granted) { /* Permission was granted */ } })
```

Or with the Context extension method:
```kotlin
requestPermissions(permissions)
        .subscribe({ if (it.granted) { /* Permission was granted */ } })
```

License
=======
    Copyright 2016 Mattias Niiranen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
