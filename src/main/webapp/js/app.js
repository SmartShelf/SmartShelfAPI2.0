'use strict';


angular.module('smartshelf', ['ngRoute'])
        .
        config(['$routeProvider', function ($routeProvider) {

                $routeProvider.when('/', {
                    templateUrl: 'templates/welcome.html',
                    controller: 'DashboardCtrl'
                }).when('/shelfs', {
                    templateUrl: 'templates/shelfs.html',
                    controller: 'DashboardCtrl'
                }).when('/shelf', {
                    templateUrl: 'templates/shelf.html',
                    controller: 'DashboardCtrl'
                }).when('/dashboard', {
                    templateUrl: 'templates/welcome.html',
                    controller: 'DashboardCtrl'
                }).otherwise({
                    redirectTo: '/'
                });

            }])
.controller('DashboardCtrl', function () {
  // Do something with myService
});

