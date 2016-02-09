define(['ajax'], function(ajax) {

    function ViewModel(params) {
        this.value = ko.wrap(params.value);
        this.url=ko.unwrap(params.url);
    }
    ViewModel.prototype.applyClick=function() {
    $.ajax({
              url: this.url,
              data: JSON.stringify({content: this.value()}),
              contentType: 'application/json',
              method: 'POST',
              success: function(result) { }
    });
    }
    return ViewModel;
});