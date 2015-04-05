package ru.atott.combiq.web.controller;

import org.springframework.beans.BeanUtils;
import ru.atott.combiq.service.Context;

public class BaseController {
    protected Context getContext() {
        return new Context();
    }

    protected <T> T getContext(Class<T> tClass) {
        return BeanUtils.instantiate(tClass);
    }
}
