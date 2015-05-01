package ru.atott.combiq.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ru.atott.combiq.dao.entity.RememberMeEntity;
import ru.atott.combiq.dao.repository.RememberMeRepository;

import java.util.Date;

public class ElasticSearchTokenRepositoryImpl implements PersistentTokenRepository {
    @Autowired
    private RememberMeRepository rememberMeRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMeEntity rememberMe = new RememberMeEntity();
        rememberMe.setUsername(token.getUsername());
        rememberMe.setSeries(token.getSeries());
        rememberMe.setLastused(token.getDate());
        rememberMe.setToken(token.getTokenValue());
        rememberMeRepository.index(rememberMe);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        RememberMeEntity entity = rememberMeRepository.findOne(series);
        if (entity != null) {
            entity.setLastused(lastUsed);
            entity.setToken(tokenValue);
            rememberMeRepository.save(entity);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMeEntity entity = rememberMeRepository.findOne(seriesId);
        if (entity == null) {
            return null;
        }
        return new PersistentRememberMeToken(entity.getUsername(), entity.getSeries(),
                entity.getToken(), entity.getLastused());
    }

    @Override
    public void removeUserTokens(String username) {
        rememberMeRepository.deleteByUsername(username);
    }
}
