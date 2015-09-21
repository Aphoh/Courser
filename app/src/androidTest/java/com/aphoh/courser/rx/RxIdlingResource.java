package com.aphoh.courser.rx;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.plugins.RxJavaObservableExecutionHook;

import static com.aphoh.courser.rx.RxIdlingResource.LogLevel.DEBUG;
import static com.aphoh.courser.rx.RxIdlingResource.LogLevel.NONE;
import static com.aphoh.courser.rx.RxIdlingResource.LogLevel.VERBOSE;

/**
 * Provides the hooks for both RxJava and Espresso so that Espresso knows when to wait
 * until RxJava subscriptions have completed.
 */

public final class RxIdlingResource extends RxJavaObservableExecutionHook implements IdlingResource {
    public static final String TAG = "RxIdlingResource";

    static LogLevel LOG_LEVEL = NONE;

    private final AtomicInteger subscriptions = new AtomicInteger(0);

    private static RxIdlingResource INSTANCE;

    private ResourceCallback resourceCallback;

    private RxIdlingResource() {
        //private
    }

    public static RxIdlingResource get() {
        if (INSTANCE == null) {
            INSTANCE = new RxIdlingResource();
            Espresso.registerIdlingResources(INSTANCE);
        }
        return INSTANCE;
    }

    public static void setLogLevel(LogLevel logLevel) {
        LOG_LEVEL = logLevel;
    }

    /* ======================== */
    /* IdlingResource Overrides */
    /* ======================== */

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean isIdleNow() {
        int activeSubscriptionCount = subscriptions.get();
        boolean isIdle = activeSubscriptionCount == 0;

        if (LOG_LEVEL.atOrAbove(DEBUG)) {
            Log.d(TAG, "activeSubscriptionCount: " + activeSubscriptionCount);
            Log.d(TAG, "isIdleNow: " + isIdle);
        }

        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        if (LOG_LEVEL.atOrAbove(DEBUG)) {
            Log.d(TAG, "registerIdleTransitionCallback");
        }
        this.resourceCallback = resourceCallback;
    }

    /* ======================================= */
    /* RxJavaObservableExecutionHook Overrides */
    /* ======================================= */

    @Override
    public <T> Observable.OnSubscribe<T> onSubscribeStart(Observable<? extends T> observableInstance,
            final Observable.OnSubscribe<T> onSubscribe) {
        int activeSubscriptionCount = subscriptions.incrementAndGet();
        if (LOG_LEVEL.atOrAbove(DEBUG)) {
            if (LOG_LEVEL.atOrAbove(VERBOSE)) {
                Log.d(TAG, onSubscribe + " - onSubscribeStart: " + activeSubscriptionCount, new Throwable());
            } else {
                Log.d(TAG, onSubscribe + " - onSubscribeStart: " + activeSubscriptionCount);
            }
        }

        onSubscribe.call(new Subscriber<T>() {
            boolean done = false;

            @Override
            public void onCompleted() {
                if (!done) {
                    done = true;
                    onFinally(onSubscribe, "onComplete");
                }
            }

            @Override
            public void onError(Throwable e) {
                onFinally(onSubscribe, "onError");
            }

            @Override
            public void onNext(T t) {
                if(!done){
                    done = true;
                    onFinally(onSubscribe, "onNext");
                }
            }
        });

        return onSubscribe;
    }

    private <T> void onFinally(Observable.OnSubscribe<T> onSubscribe, final String finalizeCaller) {
        int activeSubscriptionCount = subscriptions.decrementAndGet();
        if (LOG_LEVEL.atOrAbove(DEBUG)) {
            Log.d(TAG, onSubscribe + " - " + finalizeCaller + ": " + activeSubscriptionCount);
        }
        if (activeSubscriptionCount == 0) {
            Log.d(TAG, "onTransitionToIdle");
            resourceCallback.onTransitionToIdle();
        }
    }

    public enum LogLevel {
        NONE(0),
        DEBUG(1),
        VERBOSE(2);
        private int levelValue;

        LogLevel(int levelValue) {
            this.levelValue = levelValue;
        }

        public boolean atOrAbove(LogLevel logLevel) {
            return this.levelValue >= logLevel.levelValue;
        }
    }
}
