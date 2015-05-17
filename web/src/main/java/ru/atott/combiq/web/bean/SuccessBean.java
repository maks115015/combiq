package ru.atott.combiq.web.bean;

public class SuccessBean {
    private boolean success = true;

    public SuccessBean() { }

    public SuccessBean(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
