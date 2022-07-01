package benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
@State(Scope.Benchmark)
public class ParallelStreamBenchmark {

    private static long N = 50_000L;

    /*@Benchmark
    public long sequentialSum(){
        return Stream.iterate(1L, i -> i + 1)
                .limit(N)
                .reduce(0L, Long::sum);

    }*/
    /*@Benchmark
    public long parallelSum(){
        return Stream.iterate(1L, i -> i + 1)
                .parallel()
                .limit(N)
                .reduce(0L, Long::sum);

    }*/
    @Benchmark
    public long sequentialSumWithLongStreamRange(){
        return LongStream.rangeClosed(1L, N)
                .reduce(0L, Long::sum);

    }

   /* @Benchmark
    public long parallelSumWithLongStreamRange() {
        return LongStream.rangeClosed(1L, N)
                .parallel()
                .reduce(0L, Long::sum);

    }*/

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}
