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

Show rationale when needed:
```kotlin
Permission.addRationale(permission,
                        PermissionRationale(R.string.rationale_title,
                                            R.string.rationale_ok,
                                            R.string.rationale_cancel,
                                            R.string.rationale_message))
```

Calling from java:
```java
Permission.INSTANCE.addRationale(Manifest.permission.CAMERA,
                                 new PermissionRationale(R.string.camera_rationale_title,
                                                         R.string.rationale_ok,
                                                         R.string.rationale_cancel,
                                                         R.string.camera_rationale_message));

Permission.INSTANCE.request(context, permissions).subscribe(
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
