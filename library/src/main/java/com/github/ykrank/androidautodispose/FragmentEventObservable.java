package com.github.ykrank.androidautodispose;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import com.github.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * Provide fragment lifecycle event
 */
final class FragmentEventObservable extends Observable<AndroidRxEvent> {
    private final FragmentLifeCycleManager lifeCycleManager;
    private final FragmentEvent event;

    FragmentEventObservable(Fragment fragment, FragmentEvent event) {
        this.lifeCycleManager = AndroidLifeCycle.with(fragment);
        this.event = event;
    }

    FragmentEventObservable(android.app.Fragment fragment, FragmentEvent event) {
        this.lifeCycleManager = AndroidLifeCycle.with(fragment);
        this.event = event;
    }

    @Override
    protected void subscribeActual(final Observer<? super AndroidRxEvent> observer) {
        LifeCycleListener listener = new LifeCycleListener() {
            @Override
            public void accept() {
                observer.onNext(AndroidRxEvent.DISPOSE);
            }
        };
        observer.onSubscribe(new ListenerDispose(lifeCycleManager, listener, event));

        lifeCycleManager.listen(event, listener);

        observer.onNext(AndroidRxEvent.START);
    }

    private static final class ListenerDispose extends MainThreadDisposable {
        private final FragmentLifeCycleManager lifeCycleManager;
        private final LifeCycleListener listener;
        private final FragmentEvent event;

        ListenerDispose(@NonNull FragmentLifeCycleManager lifeCycleManager,
                        @NonNull LifeCycleListener listener, FragmentEvent event) {
            this.lifeCycleManager = lifeCycleManager;
            this.listener = listener;
            this.event = event;
        }

        @Override
        protected void onDispose() {
            lifeCycleManager.unListen(event, listener);
        }
    }
}
