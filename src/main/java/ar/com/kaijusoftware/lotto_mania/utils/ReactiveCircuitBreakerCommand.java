package ar.com.kaijusoftware.lotto_mania.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class ReactiveCircuitBreakerCommand<T> {

    private Mono<T> runnableMono;

    private Flux<T> runnableFlux;

    private Function<Throwable, Mono<T>> fallbackMono;

    private Function<Throwable, Flux<T>> fallbackFlux;

    private final String name;

    private final ReactiveCircuitBreaker rcb;

    private ReactiveCircuitBreakerCommand(String name, ReactiveResilience4JCircuitBreakerFactory factory) {
        this.rcb = factory.create(name);
        this.name = name;
    }

    private ReactiveCircuitBreakerCommand(String name,
                                          ReactiveResilience4JCircuitBreakerFactory factory,
                                          Mono<T> runnable,
                                          Function<Throwable, Mono<T>> fallback) {
        this(name, factory);
        this.runnableMono = runnable;
        this.fallbackMono = fallback;
    }

    private ReactiveCircuitBreakerCommand(String name,
                                          ReactiveResilience4JCircuitBreakerFactory factory,
                                          Flux<T> runnable,
                                          Function<Throwable, Flux<T>> fallback) {
        this(name, factory);
        this.runnableFlux = runnable;
        this.fallbackFlux = fallback;
    }

    private Function<Throwable, Mono<T>> getDefaultFallbackMono() {
        return t -> {
            log.error("Default error for Reactive Circuit Breaker Command: {}", name, t);
            return Mono.empty();
        };
    }

    private Function<Throwable, Flux<T>> getDefaultFallbackFlux() {
        return t -> {
            log.error("Default error for Reactive Circuit Breaker Command: {}", name, t);
            return Flux.empty();
        };
    }

    private Mono<T> runMono() {
        if (Objects.nonNull(fallbackMono))
            return rcb.run(runnableMono, fallbackMono);
        else
            return rcb.run(runnableMono, getDefaultFallbackMono());
    }

    private Flux<T> runFlux() {
        if (Objects.nonNull(fallbackFlux))
            return rcb.run(runnableFlux, fallbackFlux);
        else
            return rcb.run(runnableFlux, getDefaultFallbackFlux());
    }

    public static <T> Mono<T> create(final String name, final ReactiveResilience4JCircuitBreakerFactory factory, Mono<T> runnable) {
        return new ReactiveCircuitBreakerCommand<T>(name, factory, runnable, null).runMono();
    }

    public static <T> Mono<T> create(final String name, final ReactiveResilience4JCircuitBreakerFactory factory, Mono<T> runnable, Function<Throwable, Mono<T>> fallback) {
        return new ReactiveCircuitBreakerCommand<T>(name, factory, runnable, fallback).runMono();
    }

    public static <T> Flux<T> create(final String name, final ReactiveResilience4JCircuitBreakerFactory factory, Flux<T> runnable) {
        return new ReactiveCircuitBreakerCommand<T>(name, factory, runnable, null).runFlux();
    }

    public static <T> Flux<T> create(final String name, final ReactiveResilience4JCircuitBreakerFactory factory, Flux<T> runnable, Function<Throwable, Flux<T>> fallback) {
        return new ReactiveCircuitBreakerCommand<T>(name, factory, runnable, fallback).runFlux();
    }

}
