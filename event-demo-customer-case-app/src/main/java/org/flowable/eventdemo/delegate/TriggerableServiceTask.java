package org.flowable.eventdemo.delegate;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.context.Context;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("triggerableServiceTask")
@Scope("prototype")
public class TriggerableServiceTask implements JavaDelegate, TriggerableActivityBehavior, Serializable {

    private final Map<String, DelegateExecution> executions = new ConcurrentHashMap<>();


    public void onExternalSystemCallback(String executionId, Object callbackData) {
        System.out.println("TriggerableService.onExternalSystemCallback");

        DelegateExecution execution = executions.remove(executionId);

        if (execution != null) {
            execution.setVariable("callbackData", callbackData);
            // Trigger continuation of the process
            RuntimeService runtimeService = Context.getProcessEngineConfiguration().getRuntimeService();
            runtimeService.trigger(execution.getId());
        }
    }

    @Override
    public void execute(DelegateExecution execution) {
        // Store execution to use it later for triggering continuation
        System.out.println("TriggerableService.doSomething");
        executions.put(execution.getId(), execution);

        // Call the external system and pass the execution ID for correlation
        // Nothing here since this is example
    }

    @Override
    public void trigger(DelegateExecution execution, String signalEvent, Object signalData) {
        onExternalSystemCallback(execution.getId(), signalData);
    }
}
