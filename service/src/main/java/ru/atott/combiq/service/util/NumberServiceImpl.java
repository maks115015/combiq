package ru.atott.combiq.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.OptionsEntity;
import ru.atott.combiq.dao.repository.OptionsRepository;

@Service
public class NumberServiceImpl implements NumberService {

    @Autowired
    private OptionsRepository optionsRepository;

    @Override
    public synchronized long getUniqueNumber() {
        OptionsEntity optionsEntity = getOptions();

        if (optionsEntity.getLastNumber() == null) {
            optionsEntity.setLastNumber(0L);
        }

        long lastNumber = optionsEntity.getLastNumber() + 1;

        optionsEntity.setLastNumber(lastNumber);
        optionsRepository.save(optionsEntity);

        return lastNumber;
    }

    private OptionsEntity getOptions() {
        OptionsEntity one = optionsRepository.findOne("1");
        if (one == null) {
            one = new OptionsEntity();
            one.setId("1");
        }
        return one;
    }
}
