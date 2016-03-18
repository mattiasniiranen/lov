Lov
===
Lov<sup>[loːv]</sup> is a library aimed at making permission requests on Android simple.

Usage
=====
Using the object directly:
```kotlin
Lov.requestPermissions(context, permissions)
        .subscribe({ if (it.granted) { /* Permission was granted */ } })
```
Or use the Context extension method to drop the `Lov`.

Show rationale when needed:
```kotlin
Lov.addRationale(permission,
                 PermissionRationale(R.string.rationale_title,
                                     R.string.rationale_ok,
                                     R.string.rationale_cancel,
                                     R.string.rationale_message))
```

Of course you can call Lov from Java, just use `Lov.INSTANCE`.

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
