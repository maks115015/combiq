var coSearch = {

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