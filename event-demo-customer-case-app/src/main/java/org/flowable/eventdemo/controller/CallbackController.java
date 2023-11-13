package org.flowable.eventdemo.controller;

import org.flowable.eventdemo.delegate.TriggerableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

    private final TriggerableService triggerableService;

    public CallbackController(TriggerableService triggerableService) {
        this.triggerableService = triggerableService;
    }

    @PostMapping("/external-callback")
    public ResponseEntity<Void> handleCallback(@RequestParam String executionId, @RequestBody Object callbackData) {
        triggerableService.onExternalSystemCallback(executionId, callbackData);
        return ResponseEntity.ok().build();
    }
}
