var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['mainService','$scope', '$interval', function(mainService, $scope, $interval) {
    $scope.logLines = [];

    $scope.update = function() {
        mainService.getAll().then(function(lines) {
            $scope.logLines = lines;
        });
    }

    $scope.stop = $interval(function() {
        $scope.update();
    }, 1000);
}]);

appModule.service('mainService', function($http) {
    return {
        getAll : function() {
            return $http.get('/log/all').then(function(response) {
                return response.data;
            });
        }
    };
});