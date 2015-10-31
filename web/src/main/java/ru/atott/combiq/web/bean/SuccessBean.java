package ru.atott.combiq.web.bean;

public class SuccessBean {
    private boolean success = true;
    private String error;

    public SuccessBean() { }

    public SuccessBean(boolean success) {
        this.success = success;
    }

    public SuccessBean(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
