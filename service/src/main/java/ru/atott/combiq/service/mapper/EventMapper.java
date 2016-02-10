package ru.atott.combiq.service.mapper;

import ru.atott.combiq.dao.entity.EventEntity;
import ru.atott.combiq.service.bean.Event;

public class EventMapper implements Mapper<EventEntity, Event> {

    @Override
    public Event map(EventEntity source) {
        Event event = new Event();
        event.setId(source.getId());
        event.setCreateDate(source.getCreateDate());
        event.setMessage(source.getMessage());
        event.setCreatorUserId(source.getCreatorUserId());
        event.setCreatorUserName(source.getCreatorUserName());
        event.setRelevantLinks(source.getRelevantLinks());
        event.setType(source.getType());
        return event;
    }
}
