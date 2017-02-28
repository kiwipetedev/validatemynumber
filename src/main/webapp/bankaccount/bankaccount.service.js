'use strict';

angular.module('validate-app.bankaccount.service', ['ngResource', 'ngRoute']).factory('BankAccountValidationFactory', function ($resource)
{
    return $resource('webapi/bankaccount/:bankaccount')
});
