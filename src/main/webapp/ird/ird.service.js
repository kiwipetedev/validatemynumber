'use strict';

angular.module('validate-app.ird.service', ['ngResource', 'ngRoute']).factory('IRDValidationFactory', function ($resource)
{
    return $resource('webapi/irdnumber/:irdnumber')
});
