var appModule = angular.module('myApp');

appModule.directive('line', ['mainService', function(mainService) {
    return {
         restrict: 'E',
         templateUrl: 'directives/line.html',

         scope: {
            line:"="
         },
         controller: function($scope) {
            $scope.getTime = function() {
                return new Date($scope.line.timestamp);
            }
         }
    };
}]);