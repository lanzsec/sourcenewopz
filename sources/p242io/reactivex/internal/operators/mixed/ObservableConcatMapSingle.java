package p242io.reactivex.internal.operators.mixed;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p242io.reactivex.Observable;
import p242io.reactivex.Observer;
import p242io.reactivex.SingleObserver;
import p242io.reactivex.SingleSource;
import p242io.reactivex.disposables.Disposable;
import p242io.reactivex.exceptions.Exceptions;
import p242io.reactivex.functions.Function;
import p242io.reactivex.internal.disposables.DisposableHelper;
import p242io.reactivex.internal.functions.ObjectHelper;
import p242io.reactivex.internal.fuseable.SimplePlainQueue;
import p242io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p242io.reactivex.internal.util.AtomicThrowable;
import p242io.reactivex.internal.util.ErrorMode;
import p242io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapSingle */
public final class ObservableConcatMapSingle<T, R> extends Observable<R> {

    /* renamed from: a */
    final Observable<T> f58687a;

    /* renamed from: b */
    final Function<? super T, ? extends SingleSource<? extends R>> f58688b;

    /* renamed from: c */
    final ErrorMode f58689c;

    /* renamed from: d */
    final int f58690d;

    public ObservableConcatMapSingle(Observable<T> observable, Function<? super T, ? extends SingleSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.f58687a = observable;
        this.f58688b = function;
        this.f58689c = errorMode;
        this.f58690d = i;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        if (!C21210a.m41821b(this.f58687a, this.f58688b, observer)) {
            this.f58687a.subscribe(new ConcatMapSingleMainObserver(observer, this.f58688b, this.f58690d, this.f58689c));
        }
    }

    /* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapSingle$ConcatMapSingleMainObserver */
    static final class ConcatMapSingleMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final int STATE_ACTIVE = 1;
        static final int STATE_INACTIVE = 0;
        static final int STATE_RESULT_VALUE = 2;
        private static final long serialVersionUID = -9140123220065488293L;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super R> downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final ConcatMapSingleObserver<R> inner = new ConcatMapSingleObserver<>(this);
        R item;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final SimplePlainQueue<T> queue;
        volatile int state;
        Disposable upstream;

        ConcatMapSingleMainObserver(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, int i, ErrorMode errorMode2) {
            this.downstream = observer;
            this.mapper = function;
            this.errorMode = errorMode2;
            this.queue = new SpscLinkedArrayQueue(i);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.queue.offer(t);
            drain();
        }

        public void onError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.inner.dispose();
                }
                this.done = true;
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.inner.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
                this.item = null;
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void innerSuccess(R r) {
            this.item = r;
            this.state = 2;
            drain();
        }

        /* access modifiers changed from: package-private */
        public void innerError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.dispose();
                }
                this.state = 0;
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.downstream;
                ErrorMode errorMode2 = this.errorMode;
                SimplePlainQueue<T> simplePlainQueue = this.queue;
                AtomicThrowable atomicThrowable = this.errors;
                int i = 1;
                while (true) {
                    if (this.cancelled) {
                        simplePlainQueue.clear();
                        this.item = null;
                    } else {
                        int i2 = this.state;
                        if (atomicThrowable.get() == null || !(errorMode2 == ErrorMode.IMMEDIATE || (errorMode2 == ErrorMode.BOUNDARY && i2 == 0))) {
                            boolean z = false;
                            if (i2 == 0) {
                                boolean z2 = this.done;
                                T poll = simplePlainQueue.poll();
                                if (poll == null) {
                                    z = true;
                                }
                                if (z2 && z) {
                                    Throwable terminate = atomicThrowable.terminate();
                                    if (terminate == null) {
                                        observer.onComplete();
                                        return;
                                    } else {
                                        observer.onError(terminate);
                                        return;
                                    }
                                } else if (!z) {
                                    try {
                                        SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(poll), "The mapper returned a null SingleSource");
                                        this.state = 1;
                                        singleSource.subscribe(this.inner);
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.upstream.dispose();
                                        simplePlainQueue.clear();
                                        atomicThrowable.addThrowable(th);
                                        observer.onError(atomicThrowable.terminate());
                                        return;
                                    }
                                }
                            } else if (i2 == 2) {
                                R r = this.item;
                                this.item = null;
                                observer.onNext(r);
                                this.state = 0;
                            }
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
                simplePlainQueue.clear();
                this.item = null;
                observer.onError(atomicThrowable.terminate());
            }
        }

        /* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapSingle$ConcatMapSingleMainObserver$ConcatMapSingleObserver */
        static final class ConcatMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final ConcatMapSingleMainObserver<?, R> parent;

            ConcatMapSingleObserver(ConcatMapSingleMainObserver<?, R> concatMapSingleMainObserver) {
                this.parent = concatMapSingleMainObserver;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            public void onSuccess(R r) {
                this.parent.innerSuccess(r);
            }

            public void onError(Throwable th) {
                this.parent.innerError(th);
            }

            /* access modifiers changed from: package-private */
            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}