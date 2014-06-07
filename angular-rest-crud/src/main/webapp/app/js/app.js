var booklandModule = angular.module('wines', ['ngRoute','ngResource'])
	.config(function ($routeProvider) {
		$routeProvider
			.when('/', {
				templateUrl: 'views/list.html',
				controller: 'ListController',
				controllerAs: 'listCtrl',
				name: 'list'
			})
			.when('/new', {
				templateUrl: 'views/edit.html',
				controller: 'NewController',
				controllerAs: 'newCtrl',
				name: 'new'
			})
			.when('/edit/:id', {
				templateUrl: 'views/edit.html',
				controller: 'EditController',
				controllerAs: 'editCtrl',
				name: 'edit'
			})
			.when('/detail/:id', {
				templateUrl: 'views/detail.html',
				controller: 'DetailController',
				controllerAs: 'detailCtrl',
				name: 'detail'
			})
			.otherwise({
				redirectTo: '/'
			});
		})
	.factory('wineService', ['$resource',
		function ($resource) {
			return $resource('../rest/wines/:id', {id: '@id'}, {
				'update': { method: 'PUT'}
		    });
	}])
	.controller('ListController', ['$scope', 'wineService',
	    function ($scope, wineService) {
	        $scope.items = wineService.query();
	        $scope.remove = function(id) {
	        	wineService.remove({'id': id});
	        	$scope.items = wineService.query();
	        };
	}])
	.controller('NewController', ['$scope', 'wineService', '$location',
	    function ($scope, wineService, $location) {
		    $scope.wine = {};
		    $scope.save = function() {
		    	wineService.save($scope.wine);
		        $location.path("/");
		    };
	}])
	.controller('EditController', ['$scope', 'wineService', '$location', '$routeParams',
	    function ($scope, wineService, $location, $routeParams) {
		    $scope.wine = wineService.get({},{'id': $routeParams.id});
		    $scope.save = function() {
		    	wineService.update($scope.wine);
		        $location.path("/");
		    };
	}])
	.controller('DetailController', ['$scope', '$routeParams', 'wineService', '$location',
	    function ($scope, $routeParams, wineService, $location) {
			$scope.detail = wineService.get({},{'id': $routeParams.id});
			var id = $routeParams.id;
			$scope.edit = function() {
				$location.path("/edit/"+id);
			};
	}]);
