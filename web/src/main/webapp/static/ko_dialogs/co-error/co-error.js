define([], function() {
    function ViewModel(params) {
        this.message = params.message;
        this.title = params.title || 'Произошла ошибка';
    }

    return ViewModel;
});