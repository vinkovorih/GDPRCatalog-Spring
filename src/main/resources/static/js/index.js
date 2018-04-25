
var test = angular.module("test", ['ngRoute','ngMessages','ui.bootstrap']);

test.run(function($rootScope, $location) {
	$rootScope.backToFirst = function() {
		$location.path("/");
	}
	$rootScope.redirectedFromAdmin = false;
	$rootScope.loggedInUser = false;
});

test.config(function($routeProvider, $locationProvider) {
    $routeProvider.
    when('/', {
        templateUrl: 'templates/test.html',
        controller: 'testCtrl'
    })
    .otherwise({
        redirectTo: '/'
    }); 
});
test.controller("testCtrl", [ '$rootScope','$scope', '$http', '$location', '$compile',
		function($rootScope, $scope, $http, $location, $compile) {
}]);