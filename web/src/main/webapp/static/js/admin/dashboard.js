var dashboard = (function() {

    function Dashboard() {

    }

    Dashboard.prototype.submitJob = function(job) {
        require(['ajax'], function(ajax) {
            ajax
                .rest('POST', '/admin/job', {job: job})
                .done(function() {
                    new Dialog('Задача успешно выполнена.');
                });
        })
    };

    return new Dashboard();
})();