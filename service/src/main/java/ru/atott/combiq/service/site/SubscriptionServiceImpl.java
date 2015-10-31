package ru.atott.combiq.service.site;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.SubscriptionEntity;
import ru.atott.combiq.dao.repository.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void subscribe(String email, SubscriptionType type) {
        Validate.notEmpty(email);

        SubscriptionEntity entity = subscriptionRepository.findByEmailAndType(email, type.name());

        if (entity == null) {
            entity = new SubscriptionEntity();
            entity.setEmail(email.toLowerCase());
            entity.setType(type.name());
            subscriptionRepository.save(entity);
        }
    }

    @Override
    public boolean hasSubscription(String email, SubscriptionType type) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        return subscriptionRepository.findByEmailAndType(email.toLowerCase(), type.name()) != null;
    }
}
