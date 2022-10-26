package me.designpattern.code.behavior.oberver.example.java;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowInJava {
    public static void main(String[] args) {
        Flow.Publisher<String> publisher = new Flow.Publisher<>() {
            @Override
            public void subscribe(Flow.Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello flow");
                subscriber.onComplete();
            }
        };

        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("sub!");
                this.subscription = subscription;
                this.subscription.request(1); // publisher 에 하나 꺼내주라는 뜻
            }

            @Override
            public void onNext(String item) {
                System.out.println(item);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("completed");
            }
        };

        publisher.subscribe(subscriber);

        SubmissionPublisher publisher1 = (SubmissionPublisher) publisher;
        publisher1.submit("Hello");

        System.out.println("Async");

        // RxJava, Reactor 도 틈틈이 공부해볼 것 (Reactive stream)
    }
}
