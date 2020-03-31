package com.github.dreamylost.Instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 自定义的instrumentation实现，用以计算请求处理时间
 *
 * @author 梦境迷离
 * @time 2020年03月31日14:22:24
 */
class CustomInstrumentationState implements InstrumentationState {
    private Map<String, Object> anyStateYouLike = new HashMap<>();

    void recordTiming(String key, long time) {
        anyStateYouLike.put(key, time);
    }
}

public class CustomInstrumentation extends SimpleInstrumentation {
    @Override
    public InstrumentationState createState() {
        return new CustomInstrumentationState();
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        long startNanos = System.nanoTime();
        return new SimpleInstrumentationContext<ExecutionResult>() {
            @Override
            public void onCompleted(ExecutionResult result, Throwable t) {
                CustomInstrumentationState state = parameters.getInstrumentationState();
                state.recordTiming(parameters.getQuery(), System.nanoTime() - startNanos);
            }
        };
    }

    @Override
    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters) {
        return dataFetcher;
    }

    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult, InstrumentationExecutionParameters parameters) {
        return CompletableFuture.completedFuture(executionResult);
    }
}