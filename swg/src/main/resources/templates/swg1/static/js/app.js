'use strict';

/* App Module */

var galleryApp = angular.module('galleryApp', [
  'ngRoute',
  'galleryControllers'
]);

galleryApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/gallery/:galleryId', {
        templateUrl: 'pages/gallery.html',
        controller: 'PageCtrl'
      }).
      when('/article/:articleId', {
        templateUrl: 'pages/article.html',
        controller: 'PageCtrl'
      }).
      when('/image/:galleryId/:imageNr', {
        templateUrl: 'pages/image.html',
        controller: 'PageCtrl'
      }).
      when('/image/all', {
        templateUrl: 'pages/image.html',
        controller: 'PageCtrl'
      }).
      otherwise({
        redirectTo: '/image/all'
      });
  }]);

