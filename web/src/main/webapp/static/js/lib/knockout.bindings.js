ko.wrap = function(value) {
    return ko.isObservable(value)
        ? value
        : (value instanceof Array ? ko.observableArray(value) : ko.observable(value));
};

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

ko.bindingHandlers.ace = {
    init: function(element, valueAccessor, allBindingsAccessor) {
        var editor = window.ace.edit(element);
        var value = valueAccessor();
        editor.getSession().setValue(value() || '');

        var silent = false;

        value.subscribe(function(newValue) {
            if (!silent) {
                editor.getSession().setValue(newValue || '');
            }
        });

        editor.on('change', function() {
            silent = true;
            value(editor.getSession().getValue());
            silent = false;
        });
    }
};