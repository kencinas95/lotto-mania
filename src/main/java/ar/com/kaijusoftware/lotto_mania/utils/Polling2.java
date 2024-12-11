package ar.com.kaijusoftware.lotto_mania.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class Polling2 {
    private final static ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

    public enum PollingMethod {
        FOREVER,
        MAX_RETRIES,
        TIMEOUT;
    }

    public static class PollingMaxCallException extends Exception {
    }

    public static class PollingTimeoutException extends Exception {
    }


    @Getter
    public static class PollingArgs<T> {
        private final PollingMethod method;

        private Long step;

        private Long limit;

        private Set<Class<? extends Throwable>> ignoreExceptions = new HashSet<>();

        private Function<T, Boolean> checkSuccess;

        private Function<Long, Long> stepFunction;

        private PollingArgs(Function<T, Boolean> checkSuccess) {
            this.method = PollingMethod.FOREVER;
            this.checkSuccess = checkSuccess;
        }

        private PollingArgs(PollingMethod method, Long limit, Long step, Function<T, Boolean> checkSuccess) {
            this.method = method;
            this.limit = limit;
            this.step = step;
            this.checkSuccess = checkSuccess;
        }

        private PollingArgs(PollingMethod method,
                            Long limit,
                            Long step,
                            Function<T, Boolean> checkSuccess,
                            Set<Class<? extends Throwable>> ignoreExceptions) {
            this(method, limit, step, checkSuccess);
            this.ignoreExceptions = ignoreExceptions;
        }

        private PollingArgs(PollingMethod method,
                            Long limit,
                            Long step,
                            Function<T, Boolean> checkSuccess,
                            Function<Long, Long> stepFunction) {
            this(method, limit, step, checkSuccess);
            this.stepFunction = stepFunction;
        }

        private PollingArgs(PollingMethod method,
                            Long limit,
                            Long step,
                            Function<T, Boolean> checkSuccess,
                            Set<Class<? extends Throwable>> ignoreExceptions,
                            Function<Long, Long> stepFunction) {
            this(method, limit, step, checkSuccess);
            this.ignoreExceptions = ignoreExceptions;
            this.stepFunction = stepFunction;
        }

        private PollingArgs(PollingMethod method, Long limit, Long step) {
            this.method = method;
            this.limit = limit;
            this.step = step;
        }

        public static <R> PollingArgs<R> forever(final Function<R, Boolean> checkSuccess) {
            return new PollingArgs<>(checkSuccess);
        }

        public static <R> PollingArgs<R> usingMaxTries(final Long tries,
                                                       final Long step,
                                                       final Function<R, Boolean> checkSuccess) {
            return new PollingArgs<>(PollingMethod.MAX_RETRIES, tries, step, checkSuccess);
        }

        public static <R> PollingArgs<R> usingMaxTries(final Long tries,
                                                       final Long step,
                                                       final Function<R, Boolean> checkSuccess,
                                                       final Function<Long, Long> stepFunction) {
            return new PollingArgs<>(PollingMethod.MAX_RETRIES, tries, step, checkSuccess, stepFunction);
        }

        public static <R> PollingArgs<R> usingMaxTries(final Long tries,
                                                       final Long step,
                                                       final Function<R, Boolean> checkSuccess,
                                                       final Set<Class<? extends Throwable>> ignoreExceptions) {
            return new PollingArgs<>(PollingMethod.MAX_RETRIES, tries, step, checkSuccess, ignoreExceptions);
        }

        public static <R> PollingArgs<R> usingMaxTries(final Long tries,
                                                       final Long step,
                                                       final Function<R, Boolean> checkSuccess,
                                                       final Set<Class<? extends Throwable>> ignoreExceptions,
                                                       final Function<Long, Long> stepFunction) {
            return new PollingArgs<>(PollingMethod.MAX_RETRIES, tries, step, checkSuccess, ignoreExceptions, stepFunction);
        }

        public static <R> PollingArgs<R> usingTimeout(final Long timeout,
                                                      final Long step,
                                                      final Function<R, Boolean> checkSuccess) {
            return new PollingArgs<>(PollingMethod.TIMEOUT, timeout, step, checkSuccess);
        }

        public static <R> PollingArgs<R> usingTimeout(final Long timeout,
                                                      final Long step,
                                                      final Function<R, Boolean> checkSuccess,
                                                      final Function<Long, Long> stepFunction) {
            return new PollingArgs<>(PollingMethod.TIMEOUT, timeout, step, checkSuccess, stepFunction);
        }

        public static <R> PollingArgs<R> usingTimeout(final Long timeout,
                                                      final Long step,
                                                      final Function<R, Boolean> checkSuccess,
                                                      final Set<Class<? extends Throwable>> ignoreExceptions) {
            return new PollingArgs<>(PollingMethod.TIMEOUT, timeout, step, checkSuccess, ignoreExceptions);
        }

        public static <R> PollingArgs<R> usingTimeout(final Long timeout,
                                                      final Long step,
                                                      final Function<R, Boolean> checkSuccess,
                                                      final Set<Class<? extends Throwable>> ignoreExceptions,
                                                      final Function<Long, Long> stepFunction) {
            return new PollingArgs<>(PollingMethod.TIMEOUT, timeout, step, checkSuccess, ignoreExceptions, stepFunction);
        }
    }

    @SneakyThrows
    public static <T> Optional<T> blockingPoll(final Supplier<T> target, final PollingArgs<T> args) {
        // Check success function
        final Function<T, Boolean> checkSuccess = args.getCheckSuccess();

        // Get step function
        final Function<Long, Long> getStep = Optional.ofNullable(args.getStepFunction())
                .orElse(constantStep(args.getStep()));

        // Is the poll forever?
        final boolean isForever = PollingMethod.FOREVER.equals(args.getMethod());

        // Is the poll a timeout?
        final boolean isPollingTimeout = PollingMethod.TIMEOUT.equals(args.getMethod());

        final Set<Class<? extends Throwable>> ignoreExceptionClasses = Optional.ofNullable(args.getIgnoreExceptions())
                .orElse(new HashSet<>());

        // Limit
        final Long limit = isPollingTimeout ? getTimeoutLimit(args.getLimit()) : args.getLimit();

        long step = 0L;
        long counter = 0L;

        while (true) {
            try {
                T result = target.get();
                if (checkSuccess.apply(result)) {
                    return Optional.ofNullable(result);
                }
            } catch (Throwable t) {
                if (isForever || !shouldIgnoreException(t, ignoreExceptionClasses)) {
                    log.error("An error has occurred while polling, unable to continue.", t);
                    throw t;
                } else {
                    log.error("Ignoring exception for poll.", t);
                }
            }

            if (!isForever) {
                if (!isPollingTimeout) {
                    if (++counter > limit) {
                        throw new PollingMaxCallException();
                    }
                } else {
                    if (isTimeout(limit)) {
                        throw new PollingTimeoutException();
                    }
                }
            }

            Thread.sleep(step);
            step = getStep.apply(step);

        }
    }

    public static <T> CompletableFuture<T> nonBlockingPoll(final Supplier<T> target, final PollingArgs<T> args) {
        // Check success function
        final Function<T, Boolean> checkSuccess = args.getCheckSuccess();

        // Get step function
        final Long step = args.getStep();
        log.warn("Non-Blocking poll only takes static step values!");

        // Is the poll forever?
        final boolean isForever = PollingMethod.FOREVER.equals(args.getMethod());

        // Is the poll a timeout?
        final boolean isPollingTimeout = PollingMethod.TIMEOUT.equals(args.getMethod());

        final Set<Class<? extends Throwable>> ignoreExceptionClasses = Optional.ofNullable(args.getIgnoreExceptions())
                .orElse(new HashSet<>());

        // Limit
        final Long limit = isPollingTimeout ? getTimeoutLimit(args.getLimit()) : args.getLimit();

        AtomicLong counter = new AtomicLong(0);

        CompletableFuture<T> task = new CompletableFuture<>();

        Runnable polling = () -> {
            if (isPollingTimeout && isTimeout(limit)) {
                task.completeExceptionally(new TimeoutException());
                return;
            }

            try {
                T result = target.get();
                if (checkSuccess.apply(result)) {
                    task.complete(result);
                    return;
                }
            } catch (Throwable t) {
                if (isForever || !shouldIgnoreException(t, ignoreExceptionClasses)) {
                    task.completeExceptionally(t);
                    return;
                }
            }

            if (!isPollingTimeout && !isForever) {
                if (counter.getAndAdd(1) > limit)
                {
                    task.completeExceptionally(new PollingMaxCallException());
                }
            }
        };

        SCHEDULER.scheduleAtFixedRate(polling, 0, step, TimeUnit.MILLISECONDS);
        return task;
    }

    private static Function<Long, Long> constantStep(final Long step) {
        return (s) -> step;
    }

    private static Boolean shouldIgnoreException(final Throwable t,
                                                 final Set<Class<? extends Throwable>> exceptions) {
        return exceptions.stream().anyMatch(ex -> ex.isInstance(t));
    }

    private static Long getTimeoutLimit(Long seconds) {
        return Instant.now().getEpochSecond() + seconds;
    }

    private static Boolean isTimeout(Long limit) {
        return Instant.now().getEpochSecond() > limit;
    }
}
