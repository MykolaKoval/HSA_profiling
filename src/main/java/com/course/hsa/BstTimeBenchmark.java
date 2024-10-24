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
public class BstTimeBenchmark {

    @State(Scope.Thread)
    public static class ExecutionPlan {

        @Param({"1000000", "2000000", "3000000", "4000000", "5000000", "6000000", "7000000", "8000000", "9000000", "10000000"})
        public int size;

        List<Integer> values;
        AVLTree tree;

        @Setup(Level.Invocation)
        public void setupInvocation() {
            values = generateDataset(10000, size + size);
        }

        @Setup(Level.Trial)
        public void setupTrial() {
            tree = createTree(size, size + size);
        }
    }

//    @Benchmark
//    public void testInsert(ExecutionPlan plan) {
//        plan.values.stream().forEach(plan.tree::insert);
//    }

//    @Benchmark
//    public void testSearch(ExecutionPlan plan) {
//        plan.values.stream().forEach(plan.tree::find);
//    }
//
    @Benchmark
    public void testDelete(ExecutionPlan plan) {
        plan.values.stream().forEach(plan.tree::delete);
    }

    private static List<Integer> generateDataset(int size, int maxValue) {
        return new Random().ints(size, 0, maxValue).distinct().boxed().toList();
    }

    private static AVLTree createTree(int size, int maxValue) {
        var values = generateDataset(size, maxValue);
        AVLTree tree = new AVLTree();
        values.forEach(tree::insert);
        return tree;
    }
}
