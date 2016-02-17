define(['ajax', 'knockout'], function(ajax, ko) {

    function ViewModel(params) {
        this.text = ko.wrap(params.text);
        this.textarea = ko.wrap();
        this.active = ko.wrap(params.active || 'html'); // markdown, html
        this.preview = ko.wrap();

        if (params.markdownBoxModel) {
            params.markdownBoxModel(this);
        }

        if (this.active() != 'markdown') {
            this.refreshPreview();
        }
    }

    ViewModel.prototype.focus = function() {
        var self = this;
        if (this.active != 'markdown') {
            this.active('markdown');
        }
        setTimeout(function() {
            self.textarea().focus();
        }, 1);
    };

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

        coMarkdown
            .toHtml(self.text())
            .done(function(html) {
                self.preview(html);
            });
    };

    return ViewModel;
});
