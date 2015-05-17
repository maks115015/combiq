function questionReputationVote(up, questionId) {
    if (!co.userId) {
        var dialog = document.getElementById('inviteAuthDialog');
        dialog.open();
        return;
    }
    $.post(
        '/questions/reputationVote', {
            up: up,
            questionId: questionId
        }, function(response) {
            $('.js-questionReputationLabel-' + questionId).text(response.questionReputation);
            $('.js-questionReputationUp-' + questionId)
                .removeClass('co-voted')
                .addClass(up ? 'co-voted' : '');
            $('.js-questionReputationDown-' + questionId)
                .removeClass('co-voted')
                .addClass(!up ? 'co-voted' : '');
        });
}

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