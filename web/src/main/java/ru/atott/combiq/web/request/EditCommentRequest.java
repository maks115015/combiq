package ru.atott.combiq.web.request;

public class EditCommentRequest extends ContentRequest {

    private String commentId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
