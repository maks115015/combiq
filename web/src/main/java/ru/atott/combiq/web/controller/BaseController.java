package ru.atott.combiq.web.controller;

public class BaseController {
    protected int getZeroBasedPage(int page) {
        return Math.max(0, page - 1);
    }
}
