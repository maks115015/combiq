package ru.atott.combiq.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.UserEntity;
import ru.atott.combiq.dao.repository.UserRepository;
import ru.atott.combiq.service.ServiceException;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserType;
import ru.atott.combiq.service.mapper.UserMapper;
import ru.atott.combiq.service.user.GithubRegistrationContext;
import ru.atott.combiq.service.user.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper = new UserMapper();
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        List<UserEntity> byEmail = userRepository.findByEmail(email);

        UserEntity userEntity = null;
        if (byEmail.size() == 1) {
            userEntity = byEmail.get(0);
        }

        if (userEntity != null) {
            return userMapper.map(userEntity);
        }

        if (byEmail.size() == 0) {
            return null;
        }

        throw new ServiceException(String.format("There are more then one user with email: %s", email));
    }

    @Override
    public User findByLogin(String login) {
        List<UserEntity> byEmail = userRepository.findByLogin(login);

        UserEntity userEntity = null;
        if (byEmail.size() == 1) {
            userEntity = byEmail.get(0);
        }

        if (userEntity != null) {
            return userMapper.map(userEntity);
        }

        if (byEmail.size() == 0) {
            return null;
        }

        throw new ServiceException(String.format("There are more then one user with login: %s", login));
    }

    @Override
    public User registerUserViaGithub(GithubRegistrationContext context) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(context.getEmail());
        userEntity.setLogin(context.getLogin());
        userEntity.setHome(context.getHome());
        userEntity.setLocation(context.getLocation());
        userEntity.setName(context.getName());
        userEntity.setType(UserType.github.name());
        userEntity.setAvatarUrl(context.getAvatarUrl());

        userEntity = userRepository.index(userEntity);
        return userMapper.map(userEntity);
    }

    @Override
    public User updateGithubUser(GithubRegistrationContext context) {
        UserEntity userEntity = userRepository.findByLogin(context.getLogin()).get(0);

        userEntity.setLogin(context.getLogin());
        userEntity.setHome(context.getHome());
        userEntity.setLocation(context.getLocation());
        userEntity.setName(context.getName());
        userEntity.setType(UserType.github.name());
        userEntity.setAvatarUrl(context.getAvatarUrl());

        userRepository.save(userEntity);
        return userMapper.map(userEntity);
    }
}
