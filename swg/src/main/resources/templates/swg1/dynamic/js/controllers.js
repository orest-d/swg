'use strict';

/* Controllers */

var galleryControllers = angular.module('galleryControllers', []);

galleryControllers.controller('SiteCtrl', ['$scope', '$http', '$rootScope',
    function($scope, $http, $rootScope) {
        $http.get('data/images.json').success(function(data) {
            $scope.images = data;
        });
        $http.get('data/translation.json').success(function(data) {
            $scope.translation = data;
        });
        $http.get('data/siteinfo.json').success(function(data) {
            $scope.siteinfo = data;
        });
        $scope.languagecode = function() {
            var lc = $$LANGUAGETOCODE$$;
            return lc[$scope.language];
        };
        $scope.translate = function(text) {
            return $scope.translation[text][$scope.languagecode()];
        };
        $scope.languages = $$LANGUAGES$$;

        $scope.language = "$$DEFAULTLANGUAGE$$";

        $scope.title = function() {
            if ($scope.siteinfo.title) {
                return $scope.siteinfo.title[$scope.languagecode()];
            }
            else {
                return "SWG";
            }
        };
        $scope.menutitle = function() {
            if ($scope.siteinfo.menutitle) {
                return $scope.siteinfo.menutitle[$scope.languagecode()];
            }
            else {
                return "SWG";
            }
        };
        $scope.$watch(
                function() {
                    return $scope.language;
                },
                function(newvalue, oldvalue) {
                    $rootScope.language = newvalue;
                    if (!$rootScope.$$phase) {
                        $rootScope.$apply();
                    }
                }
        );

    }]);

galleryControllers.controller('PageCtrl', ['$scope', '$http', '$rootScope', '$routeParams', '$location',
    function($scope, $http, $rootScope, $routeParams, $location) {
        $scope.galleries = [];
        $http.get('data/images.json').success(function(data) {
            $scope.images = data;
        });
        $http.get('data/galleries.json').success(function(data) {
            $scope.galleries = data;
            $scope.selectedImageNr = $routeParams.imageNr ? Math.floor($routeParams.imageNr) : Math.floor(Math.random() * ($scope.gallery().images.length));
        });
        $http.get('data/translation.json').success(function(data) {
            $scope.translation = data;
        });
        $scope.galleryId = $routeParams.galleryId ? $routeParams.galleryId : "default";
        $scope.articleId = $routeParams.articleId ? $routeParams.articleId : "default";
        $scope.article = function() {
            return "articles/" + $scope.articleId + ".html";
        };
        $scope.gallery = function() {
            if ($scope.galleries[$scope.galleryId]) {
                return $scope.galleries[$scope.galleryId];
            }
            else {
                return {"images": []};
            }
        };
        $scope.galleryName = function() {
            var g = $scope.gallery().name[$scope.languagecode()];
            return g ? g : "";
        };
        $scope.galleryText = function() {
            return $scope.gallery().text[$scope.languagecode()];
        };
        $scope.selectedImageNr = $routeParams.imageNr ? Math.floor($routeParams.imageNr) : Math.floor(Math.random() * ($scope.gallery().images.length));
        $scope.previousImageNr = function() {
            return $scope.selectedImageNr - 1;
        };
        $scope.nextImageNr = function() {
            return $scope.selectedImageNr + 1;
        };
        $scope.isPreviousImageNrValid = function() {
            return $scope.previousImageNr() >= 0;
        };
        $scope.isNextImageNrValid = function() {
            return $scope.nextImageNr() < $scope.gallery().images.length;
        };
        $scope.selectedImage = function() {
            return $scope.selectedImages()[$scope.selectedImageNr];
        };
        $scope.selectedImages = function() {
            var allimages = $scope.images;
            var selected = [];
            var gi = $scope.gallery().images;
            for (var i = 0; i < gi.length; i++) {
                var imageId = gi[i];
                for (var j = 0; j < allimages.length; j++) {
                    var img = allimages[j];
                    if (img.imageId == imageId) {
                        var iimg = img;
                        iimg.imageNr = i;
                        iimg.url = "images/" + imageId + ".jpg";
                        iimg.thumbnailUrl = "images/" + imageId + "t.png";
                        iimg.translatedName = iimg.name[$scope.languagecode()];
                        selected.push(iimg);
                    }
                }

            }
            return selected;
        };
        $scope.languagecode = function() {
            var lc = $$LANGUAGETOCODE$$;
            return lc[$scope.language];
        };
        $scope.translate = function(text) {
            if ($scope.languagecode() && $scope.translation) {
                if ($scope.translation[text]) {
                    var t = $scope.translation[text][$scope.languagecode()];
                    return t ? t : text;
                }
            }
            return text;
        };

        $scope.language = "English";
        $rootScope.$watch(
                function() {
                    return $rootScope.language;
                },
                function(newvalue, oldvalue) {
                    $scope.language = newvalue;
                    if (!$scope.$$phase) {
                        $scope.$apply();
                    }
                }
        );
        $scope.imageUrl = function() {
            {
                return $scope.selectedImage().url;
            }
        };
        $scope.imageAuthor = function() {
            {
                return $scope.selectedImage().author;
            }
        };
        $scope.imageName = function() {
            {
                if ($scope.selectedImage()) {
                    var t = $scope.selectedImage().translatedName;
                    return t ? t : $scope.selectedImageNr;
                }
                else
                    return "";
            }
        };
        $scope.imageTechnique = function() {
            {

                return $scope.translate($scope.selectedImage().technique);
            }
        };
        $scope.imageInception = function() {
            {
                return $scope.selectedImage().inception;
            }
        };
        $scope.imageSize = function() {
            var w = Math.round($scope.selectedImage().width);
            var h = Math.round($scope.selectedImage().height);
            return "" + w + " x " + h + " cm";
        };
        $scope.go = function(path) {
            $location.path(path);
        };
        $scope.goToPrevious = function() {
            if ($scope.isPreviousImageNrValid()) {
                $scope.go('/image/' + $scope.galleryId + '/' + $scope.previousImageNr());
            }
        };
        $scope.goToNext = function() {
            if ($scope.isNextImageNrValid()) {
                $scope.go('/image/' + $scope.galleryId + '/' + $scope.nextImageNr());
            }
        };
        $scope.goUp = function() {
            $scope.go('/gallery/' + $scope.galleryId);
        };
    }]);
