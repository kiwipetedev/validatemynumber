'use strict';

angular
    .module('validate-app.ird.controller', [])
    .controller('IRDController', ['$scope', 'IRDValidationFactory', function ($scope, IRDValidationFactory)
{
    $scope.valid = null;
    $scope.submit = function()
    {
        IRDValidationFactory.get({irdnumber: $scope.irdnumber}, function (data)
        {
            $scope.valid = data.valid;
            $scope.irdnumber = data["formatted-ird-number"];
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
    
    $scope.irdNumberSelect = function()
    {
        if ($scope.valid == true)
        {
            $scope.irdnumber = null;
            $scope.valid = null;
        }
    }
}]);
