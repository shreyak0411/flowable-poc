package org.flowable.eventdemo.controller;

import org.flowable.eventdemo.delegate.TriggerableServiceTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

    private final TriggerableServiceTask triggerableService;

    public CallbackController(TriggerableServiceTask triggerableService) {
        this.triggerableService = triggerableService;
    }

    @GetMapping(value = "/external-callback")
    public ResponseEntity<Void> handleCallback(@RequestParam String executionId) {
        triggerableService.onExternalSystemCallback(executionId, "someData");
        return ResponseEntity.ok().build();
    }
}
