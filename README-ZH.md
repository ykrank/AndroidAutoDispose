# AndroidAutoDispose
一个随Android生命周期事件自动解绑Rxjava订阅的方便工具。

概述
--------
应用了 [AndroidLifecycle][android-lifecycle] 和 [AutoDispose][autodispose], 随绑定的Fragment, Activity, Context 和View的生命周期事件来自动解绑Rxjava2订阅。

### 动机
管理Rxjava和Android的生命周期并不是新鲜事，那么为什么要开发这个库呢？
对于这个问题，详细情况可以看Uber 的[AutoDispose][autodispose] readme #Motivations.
简单来说，你需要手动添加disposable，或者使用[RxLifecycle][RxLifecycle]。前者并不优雅。而为了正确的使用RxLifecycle，compose()必须离subscribe()调用越近越好，以将之前的操作流包装进来。同时，Single和Completable的解绑将一直引起CancellationException， 你必须总要记得处理这种异常。这些限制让使用它变得有一定风险（特别是在多人项目里面，和不同Rxjava基础的人一同使用时）。
但在这个库中，你只能在订阅的最终阶段才可以监听事件，同时，你也能通过 AutoDisposePlugins.setOutsideLifecycleHandler添加全局的生命事件异常处理，避免了很多使用上的风险。

### 示例
将 myObservable 的解绑和 myFragment 的 destroy 事件绑定
```
myObservable
    .doStuff()
    .to(AndroidRxDispose.withObservable(myFragment, FragmentEvent.DESTROY))   // The scope
    .subscribe(s -> ...);
```

将 mySingle 的解绑和 myView 所在的 Activity 的 pause 事件绑定
```
mySingle
    .doStuff()
    .to(AndroidRxDispose.withSingle(myView, ViewEvent.PAUSE))   // The scope
    .subscribe(s -> ...);
```

将 myCompletable 的解绑和 myView 所绑定的 Fragment 的 stop 事件绑定
```
AndroidLifeCycle.bindFragment(myView, myFragment);

myCompletable
    .doStuff()
    .to(AndroidRxDispose.withCompletable(myView, ViewEvent.STOP))   // The scope
    .subscribe(s -> ...);
```

支持 Rxjava2 Observable, Single, Completable, Maybe, Flowable

下载
--------
[![Maven Central](https://img.shields.io/maven-central/v/com.github.ykrank/androidautodispose.svg)](https://mvnrepository.com/artifact/com.github.ykrank/androidautodispose)

```gradle
compile 'com.github.ykrank:androidautodispose:x.y.z'
```

版权许可
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