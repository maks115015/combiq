package ru.atott.combiq.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.SubscriptionEntity;

@Component
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String> {
    SubscriptionEntity findByEmailAndType(String email, String type);
}
