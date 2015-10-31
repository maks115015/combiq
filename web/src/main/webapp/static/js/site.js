
ko.wrap = function(value) {
    return ko.isObservable(value)
        ? value
        : (value instanceof Array ? ko.observableArray(value) : ko.observable(value));
};

function saveQuestionComment(comment, questionId) {
    $('#questionMyCommentStatus').text('Сохранение...');
    $.post(
        '/questions/commentSave', {
            comment: comment,
            questionId: questionId
        }).done(function() {
            $('#questionMyCommentStatus').text('Комментарий успешно сохранен');
        }).fail(function() {
            $('#questionMyCommentStatus').text('Ошибка при сохранении');
        });
}