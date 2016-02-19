define(['knockout', 'ajax'], function(ko, ajax) {

    function ViewModel(params) {
        this.enabled = ko.wrap(params.enabled);
        this.element = ko.wrap();
    }

    ViewModel.prototype.change = function() {
        var self = this;

        var searchQuery = coSearch.getSearchQuery();
        searchQuery = searchQuery.replace(/comments:\d+/g, '');
        if (this.enabled()) {
            searchQuery = 'comments:' + 1 + ' ' + searchQuery.trim();
        }
        coSearch.setSearchQuery(searchQuery.trim());

        this.element().tooltipster('hide');
        this
            .getCountQuestions()
            .done(function(count) {
                var url = '/questions/search?q=' + encodeURIComponent(searchQuery);
                self.element().tooltipster('content', 'Нашлось <a href="' + url + '">' + count + ' вопрос(-ов)</a>');
                self.element().tooltipster('show');
            })
    };

    ViewModel.prototype.init = function() {
        this.element().tooltipster({
            position: 'right',
            interactive: true,
            trigger: 'custom',
            contentAsHTML: true,
            timer: 4000
        });
    };

    ViewModel.prototype.getCountQuestions = function() {
        var url = '/questions/search/count?q=' + encodeURIComponent(coSearch.getSearchQuery());
        return ajax
            .rest('GET', url, null, {smSilent: true})
            .then(function(response) {
                return response.count;
            });
    };

    return ViewModel;
});