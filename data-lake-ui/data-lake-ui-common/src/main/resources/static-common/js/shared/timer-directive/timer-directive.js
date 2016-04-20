(function () {

    var directive = function ($interval) {
        return {
            restrict: "EA",
            scope: {
               startTime:"=",
               refreshTime:"@"
            },
            link: function ($scope, element, attrs) {
                $scope.time = $scope.startTime;
                $scope.$watch('startTime',function(newVal,oldVal){
                    $scope.time = $scope.startTime;
                    format();
                })
                format();
                $scope.seconds =0;
                $scope.minutes =0;
                $scope.hours =0;
                $scope.days = 0;
                $scope.months = 0;
                $scope.years = 0;
                if($scope.refreshTime == undefined){
                    $scope.refreshTime = 1000;
                }
                function update() {
                    $scope.time +=$scope.refreshTime;
                    //format it
                    format();

                }

                function formatDaysHoursMinutesSeconds(ms){
                    days = Math.floor(ms / (24*60*60*1000));
                    daysms=ms % (24*60*60*1000);
                    hours = Math.floor((daysms)/(60*60*1000));
                    hoursms=ms % (60*60*1000);
                    minutes = Math.floor((hoursms)/(60*1000));
                    minutesms=ms % (60*1000);
                    sec = Math.floor((minutesms)/(1000));


                }

                function format(){
                    $scope.seconds = Math.floor(($scope.time / 1000) % 60);
                    $scope.minutes = Math.floor((($scope.time / (60000)) % 60));
                    $scope.hours = Math.floor($scope.time / 3600000);
                    $scope.days = 0;
                    $scope.months = 0;
                    $scope.years = 0;
                    if($scope.hours <0){
                        $scope.hours = 0;
                    }
                    if($scope.minutes <0){
                        $scope.minutes = 0;
                    }
                    if($scope.seconds <0){
                        $scope.seconds = 0;
                    }
                    var str = $scope.seconds+' sec';
                    if($scope.hours >0 || ($scope.hours ==0 && $scope.minutes >0)) {
                      str = $scope.minutes+' min '+str;
                    }
                    if($scope.hours >0){
                        str =  $scope.hours+' hrs '+str;
                    }

                    element.html(str);
                    element.attr('title',str)
                }

                var interval = $interval(update, $scope.refreshTime);

                var clearInterval = function() {
                    $interval.cancel(interval);
                   interval = null;
                }
                $scope.$on('$destroy', function() {
                    clearInterval()
                });
            }

        }
    };


    angular.module(COMMON_APP_MODULE_NAME)
        .directive('tbaTimerOld',['$interval', directive]);

}());