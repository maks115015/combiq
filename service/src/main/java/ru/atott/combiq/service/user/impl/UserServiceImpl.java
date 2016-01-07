package ru.atott.combiq.service.user.impl;

import org.elasticsearch.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.UserEntity;
import ru.atott.combiq.dao.repository.UserRepository;
import ru.atott.combiq.service.ServiceException;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserQualifier;
import ru.atott.combiq.service.bean.UserType;
import ru.atott.combiq.service.mapper.UserMapper;
import ru.atott.combiq.service.user.GithubRegistrationContext;
import ru.atott.combiq.service.user.UserRoles;
import ru.atott.combiq.service.user.UserService;
import ru.atott.combiq.service.user.VkRegistrationContext;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper = new UserMapper();
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByLogin(String login, UserType type) {
        List<UserEntity> byEmail = userRepository.findByLoginAndType(login, type.name());

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
    public User registerUserViaVk(VkRegistrationContext context) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(context.getUid());
        userEntity.setLocation(context.getLocation());
        userEntity.setName(context.getName());
        userEntity.setType(UserType.vk.name());
        userEntity.setAvatarUrl(context.getAvatarUrl());

        userEntity = userRepository.index(userEntity);
        return userMapper.map(userEntity);
    }

    @Override
    public User updateGithubUser(GithubRegistrationContext context) {
        UserEntity userEntity = userRepository.findByLoginAndType(context.getLogin(), UserType.github.name()).get(0);

        userEntity.setLogin(context.getLogin());
        userEntity.setHome(context.getHome());
        userEntity.setLocation(context.getLocation());
        userEntity.setName(context.getName());
        userEntity.setType(UserType.github.name());
        userEntity.setAvatarUrl(context.getAvatarUrl());

        userRepository.save(userEntity);
        return userMapper.map(userEntity);
    }

    @Override
    public User updateVkUser(VkRegistrationContext context) {
        UserEntity userEntity = userRepository.findByLoginAndType(context.getUid(), UserType.vk.name()).get(0);

        userEntity.setLogin(context.getUid());
        userEntity.setLocation(context.getLocation());
        userEntity.setName(context.getName());
        userEntity.setType(UserType.vk.name());
        userEntity.setAvatarUrl(context.getAvatarUrl());

        userRepository.save(userEntity);
        return userMapper.map(userEntity);
    }

    @Override
    public List<String> getUserRoles(UserQualifier userQualifier) {
        if (UserQualifier.ATOTT.equals(userQualifier)) {
            return Lists.newArrayList(UserRoles.sa, UserRoles.user);
        }

        return Collections.singletonList(UserRoles.user);
    }

    @Override
    public User getById(String userId) {
        UserEntity userEntity = userRepository.findOne(userId);

        if (userEntity != null) {
            return userMapper.map(userEntity);
        }

        return null;
    }
}
