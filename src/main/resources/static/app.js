var appModule = angular.module('myApp', ['ngRoute']);

appModule.config(function($routeProvider, $locationProvider) {
    $routeProvider.when('/:ip?', {
        templateUrl: 'main.html',
        controller: 'MainCtrl'
    });
});


appModule.controller('MainCtrl', ['mainService','$scope', '$interval', '$routeParams', function(mainService, $scope, $interval, $routeParams) {
    $scope.logLines = [];
    $scope.ip = $routeParams.ip;
    $scope.timer = null;

    $scope.update = function() {
        mainService.getAll($scope.ip).then(function(lines) {
            $scope.logLines = lines;
        });
    }

    $scope.timer = $interval(function() {
        $scope.update();
    }, 1000);

    $scope.$on('$locationChangeStart', function(){
        $interval.cancel($scope.timer);
    });
}]);

appModule.service('mainService', function($http) {
    return {
        getAll : function(ip) {
            var url;
            if(ip) {
                url = '/log/' + ip + '/';
            }
            else {
                url = '/log/all'
            }
            return $http.get(url).then(function(response) {
                return response.data;
            });
        }
    };
});


