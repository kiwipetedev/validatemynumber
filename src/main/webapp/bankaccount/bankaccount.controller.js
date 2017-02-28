'use strict';

angular
    .module('validate-app.bankaccount.controller', [])
    .controller('BankAccountController', ['$scope', 'BankAccountValidationFactory', function ($scope, BankAccountValidationFactory)
{
    $scope.valid = null;
    $scope.submit = function()
    {
        BankAccountValidationFactory.get({bankaccount: $scope.bankaccount}, function (data)
        {
            $scope.valid = data.valid;
            $scope.bankaccount = data["formatted-bank-account-number"];
        })
    }
    
    $scope.getInputStyle = function()
    {
        if ($scope.valid == null)
        {
            return null;
        }
        else if ($scope.valid == true)
        {
            return 'valid-class';
        }
        else
        {
            return 'error-class';
        }
    }
    
    $scope.bankAccountSelect = function()
    {
        if ($scope.valid == true)
        {
            $scope.bankaccount = null;
            $scope.valid = null;
        }
    }
}]);
