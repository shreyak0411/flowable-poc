package org.flowable.eventdemo.controller;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.eventregistry.api.EventRegistryEvent;
import org.flowable.eventregistry.api.EventRegistryEventConsumer;
import org.flowable.eventregistry.api.EventRegistryProcessingInfo;
import org.flowable.eventregistry.api.runtime.EventInstance;
import org.flowable.eventregistry.model.EventModel;
import org.springframework.stereotype.Service;

/**
 * @author Filip Hrisafov
 */
@Service
public class ReviewEventCounter implements EventRegistryEventConsumer {

    private final AtomicLong eventCounter = new AtomicLong(0);

    public long getEventCount() {
        return eventCounter.get();
    }

    protected void eventReceived(EventInstance eventInstance) {
        String eventKey = eventInstance.getEventKey();
        if (Objects.equals("reviewEvent", eventKey)) {
            eventCounter.incrementAndGet();
        }
    }

    @Override
    public EventRegistryProcessingInfo eventReceived(EventRegistryEvent event) {
        if (event.getEventObject() != null && event.getEventObject() instanceof EventInstance) {
            eventReceived((EventInstance) event.getEventObject());
        } else {
            if (event.getEventObject() == null) {
                throw new FlowableIllegalArgumentException("No event object was passed to the consumer");
            } else {
                throw new FlowableIllegalArgumentException("Unsupported event object type: " + event.getEventObject().getClass());
            }
        }
        return null;
    }

    @Override
    public String getConsumerKey() {
        return "reviewEventConsumer";
    }
}
