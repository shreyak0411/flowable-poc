package org.flowable.eventdemo.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class ProcessResponseDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String httpResponse = (String) execution.getVariable("makeHttpCallResponseBody");

        System.out.println("ProcessResponseDelegate - " + httpResponse);
    }
}
