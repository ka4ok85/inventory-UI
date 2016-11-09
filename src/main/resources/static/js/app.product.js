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
  
  $stateProvider.state('product_in_store_view', {
	    url: '/ui/view_product_in_store',
	    controller: 'ProductViewCtrl',
	    templateUrl:function ($stateParams){
	        return 'ui/view_product_in_store/' + $stateParams.productId + '.html';
	    },		    
	    params: {
	        'productId': null
	    }
  });
  
  $stateProvider.state('product_add_into_location', {
	    url: '/ui/add_product_into_location',
	    controller: 'ProductAddLocationCtrl',
	    templateUrl:function ($stateParams){
	        return 'ui/change_product_into_location/' + $stateParams.locationId + '/' + $stateParams.productId + '/add.html';
	    },		    
	    params: {
	        'locationId': null,
	        'productId': null
	    }
  });

  $stateProvider.state('product_remove_from_location', {
	    url: '/ui/remove_product_from_location',
	    controller: 'ProductRemoveLocationCtrl',
	    templateUrl:function ($stateParams){
	        return 'ui/change_product_into_location/' + $stateParams.locationId + '/' + $stateParams.productId + '/remove.html';
	    },		    
	    params: {
	        'locationId': null,
	        'productId': null
	    }
  });

  $stateProvider.state('product_add_into_new_location', {
	    url: '/ui/add_product_into_new_location',
	    controller: 'ProductAddNewLocationCtrl',
	    templateUrl:function ($stateParams){
	        return 'ui/change_product_into_new_location/' + $stateParams.productId + '/add.html';
	    },		    
	    params: {
	        'productId': null
	    }
  });

})


app.controller('ProductListCtrl', function($scope, $http, $location, $state, $stateParams) {
    console.log('product controller start');
    console.log($stateParams.userId);
    console.log($stateParams.storeId);
    
    console.log('product controller start');

    $scope.viewProduct = function(id){
        $state.go('product_in_store_view', { 'productId':id });
    }

});   


app.controller('ProductViewCtrl', function($scope, $http, $location, $state, $stateParams, Restangular) {
    console.log('view product in store controller start');

    $scope.listView = function() {
        $state.go('products');
    } 
 
    $scope.addProduct = function(locationId, productId) {
        $state.go('product_add_into_location', { 'locationId':locationId, 'productId':productId });
    } 

    $scope.removeProduct = function(locationId, productId) {
        $state.go('product_remove_from_location', { 'locationId':locationId, 'productId':productId });
    }
    
    $scope.addProductToNewLocation = function(productId) {
        $state.go('product_add_into_new_location', { 'productId':productId });
    } 
    
}); 


app.controller('ProductAddLocationCtrl', function($scope, $http, $location, $state, $stateParams, Restangular) {
    console.log('add product into location controller start');

    $scope.listView = function() {
        $state.go('products');
    } 

    $scope.viewProduct = function(id){
        $state.go('product_in_store_view', { 'productId':id });
    }

    $scope.productLocationChangeData = {'productId':$stateParams.productId, 'locationId':$stateParams.locationId, 'action':'add'};
    $http.defaults.headers.post["Content-Type"] = "application/json";
    $scope.processProductLocationChange = function(){
        var change = Restangular.all('ui/change_product_into_location_process');
        change.post($scope.productLocationChangeData).then(function(){
        	console.log($scope.productLocationChangeData.productId);
        	console.log($scope.productLocationChangeData.quantity);
            $state.go('product_in_store_view', { 'productId':$scope.productLocationChangeData.productId}, {reload: true, inherit: false, notify: true});
        }, function() {
            console.log('Error adding product to location.');
        });
    }
    
}); 

app.controller('ProductRemoveLocationCtrl', function($scope, $http, $location, $state, $stateParams, Restangular) {
    console.log('remove product from location controller start');

    $scope.listView = function() {
        $state.go('products');
    } 

    $scope.viewProduct = function(id){
        $state.go('product_in_store_view', { 'productId':id });
    }

    $scope.productLocationChangeData = {'productId':$stateParams.productId, 'locationId':$stateParams.locationId, 'action':'remove'};
    $http.defaults.headers.post["Content-Type"] = "application/json";
    $scope.processProductLocationChange = function(){
        var change = Restangular.all('ui/change_product_into_location_process');
        change.post($scope.productLocationChangeData).then(function(response){
        	console.log("response " + response)
        	console.log("message " + response.message);
        	if (angular.isUndefined(response.message)) {
                $state.go('product_in_store_view', { 'productId':$scope.productLocationChangeData.productId}, {reload: true, inherit: false, notify: true});
     	    
        	} else {
        		console.log('Can not remove product.');
        	    $scope.errors = {};
        	    var modelState = {quantity: response.message};
        	    errorHelper.validateForms($scope.productLocationChangeForm, $scope.errors, modelState);
        	}
        }, function() {
            console.log('Error removing product from location.');
        });
    }
    
}); 

app.controller('ProductAddNewLocationCtrl', function($scope, $http, $location, $state, $stateParams, Restangular) {
    console.log('add product into new location controller start');

    $scope.listView = function() {
        $state.go('products');
    } 

    $scope.viewProduct = function(id){
        $state.go('product_in_store_view', { 'productId':id });
    }

    $scope.productLocationChangeData = {'productId':$stateParams.productId, 'action':'add'};
    $http.defaults.headers.post["Content-Type"] = "application/json";
    $scope.processProductLocationChange = function(){
        var change = Restangular.all('ui/change_product_into_location_process');
        change.post($scope.productLocationChangeData).then(function(){
        	console.log($scope.productLocationChangeData.productId);
        	console.log($scope.productLocationChangeData.productId);
        	console.log($scope.productLocationChangeData.quantity);
            $state.go('product_in_store_view', { 'productId':$scope.productLocationChangeData.productId}, {reload: true, inherit: false, notify: true});
        }, function() {
            console.log('Error adding product to location.');
        });
    }
    
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