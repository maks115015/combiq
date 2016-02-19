var coSearch = {

    toggleCheckBoxOnlyWithComments: function(checked) {
        var searchQuery = this.getSearchQuery();
        searchQuery = searchQuery.replace(/comments:\d+/g, '');
        if (checked) {
            searchQuery = 'comments:' + 1 + ' ' + searchQuery.trim();
        }
        this.setSearchQuery(searchQuery.trim());
    },

    getSearchQuery: function() {
        return $('#searchBox').val() || '';
    },

    setSearchQuery: function(query) {
        $('#searchBox').val(query);
    }
};

var coMarkdown = {

    toHtml: function(markdown) {
        markdown = ko.unwrap(markdown);

        return $.ajax({
            url: '/markdown/preview',
            datatype: 'text',
            contentType: 'text/plain',
            data: markdown,
            method: 'POST'
        });
    }
};