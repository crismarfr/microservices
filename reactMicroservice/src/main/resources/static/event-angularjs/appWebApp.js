'use strict';
var myApp = angular.module('myapp', ['ngRoute'])

myApp.controller('MyController', function($scope, $http) {
    var self = this;
     
    self.idConseiller = "DUPONT";
    
    self.idClient = "1234567";
        		
    self.useImpl = "spring5";
    
    self.eventToSend = "";
    self.formattedEvents = '';
    
    self.eventSource = null;
    self.lastEventId = 0;
    self.connStatus = '';
    
     	
	self.context = JSON.parse(window.localStorage.getItem(""+self.idConseiller+self.idClient)) || [];
	
	if(self.context[0] !== undefined)
	{
    	self.idConseiller=self.context[0].idConseiller;
    	self.idClient=self.context[0].idClient;
	}    
    
    self.onInit = function() {
        // $scope.on("destroy", function() { self.onDispose(); });
    	if (self.eventSource!=null)
    		self.onDispose();
    		
        self.connectEventSource();
    };
    
    self.onDispose = function() {
        console.log("onDispose");
        self.eventSource.close();
        self.eventSource = null;
    };
    
    self.connectEventSource = function() {
 	    	
        console.log("subscribe EventStream: " + self.idConseiller + " useImpl:" + self.useImpl + " lastEventId:" + self.lastEventId);
        self.eventSource = new EventSource('/app/event/' + self.idConseiller + '/' + self.lastEventId + '/subscribeEventsSpring' + (self.useImpl === 'spring4'? '4' : '5'));
        
        if (self.lastEventId > 0) {
            self.eventSource.id = self.lastEventId;
        }
        
        self.eventSource.addEventListener('open', function(e) {
            console.log("onopen", e);
            self.connStatus = 'connected';
            $scope.$apply();
        }, false);

        self.eventSource.addEventListener('error', function(e) {
            if (e.eventPhase == EventSource.CLOSED) {
              console.log('connection closed (..reconnect)', e);
              self.connStatus = 'connection closed (..auto reconnect in 3s)';
            } else {
              console.log("onerror", e);
              self.connStatus = 'error\n';
            }
            $scope.$apply();
          }, false);
        
        self.eventSource.addEventListener('event', function(e) {
            console.log("onmessage", e);
            self.handleMessageEvent(e);
            $scope.$apply();
        }, true);
        
    }

    self.handleMessageEvent = function(e) {
        var msg = JSON.parse(e.data);
        if (Array.isArray(msg)) { // sometimes event.eventPhase === 0 ... sometimes == 2
            // reconnect? receive several messages since 'last-message-id' ?
            console.log("event.data is Array?? ", msg);
            for(var i = 0; i < msg.length; i++) {
                console.log("msg[" + i + "]", msg[i]);
                // var subevent = JSON.parse(msg[i]); 
                // self.handleChatMsg(subevent.data);
            }
        } else {
            self.handleEventMsg(msg);
        }
    }
    
    self.handleEventMsg = function(msg) {
        self.eventSource.id = msg.id;
//        self.lastEventId = msg.id;
        if (msg.idClient == self.idClient) {
            self.addFormattedEvent(msg.id, new Date(msg.date), msg.idClient, msg.msg);
                      
            if(msg.msg === "Close event")
            {
            	console.log(""+msg.msg); 
            	
            	self.lastEventId = self.eventSource.id;
            	
            	self.onClickRemoveMessageEvent();
        	
           		self.onDispose();
           	    window.close();
            }
        }
    };
    
    self.onClickSendEvent = function () {
        console.log("send");
        var msg = self.eventToSend;
        // self.addFormattedEvent(new Date(), self.idClient, msg);
        
        var req = { onBehalfOf: self.idClient, msg };
        self.sending = true;
        $http.post("/app/event/" + self.idConseiller, req)
        .then(function() {
          self.sending = false;
        }, function(err) {
          self.addFormattedEvent(-1, new Date(), self.idClient, "Failed to send " + msg + ":" + err);
          self.sending = false;
        });
    };
    
    self.onClickRemoveMessageEvent = function () {
        console.log("remove"); 
        var msg = self.eventToSend;
        var req = { onBehalfOf: self.idClient, msg};
        self.sending = true;
        $http.post("/app/event/remove/messages/" + self.idConseiller, req)
        .then(function() {
          self.sending = false;
        }, function(err) {
          self.addFormattedEvent(-1, new Date(), self.idClient, "Failed to remove event stream conseiller " + self.idConseiller + +" et client " + self.idClient + ":" + err);
          self.sending = false;
        });
    };
    
    self.onClickSendEventClose = function () {
        console.log("close");
        var msg = "Close event";
        // self.addFormattedEvent(new Date(), self.idClient, msg);
        
        var req = { onBehalfOf: self.idClient, msg };
        self.sending = true;
        $http.post("/app/event/" + self.idConseiller, req)
        .then(function() {
          self.sending = false;
        }, function(err) {
          self.addFormattedEvent(-1, new Date(), self.idClient, "Failed to send " + msg + ":" + err);
          self.sending = false;
        });
    };
    
    self.addFormattedEvent = function(id, date, idClient, msg) {
       self.formattedEvents += "[" + id + "] " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " " +  
           ((self.idClient === idClient)? "" : "(" + idClient + ") ") + 
           msg + "\n\n";
    }
    
    self.onClickReconnect = function() {
   	
        self.formattedEvents += "** Click RECONNECT **";
        self.onDispose();
        self.connectEventSource();
    };
    
    self.onInit();
});
