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