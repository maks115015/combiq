define([], function() {
    function ViewModel() {

    }

    ViewModel.prototype.subscribe = function() {
        ko.openDialog('co-jobsubscribe');
    };

    return ViewModel;
});