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