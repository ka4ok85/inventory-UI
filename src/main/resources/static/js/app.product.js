angular.module( 'inventory.product', [
  'ui.router',
  'angular-storage'
])
app.config(function($stateProvider) {
  console.log('product config');
  $stateProvider.state('products', {
	    url: '/ui/products',
	    cache: false,
	    controller: 'ProductListCtrl',
	    templateUrl:function ($stateParams){
	        return 'ui/products' + '.html';
	    },
	    params: {
	        'userId': null, 
	        'storeId': null
	    }
  });  
})

app.controller('ProductListCtrl', function($scope, $http, $location, $state, $stateParams) {
    console.log('product controller start');
    console.log($stateParams.userId);
    console.log($stateParams.storeId);
    
    console.log('product controller start');
    //$http.defaults.headers.post["Content-Type"] = "application/json";
/*
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
*/

});   

/*

//should be live
app.controller('LoginCtrl', function($scope, $http, $location, Restangular){
    $http.defaults.headers.post["Content-Type"] = "application/json";
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
*/





/*
.controller( 'LoginCtrl', function LoginController( $scope, $http, store, $state) {
	console.log('login controller');
  $scope.user = {};

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

});
 */