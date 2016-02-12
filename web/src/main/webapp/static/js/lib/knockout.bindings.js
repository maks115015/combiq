ko.bindingHandlers.init = {
    init: function(element, valueAccessor, allBindings, data) {
        var action = valueAccessor();
        if (typeof action === 'function') {
            setTimeout(function() {
                action.bind(data)();
            }, 0);
        }
    }
};

ko.bindingHandlers.element = {
    init: function(element, valueAccessor, allBindingsAccessor) {
        var value = valueAccessor();
        value($(element));
    }
};