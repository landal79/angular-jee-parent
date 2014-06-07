var booklandModule = angular.module('app', ['ngResource'])
    .directive('fileModel', ['$parse',
        function ($parse) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    var model = $parse(attrs.fileModel);
                    var modelSetter = model.assign;

                    element.bind('change', function () {
                        scope.$apply(function () {
                            modelSetter(scope, element[0].files[0]);
                        });
                    });
                }
            };
  }]).service('fileUpload', ['$http',
        function ($http) {
            this.uploadFileToUrl = function (file, uploadUrl) {
                var fd = new FormData();
                fd.append('file', file);
                fd.append('fileName', file.name);
                return $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }
                }).success(function (data) {
                    console.info(data);
                }).error(function () {
                    console.error(data);
                });
            };
  }]).controller('upLoadCtrl', ['$scope', 'fileUpload',
        function ($scope, fileUpload) {
            $scope.uploadFile = function () {
                var file = $scope.myFile;
                console.log('file is ' + JSON.stringify(file));
                var uploadUrl = "../rest/file/upload";
                fileUpload.uploadFileToUrl(file, uploadUrl)
                    .then(function (data) {
                        $scope.uploadResult = data.data;
                    });
            };
    }]);