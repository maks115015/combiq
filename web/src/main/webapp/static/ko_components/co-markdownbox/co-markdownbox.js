define(['ajax'], function(ajax) {

    function ViewModel(params) {
        this.text = ko.wrap(params.text);
        this.active=ko.wrap('markdown');
        this.preview=ko.wrap(params.text);
    }
    ViewModel.prototype.toggleSrc=function() {
    this.active('markdown');
                };
    ViewModel.prototype.togglePreview=function() {
    this.active('html');
    ajax.rest('POST', '/markdown/preview', this.text())
        .done(function(result) {
                    this.preview(result);
        });
    };
    return ViewModel;
});
