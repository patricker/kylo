define(['angular','feed-mgr/feeds/edit-feed/module-name','kylo-utils/LazyLoadUtil','vis','kylo-feedmgr','feed-mgr/feeds/module','feed-mgr/sla/module','angular-ui-router','kylo-feedmgr','feed-mgr/visual-query/module','angular-nvd3'], function (angular,moduleName,lazyLoadUtil, vis) {
    //LAZY LOADED into the application
    var module = angular.module(moduleName, []);
      // load vis in the global state
        if(window.vis === undefined) {
            window.vis = vis;
        }
    module.config(['$stateProvider',function ($stateProvider) {
        $stateProvider.state('feed-details',{
            url:'/feed-details/{feedId}',
            params: {
                feedId: null,
                tabIndex: 0
            },
            views: {
                'content': {
                    templateUrl: 'js/feed-mgr/feeds/edit-feed/feed-details.html',
                    controller: 'FeedDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                loadMyCtrl: lazyLoadController(['feed-mgr/feeds/edit-feed/FeedDetailsController'])
            },
            data:{
                breadcrumbRoot:false,
                displayName:'Edit Feed',
                module:moduleName
            }
        })

        function lazyLoadController(path){
            return lazyLoadUtil.lazyLoadController(path,['feed-mgr/feeds/edit-feed/module-require','feed-mgr/sla/module-require','feed-mgr/visual-query/module-require','angular-visjs']);
        }
    }]);




return module;



});