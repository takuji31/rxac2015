package jp.takuji31.rxac2015;

import rx.Observable;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by takuji on 2015/12/07.
 */
public class RetryOperator {
    public static void main(String[] args) {
        int[] retryCount = new int[1];
        retryCount[0] = 0;
        Observable<Integer> observable = Observable.<Integer>create(subscriber -> {
            if (retryCount[0] < 5) {
                subscriber.onError(new RuntimeException(String.format(Locale.US, "Error %d", retryCount[0])));
            } else {
                subscriber.onNext(retryCount[0]);
                subscriber.onCompleted();
            }
            retryCount[0]++;
        });

        observable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext %d", integer));
        }, Throwable::printStackTrace);

        retryCount[0] = 0;

        Observable<Integer> retryObservable = observable.retry();

        retryObservable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext %d", integer));
        }, Throwable::printStackTrace);

        retryCount[0] = 0;

        Observable<Integer> threeTimeRetryObservable = observable.retry(3);

        threeTimeRetryObservable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext %d", integer));
        }, Throwable::printStackTrace);

        retryCount[0] = 0;

        Observable<Integer> retryWhenObservable = observable.retryWhen(observable1 -> {
            return observable1.flatMap(o -> Observable.timer(3, TimeUnit.SECONDS));
        });

        retryWhenObservable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext %d", integer));
        }, Throwable::printStackTrace);

        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
