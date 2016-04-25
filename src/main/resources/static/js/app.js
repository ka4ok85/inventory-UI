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
        .when('/planet/:planet_id',{
            templateUrl: function(params){ return '/ui/readplanet?id='+params.planet_id+'&'+dummyParam(); },
            controller:  'ReadCtrl'
        })
        .when('/addplanet/:dval?', {
            templateUrl: function(params){ return '/ui/addplanet?'+dummyParam()},
            controller: 'NewCtrl'
        })
});

app.controller('ListCtrl', function($scope, $http, $location) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $scope.viewPlanet = function(ident){
        $location.path("/planet/"+ident);
    }

    $scope.deletePlanet = function(ident){
        $http.delete("/planets/"+ident).
            success(function (data){
                $location.path("/"+dValue())
            });
    }

    $scope.addPlanet = function(){
        $location.path("/addplanet/"+dValue());
    }

});

app.controller('ReadCtrl', function($scope, $http, $location, Restangular) {

    $scope.planetData = {};

    $scope.listView = function(){
        $location.path("/");
    }

    $scope.processUpdateForm = function(){
        var pData = $scope.planetData;
        Restangular.one("planets").customPUT(pData, pData.id).then(function(planet){
            // updated
        });
    }
});

app.controller('NewCtrl', function($scope, $http, $location, Restangular){
    $scope.planetData = {};

    $scope.listView = function(){
        $location.path("/");
    }

    $scope.processNewPlanet = function(){
        var planets = Restangular.all('planets');
        planets.post($scope.planetData).then(function(){
            $location.path("/");
        }, function() {
            console.log('Error saving new planet.');
        });
    }
});
