define(['ajax', 'knockout'], function(ajax, ko) {

    function ViewModel(params) {
        this.text = ko.wrap(params.text);
        this.textarea = ko.wrap();
        this.active = ko.wrap(params.active || 'html');
        this.preview = ko.wrap();

        if (this.active() != 'markdown') {
            this.refreshPreview();
        }
    }

    ViewModel.prototype.toggleSrc = function() {
        this.active('markdown');
    };

    ViewModel.prototype.togglePreview = function() {
        this.active('html');
        this.refreshPreview();
    };

    ViewModel.prototype.insertImage = function() {
        var self = this;

        ko.openDialog('co-uploadimage')
            .done(function(location) {
                var parts = location.split(':');
                self.insertText('![' + parts[parts.length - 1] + '](/markdown/image?loc=' + encodeURIComponent(location) + ')');
            });
    };

    ViewModel.prototype.insertText = function(myValue) {
        var textarea = this.textarea().get(0);

        if (document.selection) { //IE support
            textarea.focus();
            sel = document.selection.createRange();
            sel.text = myValue;
        } else if (textarea.selectionStart || textarea.selectionStart == '0') { //MOZILLA and others
            var startPos = textarea.selectionStart;
            var endPos = textarea.selectionEnd;
            textarea.value = textarea.value.substring(0, startPos)
                + myValue
                + textarea.value.substring(endPos, textarea.value.length);
        } else {
            textarea.value += myValue;
        }

        this.textarea().change();
    };

    ViewModel.prototype.refreshPreview = function() {
        var self = this;

        $.ajax({
            url: '/markdown/preview',
            datatype: 'text',
            contentType: 'text/plain',
            data: self.text(),
            method: 'POST',
            success: function(result) {
                self.preview(result);
            }
        });
    };

    return ViewModel;
});
