package com.github.ykrank.androidautodispose;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import com.github.ykrank.androidlifecycle.manager.ActivityLifeCycleManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * Provide activity lifecycle event
 */
final class ActivityEventObservable extends Observable<AndroidRxEvent> {
    private final ActivityLifeCycleManager lifeCycleManager;
    private final ActivityEvent event;

    ActivityEventObservable(Context context, ActivityEvent event) {
        this.lifeCycleManager = AndroidLifeCycle.with(context);
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
        private final ActivityLifeCycleManager lifeCycleManager;
        private final LifeCycleListener listener;
        private final ActivityEvent event;

        ListenerDispose(@NonNull ActivityLifeCycleManager lifeCycleManager,
                        @NonNull LifeCycleListener listener, ActivityEvent event) {
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
