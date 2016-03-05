define([
    'knockout',
    'ajax',
    'js/lib/select2-4.0.2-rc.1/js/select2.min'
], function(ko, ajax, select2) {

    function ViewModel(params){
        var self = this;

        this.width = 700;
        this.align = 'top';
        this.buttons = [];
        this.sticked = true;

        this.saved = ko.wrap(false);
        this.saving = ko.wrap(false);
        this.id = ko.wrap(params.id);
        this.level = ko.wrap('D1');
        this.title = ko.wrap('');
        this.body = ko.wrap('');
        this.selectedTags = ko.wrap([]);
        this.tagsElement = ko.wrap();
        this.availableTags = ko.wrap([]);

        if (this.id()) {
            ajax.rest('GET', '/questions/' + encodeURIComponent(this.id()))
                .done(function(question) {
                    self.level(question.level);
                    self.title(question.title);
                    self.body(question.body && question.body.markdown);
                    self.selectedTags(question.tags || []);
                });
        }
    }

    ViewModel.prototype.init = function() {
        var self = this;
        var silent = false;

        ajax
            .rest('GET', '/tags')
            .done(function(result) {
                self.availableTags(result.map(function(tag) {return tag.value;}));
                self.tagsElement()
                    .select2({
                        tags: true
                    })
                    .on('change', function() {
                        silent = true;
                        self.selectedTags(self.tagsElement().val() || []);
                        silent = false;
                    });
                self.tagsElement().val(self.selectedTags()).trigger('change');
            });

        self.selectedTags.subscribe(function(newValue) {
            if (!silent) {
                self.tagsElement().val(newValue).trigger('change');
            }
        });
    };

    ViewModel.prototype.save = function() {
        var self = this;

        var json = {
            id: this.id() || null,
            title: this.title(),
            body: this.body(),
            level: this.level(),
            tags: this.selectedTags()
        };

        self.saving(true);
        self.saved(false);

        ajax.rest('POST', '/questions', json)
            .done(function(question) {
                self.id(question.id);
                self.saved(true);
            })
            .always(function() {
                self.saving(false);
            });
    };

    return ViewModel;
});