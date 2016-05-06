var dValue = function()
{
    return new Date().getTime();
}

var dummyParam = function()
{
    return "_b="+dValue();
}

angular.module('initFromForm', [])
  .directive("initFromForm", function ($parse) {
    return {
      link: function (scope, element, attrs) {
        var attr = attrs.initFromForm || attrs.ngModel || element.attrs('name'),
        val = attrs.value;
        $parse(attr).assign(scope, val)
      }
    };
  });

var app = angular.module('planetsApp', ['ngRoute', 'initFromForm', 'restangular']);

app.config(function($routeProvider){
    $routeProvider
        .when('/:dval?',{
            templateUrl: function(params){ return '/ui/restatement_jobs?'+dummyParam()},
            controller:  'ListCtrl'
        })
        .when('/restatement_job/:restatement_job',{
            templateUrl: function(params){ return '/ui/restatement_job/'+params.restatement_job+'?'+dummyParam(); },
            controller:  'ReadCtrl'
        })
        .when('/add_restatement_job/:dval?', {
            templateUrl: function(params){ return '/ui/add_restatement_job?'+dummyParam()},
            controller: 'NewCtrl'
        })
});

app.controller('ListCtrl', function($scope, $http, $location) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $scope.viewJob = function(ident){
        $location.path("/restatement_job/"+ident);
    }

    $scope.deleteJob = function(ident){
        $http.delete("/jobs/"+ident).
            success(function (data){
                $location.path("/"+dValue())
            });
    }

    $scope.addRestatementJob = function(){
        $location.path("/add_restatement_job/"+dValue());
    }

});

app.controller('ReadCtrl', function($scope, $http, $location, Restangular) {

    $scope.jobData = {};

    $scope.listView = function(){
        $location.path("/");
    }

    $scope.processUpdateForm = function(){
        var pData = $scope.jobData;
        Restangular.one("jobs").customPUT(pData, pData.id).then(function(job){
            // updated
        });
    }
});

app.controller('NewCtrl', function($scope, $http, $location, Restangular){
    $scope.jobData = {};

    $scope.listView = function(){
        $location.path("/");
    }
/*
$scope.user = {email :"", password: "", confpass: ""};
Restangular.all('data.json/:user').post("users", $scope.user); 
 */
    $scope.processNewJob = function(){
        var jobs = Restangular.all('ui/add_restatement_job_process');
        jobs.post($scope.jobData).then(function(){
            $location.path("/");
        }, function() {
            console.log('Error saving new restatement job.');
        });
    }
});
