define(['knockout', 'ajax'], function(ko, ajax) {

    function ViewModel(params) {
        this.tag = ko.unwrap(params.tag);
        this.description = ko.wrap(params.description);
        this.suggestViewOthersQuestionsLabel = ko.wrap(params.suggestViewOthersQuestionsLabel);
        this.width = 500;
    }

    ViewModel.prototype.buttonHandler = function(dialog, button) {
        if (button == 'OK') {
            this
                .save()
                .done(function() {
                    dialog.close();
                    location.reload();
                });
            return false;
        }
    };

    ViewModel.prototype.save = function() {
        var url = '/tags';
        var json = {
            tag: this.tag,
            description: this.description(),
            suggestViewOthersQuestionsLabel: this.suggestViewOthersQuestionsLabel()
        };
        return ajax.rest('POST', url, json);
    };

    return ViewModel;
});