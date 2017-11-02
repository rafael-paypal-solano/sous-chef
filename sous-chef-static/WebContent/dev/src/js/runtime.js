var RunTime = {
    applicationRepository: function() {
        return (location.protocol+"//"+location.hostname).toLowerCase();
    },
    isWeb: function() {
        return !this.applicationRepository().indexOf("file://") == 0;
    },
    isOffline: function() {
        return !this.isWeb();
    },

    host: function() {
        return this.isOffline() ? "http://localhost:8080" : "" ;
    }
}