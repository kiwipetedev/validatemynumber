'use strict';

angular.module('validate-app', ['validate-app.bankaccount.service', 'validate-app.ird.service', 'validate-app.bankaccount.controller', 'validate-app.ird.controller', 'ui.bootstrap']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html'});
        $routeProvider.otherwise({redirectTo: '/view1'});
    }]);
