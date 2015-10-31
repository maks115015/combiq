define([], function() {
    var exports = {};

    exports.isSuccessResponse = function(response) {
        return response && (response.status == 'SUCCESS' || response.success == true);
    };

    exports.getErrorMessage = function(response) {
        return response && response.message;
    };

    /**
     * @deprecated
     */
    exports.post = function(url, params, callback, failCallback, ajax) {
        var defaultAjaxOptions = {
            type: 'POST',
            url: url,
            data: params,
            success: function(data, textStatus, request) {
                var silent = ajax && ajax.smSilent;
                var debug = ajax && ajax.smDebug;

                var successResponseResolver = (ajax && ajax.smSuccessResponseResolver) || exports.isSuccessResponse;

                // payload
                if (successResponseResolver(data)) {
                    try {
                        if (callback) {
                            callback(data, textStatus, request);
                        }
                    } catch (e) {
                        if (!silent) ko.openDialog('co-error', { message: 'Inner: ' + e });
                    }
                } else {
                    try {
                        var useDefaultCatchDeferred = new $.Deferred();

                        if (failCallback) {
                            var useDefaultCatch = failCallback(data, useDefaultCatchDeferred);
                            if (useDefaultCatch !== undefined) {
                                useDefaultCatchDeferred.resolve(useDefaultCatch);
                            }
                        }

                        useDefaultCatchDeferred
                            .promise()
                            .done(function(useDefaultCatch) {
                                if (useDefaultCatch !== false) {
                                    if (!silent) {
                                        var errorMessageResolver = (ajax && ajax.smErrorMessageResolver) || exports.getErrorMessage;
                                        var errorMessage = errorMessageResolver(data);
                                        ko.openDialog('co-error', { message: errorMessage });
                                    }
                                }
                            });
                    } catch (e) {
                        if (!silent) ko.openDialog('co-error', { message: e });
                    }
                }
            }
        };
        $.ajax($.extend(defaultAjaxOptions, ajax));
    };

    exports.deferredPost = function(url, params, jqueryAjax) {
        var deferred = new $.Deferred();
        var silent = jqueryAjax && jqueryAjax.smSilent;
        this.post(
            url,
            params,
            function(data, textStatus, request) {
                deferred.resolve(data, textStatus, request);
            }, function(data, useDefaultCatchDeferred) {
                deferred.reject(data, useDefaultCatchDeferred);
                if (useDefaultCatchDeferred.state() == 'pending') {
                    useDefaultCatchDeferred.resolve(true);
                }
            },
            $.extend({
                error: function(jqXHR, textStatus, errorThrown) {
                    var message = ['Сетевая ошибка', textStatus, jqXHR.status, errorThrown]
                        .filter(Boolean)
                        .join(', ');
                    var errorData = {
                        status: 'ERROR',
                        message: message,
                        code: jqXHR.status
                    };
                    if (jqueryAjax && jqueryAjax.smRequestErrorMessageResolver) {
                        var error = jqueryAjax.smRequestErrorMessageResolver(jqXHR);
                        if (error) {
                            errorData.message = error;
                        }
                    }
                    if (!silent) ko.openDialog('co-error', { message: errorData.message });
                    deferred.reject(errorData, new $.Deferred()); // Network error.
                }
            }, jqueryAjax)
        );
        return deferred.promise();
    };

    exports.rest = function(method, url, params, jqueryAjax) {
        var restSuccessResponseResolver = function(response) {
            return response && response.success;
        };

        var restErrorMessageResolver = function(response) {
            return response && response.error;
        };

        var restRequestErrorMessageResolver = function(jqXHR) {
            return jqXHR && jqXHR.responseJSON && jqXHR.responseJSON.error;
        };

        if (method == 'GET') {
            var defaultAjaxOptions = {
                method: method,
                smSuccessResponseResolver: restSuccessResponseResolver,
                smErrorMessageResolver: restErrorMessageResolver,
                smRequestErrorMessageResolver: restRequestErrorMessageResolver
            };
            return exports.deferredPost(url, params, $.extend(defaultAjaxOptions, jqueryAjax));
        } else {
            var defaultAjaxOptions = {
                method: method,
                contentType: 'application/json',
                smSuccessResponseResolver: restSuccessResponseResolver,
                smErrorMessageResolver: restErrorMessageResolver,
                smRequestErrorMessageResolver: restRequestErrorMessageResolver
            };
            if (params) {
                params = JSON.stringify(params);
            }
            return exports.deferredPost(url, params, $.extend(defaultAjaxOptions, jqueryAjax));
        }
    };

    return exports;
});