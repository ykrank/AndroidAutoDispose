# AndroidAutoDispose

[中文说明][chinese-readme]

Easy way to auto dispose rxjava2 with android lifecycle event

**AndroidLifeCycle** is an android tool for auto dippose rxjava2 disposable when target android lifecycle event arrive. 

Overview
--------
Work with [AndroidLifecycle][android-lifecycle] and [AutoDispose][autodispose], auto dispose with bound fragment, activity, context or view lifecycle event.

### Motivations
Lifecycle management with RxJava and Android is nothing new, so why yet another tool?
You can see this in uber [AutoDispose][autodispose] readme #Motivations.
In short, you should add disposable manually, or use [RxLifecycle][RxLifecycle]. Previous not elegant. To use RxLifecycle correct, compose() needed to be as close to the subscribe() call as possible to properly wrap upstream. And Single or Completable dispose always throw CancellationException, you should always remember handle it. This limit make it was risky to use (particularly in a large team with varying levels of RxJava experience).
But in this library, you can only listen event at flow last, you can also register global outsideLifecycleExcepitonHandler by AutoDisposePlugins.setOutsideLifecycleHandler, ignore lots of risk.


### Usage
Bind myObservable with myFragment destroy event
```
myObservable
    .doStuff()
    .to(AndroidRxDispose.withObservable(myFragment, FragmentEvent.DESTROY))   // The scope
    .subscribe(s -> ...);
```

Bind mySingle with myView attached activity pause event
```
mySingle
    .doStuff()
    .to(AndroidRxDispose.withSingle(myView, ViewEvent.PAUSE))   // The scope
    .subscribe(s -> ...);
```

Bind myCompletable with myView attached fragment stop event
```
AndroidLifeCycle.bindFragment(myView, myFragment);

myCompletable
    .doStuff()
    .to(AndroidRxDispose.withCompletable(myView, ViewEvent.STOP))   // The scope
    .subscribe(s -> ...);
```

Support Rxjava2 Observable, Single, Completable, Maybe, Flowable

Download
--------
[![Maven Central](https://img.shields.io/maven-central/v/com.github.ykrank/androidautodispose.svg)](https://mvnrepository.com/artifact/com.github.ykrank/androidautodispose)

```gradle
compile 'com.github.ykrank:androidautodispose:x.y.z'
```

License
-------
    Copyright (C) 2017 Yk Rank

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[android-lifecycle]: https://github.com/ykrank/AndroidLifeCycle
[autodispose]: https://github.com/uber/AutoDispose
[chinese-readme]: https://github.com/ykrank/AndroidAutoDispose/blob/master/README-ZH.md
[RxLifecycle]:https://github.com/trello/RxLifecycle/