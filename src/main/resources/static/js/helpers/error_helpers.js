"use strict";

var errorHelper = new ErrorHelper();

function ErrorHelper() {
    function lowerizeFirstLetter(string) {
        return string.charAt(0).toLowerCase() + string.slice(1);
    };

    this.validateForms = function(form, scopeErrors, modelState) {
        angular.forEach(modelState, function(error, field) {
        	var existField = field;
            if (!form.hasOwnProperty(existField)) {
                console.log(existField + ' not found for validation');
            } else {
                form[existField].$setValidity('server', false);
                //scopeErrors[existField] = error.join(', '); // we don't want to add new error to the list since we need only last error 
                scopeErrors[existField] = error;
            }
        });
    };
}