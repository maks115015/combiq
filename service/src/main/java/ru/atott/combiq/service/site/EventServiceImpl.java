package ru.atott.combiq.service.site;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.EventEntity;
import ru.atott.combiq.dao.entity.EventType;
import ru.atott.combiq.dao.entity.Link;
import ru.atott.combiq.dao.entity.UserEntity;
import ru.atott.combiq.dao.repository.EventRepository;
import ru.atott.combiq.service.bean.Event;
import ru.atott.combiq.service.mapper.EventMapper;

import java.util.Date;

@Service
public class EventServiceImpl implements EventService {

    private static EventMapper eventMapper = new EventMapper();

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void createEvent(UserContext uc, EventType type, String text, Link... relevantLinks) {
        EventEntity entity = new EventEntity();
        entity.setCreateDate(new Date());
        entity.setType(type);
        if (uc != null && !uc.isAnonimous()) {
            entity.setCreatorUserId(uc.getUserId());
            entity.setCreatorUserName(uc.getUserName());
        }
        entity.setMessage(text);
        if (relevantLinks != null) {
            entity.setRelevantLinks(Lists.newArrayList(relevantLinks));
        }
        eventRepository.save(entity);
    }

    @Override
    public Page<Event> getEvents(long page, long size) {
        Pageable pageable = new PageRequest((int) page, (int) size, Sort.Direction.DESC, EventEntity.CREATE_DATE_FIELD);
        Page<EventEntity> result = eventRepository.findAll(pageable);
        return result.map(eventEntity -> eventMapper.map(eventEntity));
    }
}
