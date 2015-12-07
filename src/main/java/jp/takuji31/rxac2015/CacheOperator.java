package jp.takuji31.rxac2015;

import rx.Observable;

import java.util.Locale;

/**
 * Created by takuji on 2015/12/07.
 */
public class CacheOperator {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.<Integer>create(subscriber -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(String.format(Locale.US, "OnSubscribe %d", i));
                subscriber.onNext(i);
            }
        });

        observable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext 1 - %d", integer));
        });

        observable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext 2 - %d", integer));
        });

        System.out.println();
        System.out.println("---- cached observable ----");
        System.out.println();

        Observable<Integer> cachedObservable = observable.cache();

        cachedObservable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext 1 - %d", integer));
        });

        cachedObservable.subscribe(integer -> {
            System.out.println(String.format(Locale.US, "OnNext 2 - %d", integer));
        });
    }
}
