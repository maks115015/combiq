define(['ajax', 'knockout', 'js/lib/ace/ace'], function(ajax, ko) {

    function ViewModel(params) {
        this.text = ko.wrap(params.text);
        this.editorElement = ko.wrap();
        this.editorBoundElement = ko.wrap();
        this.active = ko.wrap(params.active || 'html'); // markdown, html
        this.preview = ko.wrap();

        if (params.markdownBoxModel) {
            params.markdownBoxModel(this);
        }

        if (this.active() != 'markdown') {
            this.refreshPreview();
        }
    }

    ViewModel.prototype.init = function() {
        var self = this;
        this.editorBoundElement().resizable({
            handleSelector: $('.co-markdown__resizehandler', this.editorBoundElement()),
            resizeWidth: false,
            onDragEnd: function() {
                self.editor.resize();
            }
        });
        var element = this.editorElement().get()[0];
        this.editor = ace.edit(element);
        this.editor.getSession().setMode("ace/mode/markdown");
        this.editor.renderer.setShowGutter(false);
        this.editor.renderer.setShowPrintMargin(false);
        this.editor.getSession().setUseWrapMode(true);
        this.editor.getSession().setWrapLimitRange();
    };

    ViewModel.prototype.focus = function() {
        var self = this;

        if (this.active != 'markdown') {
            this.active('markdown');
            setTimeout(function() {
                self.editor.focus();
            }, 1);
        } else {
            self.editor.focus();
        }
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
                self.focus();
            });
    };

    ViewModel.prototype.insertText = function(text) {
        this.editor.insert(text);
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
