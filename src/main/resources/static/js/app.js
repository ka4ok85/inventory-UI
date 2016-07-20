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

var app = angular.module('inventoryApp', [
                                          'ngRoute', 
                                          'initFromForm', 
                                          'restangular',
                                          'ui.router',
                                          //'inventory.login',
                                          'angular-jwt',
                                          'angular-storage'
                                          ]
);


app.config( function myAppConfig ($urlRouterProvider, $routeProvider, jwtInterceptorProvider, $httpProvider, $stateProvider) {
	console.log('1');  
	//$urlRouterProvider.otherwise('/ui/login');
	  //$urlRouterProvider.otherwise('/ui/login?'+dummyParam());

	  jwtInterceptorProvider.tokenGetter = function(store) {
		  console.log('11');
		  console.log(store.get('jwt'));
	    return store.get('jwt');
	  }
	  $stateProvider.state('login', {
		    url: '/login',
		    controller: 'LoginCtrl',
		    templateUrl: 'ui/login.html'
	  });  
	  $stateProvider.state('restatement_jobs', {
		    url: '/ui/restatement_jobs',
		    controller: 'ListCtrl',
		    templateUrl: 'ui/restatement_jobs.html'
	  });  
	  /*
        //.when('/:dval?',{
            //templateUrl: function(params){ return '/ui/restatement_jobs?'+dummyParam()},
            //controller:  'ListCtrl'
        //})
	   * 
	  $routeProvider
	  .when('/:dval?',{
	        templateUrl: function(params){ return '/ui/login?'+dummyParam()},
	        controller:  'LoginCtrl'
	  });
	  */
	  $httpProvider.interceptors.push('jwtInterceptor');
})
app.run(function($rootScope, $state, store, jwtHelper) {
	console.log('2');
    if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
  	  console.log('44');

      $state.go('login');
    } else {
    	$state.go('restatement_jobs');
    }
	
  $rootScope.$on('$stateChangeStart', function(e, to) {
	  console.log('22');
    if (to.data && to.data.requiresLogin) {
    	console.log('222');
      if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
    	  console.log('2222');
        e.preventDefault();
        $state.go('login');
        
      }
    }
  });
})
app.controller( 'AppCtrl', function AppCtrl ($scope, $location ) {
  console.log('3');
//app.controller('LoginCtrl', function($scope, $http, $location, Restangular){

  $scope.$on('$routeChangeSuccess', function(e, nextRoute){
	  console.log('33');
	  console.log(nextRoute);
    //if ( nextRoute.$$route && angular.isDefined( nextRoute.$$route.pageTitle ) ) {
    	console.log('333');
      //$scope.pageTitle = nextRoute.$$route.pageTitle + ' | ngEurope Sample' ;
    //}
  });
})

app.controller('LoginCtrl', function($scope, $http, $location, store, $state, Restangular){
	  console.log('login controller');
	$http.defaults.headers.post["Content-Type"] = "application/json";

    $scope.selectedStore = null;
    $scope.stores = [];
    $http({
    	method: 'GET',
        url: '/store_list'
    }).success(function (result) {
        $scope.stores = result;
    });
    
    $scope.processLogin = function(){
        var jobs = Restangular.all('ui/process_login');
        console.log($scope.auth.login);
        console.log($scope.auth.store);
        jobs.post($scope.auth).then(function(response){
            store.set('jwt', response.token);
            $state.go('restatement_jobs');
        }, function() {
            console.log('Error during login.');
        });
    }
    
    
    /*
  $scope.login = function() {
    $http({
      url: 'http://localhost:3001/sessions/create',
      method: 'POST',
      data: $scope.user
    }).then(function(response) {
      store.set('jwt', response.data.id_token);
      $state.go('home');
    }, function(error) {
      alert(error.data);
    });
  }
     */
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
          /*               
.config( function myAppConfig ($urlRouterProvider, jwtInterceptorProvider, $httpProvider) {
  $urlRouterProvider.otherwise('/');

  jwtInterceptorProvider.tokenGetter = function(store) {
    return store.get('jwt');
  }

  $httpProvider.interceptors.push('jwtInterceptor');
})
.run(function($rootScope, $state, store, jwtHelper) {
  $rootScope.$on('$stateChangeStart', function(e, to) {
    if (to.data && to.data.requiresLogin) {
      if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
        e.preventDefault();
        $state.go('login');
      }
    }
  });
})
.controller( 'AppCtrl', function AppCtrl ( $scope, $location ) {
  $scope.$on('$routeChangeSuccess', function(e, nextRoute){
    if ( nextRoute.$$route && angular.isDefined( nextRoute.$$route.pageTitle ) ) {
      $scope.pageTitle = nextRoute.$$route.pageTitle + ' | ngEurope Sample' ;
    }
  });
})
*/


/*

.config( function myAppConfig ($urlRouterProvider, jwtInterceptorProvider, $httpProvider) {
  $urlRouterProvider.otherwise('/');

  jwtInterceptorProvider.tokenGetter = function(store) {
    return store.get('jwt');
  }

  $httpProvider.interceptors.push('jwtInterceptor');
})

*/





/*
app.config(function($routeProvider){

    $routeProvider
        .when('/:dval?',{
            templateUrl: function(params){ return '/ui/login?'+dummyParam()},
            controller:  'LoginCtrl'
        })
        //.when('/:dval?',{
            //templateUrl: function(params){ return '/ui/restatement_jobs?'+dummyParam()},
            //controller:  'ListCtrl'
        //})
        .when('/restatement_job/:restatement_job',{
            templateUrl: function(params){ return '/ui/restatement_job/'+params.restatement_job+'?'+dummyParam(); },
            controller:  'ReadCtrl'
        })
        .when('/add_restatement_job/:dval?', {
            templateUrl: function(params){ return '/ui/add_restatement_job?'+dummyParam()},
            controller: 'NewCtrl'
        })
});

app.controller('LoginCtrl', function($scope, $http, $location, Restangular){
    $http.defaults.headers.post["Content-Type"] = "application/json";

    //$scope.viewJob = function(ident){
        //$location.path("/restatement_job/"+ident);
    //}

    //$scope.deleteJob = function(ident){
        //$http.delete("/jobs/"+ident).
            //success(function (data){
                //$location.path("/"+dValue())
            //});
    //}

    //$scope.addRestatementJob = function(){
        //$location.path("/add_restatement_job/"+dValue());
    //}
    $scope.processLogin = function(){
        var jobs = Restangular.all('ui/process_login');
        console.log($scope.auth.login);
        console.log($scope.auth.store);
        jobs.post($scope.auth).then(function(){
            $location.path("/");
        }, function() {
            console.log('Error during login.');
        });
    }
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

    $scope.processNewJob = function(){
        var jobs = Restangular.all('ui/add_restatement_job_process');
        jobs.post($scope.jobData).then(function(){
            $location.path("/");
        }, function() {
            console.log('Error saving new restatement job.');
        });
    }
});
*/