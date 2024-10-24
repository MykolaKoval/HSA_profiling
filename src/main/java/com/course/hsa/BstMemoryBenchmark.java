package com.course.hsa;

import com.course.hsa.bst.AVLTree;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Measurement(iterations = 3)
@Warmup(iterations = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BstMemoryBenchmark {

    @State(Scope.Thread)
    public static class ExecutionPlan {

        @Param({"1000000", "2000000", "3000000", "4000000", "5000000", "6000000", "7000000", "8000000", "9000000", "10000000"})
        public int size;

        List<Integer> values;

        @Setup(Level.Invocation)
        public void setupInvocation() {
            values = generateDataset(size, size + size);
        }
    }

    @Benchmark
    public AVLTree testMemory(ExecutionPlan plan) {
        return createTree(plan.values);
    }

    private static List<Integer> generateDataset(int size, int maxValue) {
        return new Random().ints(size, 0, maxValue).distinct().boxed().toList();
    }

    private static AVLTree createTree(List<Integer> values) {
        AVLTree tree = new AVLTree();
        values.forEach(tree::insert);
        return tree;
    }
}
