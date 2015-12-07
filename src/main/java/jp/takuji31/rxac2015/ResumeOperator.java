package jp.takuji31.rxac2015;

import rx.Observable;

/**
 * Created by takuji on 2015/12/07.
 */
public class ResumeOperator {
    public static void main(String[] args) {
        Observable<String> observable = Observable.range(0, 5).flatMap(integer -> Observable.error(new RuntimeException("String not found!!!")));
        observable.subscribe(s -> {
            System.out.println(s);
        }, Throwable::printStackTrace);

        Observable<String> resumeNextObservable = observable.onErrorResumeNext(throwable -> Observable.just("Resume", "Next"));
        resumeNextObservable.subscribe(s -> {
            System.out.println(s);
        }, Throwable::printStackTrace);

        Observable<String> returnObservable = observable.onErrorReturn(throwable -> "OnErrorReturn");
        returnObservable.subscribe(s -> {
            System.out.println(s);
        }, Throwable::printStackTrace);
    }
}
