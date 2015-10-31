define(['ajax'], function(ajax) {
    function ViewModel() {
        var self = this;

        this.buttons = ['Подписаться'];
        this.email = ko.wrap(window.co.userEmail);

        this.buttonHandler = function(dialog, button) {
            console.log('button', button);

            if (button == 'Подписаться') {
                if (self.email()) {
                    ajax
                        .rest('POST', '/rest/job/subscription', {email: self.email()})
                        .done(function() {
                            new Dialog('Вы успешно подписаны. Когда у нас появятся новые вакансии мы Вам об этом сообщим.');
                        });
                }

                return false;
            }
        }
    }

    return ViewModel;
});