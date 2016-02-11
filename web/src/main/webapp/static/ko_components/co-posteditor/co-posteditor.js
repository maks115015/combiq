define(['knockout', 'ajax'], function(ko, ajax) {

    function ViewModel(params) {
        this.title = ko.wrap(params.title);
        this.content = ko.wrap(params.content);
        this.published = ko.wrap(params.published);
        this.postId = ko.wrap(params.postId);
    }

    ViewModel.prototype.save = function() {
        var self = this;

        var json = {
            postId: this.postId(),
            title: this.title(),
            content: this.content(),
            published: !!this.published()
        };

        ajax
            .rest('POST', '/admin/posts/edit', json)
            .done(function(data) {
                self.postId(data.id);

                new Dialog('Статья успешно сохранена');
            });
    };

    return ViewModel;
});