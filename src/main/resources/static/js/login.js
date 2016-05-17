angular.module( 'inventory.login', [
  'ui.router',
  'angular-storage'
])
app.config(function($stateProvider) {
	console.log('login config');
  $stateProvider.state('login', {
    //url: '/ui/login',
    //controller: 'LoginCtrl',
    //templateUrl: 'ui/login.html'
      templateUrl: function(params){ return '/ui/login?'+dummyParam()},
      controller:  'LoginCtrl'
  });
})


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