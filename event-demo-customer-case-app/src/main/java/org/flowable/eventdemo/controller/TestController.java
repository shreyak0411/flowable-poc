package org.flowable.eventdemo.controller;

import org.flowable.engine.RuntimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private final RuntimeService runtimeService;

    public TestController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping(value = "/start-process-http")
    public Mono<ResponseEntity<Void>> processReview(@RequestBody String review) {
        // Map the incoming data to process variables if needed
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("review", review);

        // Start the process instance using the message name
        runtimeService.startProcessInstanceByMessage("myStartMessageName", processVariables);

        // Return a success response
        return Mono.just(ResponseEntity.ok().build());
    }

}