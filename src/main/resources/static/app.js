var appModule = angular.module('myApp', ['ngRoute']);

appModule.config(function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/routes', {
            templateUrl: 'routes.html',
            controller: 'MainCtrl'
        })
        .when('/:ip?', {
        templateUrl: 'main.html',
        controller: 'MainCtrl'
    });

});


appModule.controller('MainCtrl', ['mainService','$scope', '$interval', '$routeParams', function(mainService, $scope, $interval, $routeParams) {
    $scope.logLines = [];
    $scope.ip = $routeParams.ip;
    $scope.timer = null;
    $scope.state = null;

    $scope.update = function() {
        mainService.getAll($scope.ip).then(function(lines) {
            $scope.logLines = lines;
        });
        if($scope.ip) {
            mainService.getState($scope.ip).then(function(state) {
                $scope.state = state;
            });
        }
    }

    $scope.timer = $interval(function() {
        $scope.update();
    }, 1000);

    $scope.$on('$locationChangeStart', function(){
        $interval.cancel($scope.timer);
    });

    $scope.update();
}]);

appModule.service('mainService', function($http) {
    var plainTextTransform = {
        transformResponse: function(data, headersGetter, status) {
            return data;
        }
    };
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
        },

        getState : function(ip) {
            return $http.get('/log/state/' + ip + '/', plainTextTransform).then(function(response) {
                return response.data;
            });
        }
    };
});


