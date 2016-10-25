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
                                          'ngCacheBuster',
                                          'initFromForm', 
                                          'restangular',
                                          'ui.router',
                                          'inventory.product',
                                          'angular-jwt',
                                          'angular-storage'
                                          ]
);


app.factory('loggedUserService', function($rootScope) {
    var sharedService = {};
    
    sharedService.login = "";
    sharedService.store = "";
    sharedService.isLogged = "0";

    sharedService.prepForBroadcast = function(login, store) {
        this.login = login;
        this.store = store;
        if (login != "" && store != "") {
        	this.isLogged = "1";
        } else {
        	this.isLogged = "0";
        }
        
        this.broadcastItem();
    };

    sharedService.broadcastItem = function() {
        $rootScope.$broadcast('handleBroadcast');
    };

    return sharedService;
});


app.config( function myAppConfig ($urlRouterProvider, $routeProvider, jwtInterceptorProvider, $httpProvider, $stateProvider, httpRequestInterceptorCacheBusterProvider) {
      console.log('app config start');  

      httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*ui.*/],true);

      jwtInterceptorProvider.tokenGetter = function(store) {
		console.log("stored token " + store.get('jwt'));
	    return store.get('jwt');
	  }

	  $stateProvider.state('login', {
		    url: '/login',
		    controller: 'LoginCtrl',
		    templateUrl: 'ui/login.html'
	  });  

	  $stateProvider.state('restatement_jobs', {
		    url: '/ui/restatement_jobs',
		    cache: false,
		    controller: 'ListCtrl',
		    templateUrl:function ($stateParams){
		        return 'ui/restatement_jobs' + '.html';
		    },
		    params: {
		        'userId': null, 
		        'storeId': null
		    }
	  });  

	  $stateProvider.state('restatement_job_add', {
		    url: '/ui/add_restatement_job',
		    controller: 'AddCtrl',
		    //templateUrl: 'ui/add_restatement_job.html',
		    templateUrl:function ($stateParams){
		        return 'ui/add_restatement_job' + '.html';
//		        return 'ui/add_restatement_job/' + $stateParams.storeId + '/' + $stateParams.userId + '.html';		        
		    },		    
		    params: {
		        'userId': null, 
		        'storeId': null
		    }
	  });

	  $stateProvider.state('restatement_job_view', {
		    url: '/ui/view_restatement_job',
		    controller: 'ViewCtrl',
		    //templateUrl: 'ui/view_restatement_job.html',
		    templateUrl:function ($stateParams){
		        return 'ui/view_restatement_job/' + $stateParams.jobId + '.html';
		    },		    
		    params: {
		        'jobId': null
		    }
	  });

	  $httpProvider.interceptors.push('jwtInterceptor');
})


app.run(function($rootScope, $state, store, jwtHelper) {
	console.log('app run start');
    if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
  	  console.log('jwt does not exist or expired');

  	  // force login
      $state.go('login');
    } else {
      // redirect to dashboard
      $state.go('restatement_jobs');
    }
	
    $rootScope.$on('$stateChangeStart', function(e, to) {
	  console.log('stateChangeStart');
      if (to.data && to.data.requiresLogin) {
        console.log('to.data && to.data.requiresLogin - passed');
        if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
      	  console.log('stateChangeStart - force login');
          e.preventDefault();
          $state.go('login');
        }
      }
    });
})

app.controller( 'AppCtrl', function AppCtrl ($scope, $location, $state, store, loggedUserService ) {
  console.log('3');

  $scope.loggedUser = "";
  $scope.currentStore = "";
  $scope.isLogged = "0";
  $scope.userRoles = null;
 

  $scope.$on('handleBroadcast', function() {
	  $scope.loggedUser = loggedUserService.login;
      $scope.currentStore = loggedUserService.store;
      $scope.isLogged = loggedUserService.isLogged;
  }); 
  
  $scope.$on('$routeChangeSuccess', function(e, nextRoute){
	  console.log('33');
	  console.log(nextRoute);
    //if ( nextRoute.$$route && angular.isDefined( nextRoute.$$route.pageTitle ) ) {
    	console.log('333');
      //$scope.pageTitle = nextRoute.$$route.pageTitle + ' | ngEurope Sample' ;
    //}
  });
  
  $scope.logout = function() {
  	console.log('log out.');
  	store.remove('jwt');
  	loggedUserService.prepForBroadcast("", "");
  	$state.go('login');
  	
  	//redirect
  };
  
  // Main menu links
  $scope.callFunction = function (name){
      if(angular.isFunction($scope[name])) {
    	  $scope[name](); 
      }
  }
  
  $scope.navmenu = [];
  var restatementJobs = {title: "Restatement Jobs", clickFunction: "restatementjobs"};
  $scope.navmenu.push(restatementJobs);  
  var products = {title: "Products", clickFunction: "products"};
  $scope.navmenu.push(products);    

  $scope.selectedIndex = 0;
  $scope.itemClicked = function ($index) {
    $scope.selectedIndex = $index;
  }

  $scope.restatementjobs = function() {
	  	$state.go('restatement_jobs');
  };
  $scope.products = function() {
	  	$state.go('products');
  };
	  
})

app.controller('LoginCtrl', function($scope, $http, $location, store, $state, jwtHelper, Restangular, loggedUserService){
    console.log('login controller start');
	$http.defaults.headers.post["Content-Type"] = "application/json";

	
	// get data for Store dropdown 
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
        jobs.post($scope.auth).then(function(response){
        	console.log("token " + response.token);
        	console.log("message " + response.message);
        	if (angular.isUndefined(response.token)) {
                console.log('Wrong credentials.');
        	    $scope.errors = {};
        	    var modelState = {password: response.message};
        	    errorHelper.validateForms($scope.loginForm, $scope.errors, modelState);
        	    
        	} else {
                console.log('Succeeded login attempt.');
                store.set('jwt', response.token);
                
                var tokenPayload = jwtHelper.decodeToken(response.token);
                console.log(tokenPayload);
                console.log("Token Username:"+tokenPayload.sub);
                console.log("Token User ID:"+tokenPayload.sub_id);
                console.log("Token StoreId:"+tokenPayload.store);
                
                loggedUserService.prepForBroadcast(tokenPayload.sub, tokenPayload.store_name);
                //$state.go('restatement_jobs');
                $state.go('restatement_jobs', { 'userId':tokenPayload.sub_id, 'storeId':tokenPayload.store });
                
        	}
        }, function() {
            console.log('Backend error during login.');
        });
    }
    

});

app.controller('ListCtrl', function($scope, $http, $location, $state, $stateParams) {
    console.log('restatement job controller start');
    console.log($stateParams.userId);
    console.log($stateParams.storeId);
    
    console.log('restatement job controller start');
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $scope.viewJob = function(ident){
        //$location.path("/restatement_job/"+ident);
        $state.go('restatement_job_view', { 'jobId':ident });
    }

    $scope.deleteJob = function(ident){
        $http.delete("/jobs/"+ident).
            success(function (data){
                $location.path("/"+dValue())
            });
    }

    $scope.addRestatementJob = function(){
        //$location.path("/add_restatement_job/"+dValue());
    	console.log('addRestatementJob start');
    	//$state.go('restatement_job_add', { 'userId':$stateParams.userId, 'storeId':$stateParams.storeId });
    	$state.go('restatement_job_add');
    }
    //$state.go('restatement_jobs', { 'userId':tokenPayload.sub_id, 'storeId':tokenPayload.store });


});      


app.controller('AddCtrl', function($scope, $http, $location, $state, $stateParams, Restangular) {
    console.log('add restatement job controller start');

    $scope.listView = function() {
        $state.go('restatement_jobs', { 'userId':$stateParams.userId, 'storeId':$stateParams.storeId });
    } 
    
    //$scope.jobData = {'storeId':$stateParams.storeId};
    $scope.jobData = {};
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $scope.processNewJob = function(){
        var jobs = Restangular.all('ui/add_restatement_job_process');
        jobs.post($scope.jobData).then(function(){
            $state.go('restatement_jobs', { 'userId':$stateParams.userId, 'storeId':$stateParams.storeId }, {reload: true, inherit: false, notify: true});
        }, function() {
            console.log('Error saving new restatement job.');
        });
    }
});      

app.controller('ViewCtrl', function($scope, $http, $location, $state, $stateParams, Restangular) {
    console.log('view  restatement job controller start');

    $scope.listView = function() {
        $state.go('restatement_jobs');
    } 
    

});      



//

//
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