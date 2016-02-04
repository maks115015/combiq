define(['knockout'], function(ko) {

    function ViewModel(params) {
        this.text = ko.wrap(params.text);
    }

    return ViewModel;
});