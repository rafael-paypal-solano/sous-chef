/**
 */

(function() {
    'use strict';
    var souschef = window.souschef; 
    	//We're using 'use strict', therefore we have to reference
        //global objects using the dot operator on the window object.
    if (souschef.ajax) {
        return souschef.ajax;
    }

	/**
	* @namespace Provides functions that encapsulate the boilerplate code around AJAX post/request calls. These functions replace $ (jquery) AJAX calls..
	*/
    souschef.ajax = {};

	//Convenience constant to indicate request type as GET.
	souschef.ajax.DELETE = 2;
	
	//Convenience constant to indicate request type as GET.
	souschef.ajax.GET = 1;
	
	//Convenience constant to indicate request type as POST.
	souschef.ajax.POST = 0;
	
	//Prefix urls with this string in order to indicate that the AJAX request must be synchronic.
	souschef.ajax.SYNC = "sync:";
	
	/**
	 * Converts a JSON object into an url-encoded an application/x-www-form-urlencoded string. The object keys become form fields.
	 * @param {string} url End point 
	 * @return A application/x-www-form-urlencoded string.
	 */
	 souschef.ajax.encode = function (content) {
        var data = [];
        var encoded;

        for (var key in content) {
            data.push(key + '=' + encodeURIComponent(content[key]) + '&');
        }

        encoded = data.join().replace(/\,/g,'');
        return encoded.substring(0, encoded.length - 1);
    };

	/**
	* Creates a configuration  object for AJAX requests.
	* @url {string} url An URL that may be prefixed by "sync:" which indicates that we want to execute a synchronic request.
	* @return A JS object containing the following properties.
	*
	* target : end point 
	*
	*/
	souschef.ajax.config = function (url){
		var pos = url.indexOf(souschef.ajax.SYNC);
		var sync = pos == 0;
		var config = {
			async: !sync,
			target : sync ? url.substring(pos + souschef.ajax.SYNC.length) : url
		};
		
		return config;
	};
	
	souschef.ajax.sync = function (url) {
		return souschef.ajax.config(souschef.ajax.SYNC+url);
	}
	
	/**
	* Sends an HTTP GET request to an end point. The url may include a query string.
	*
	* @param {string or AJAX config} url If it's a string, then it must a valid argument for souschef.ajax.config.
	* @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
	* @param {function} fail (optional) Callback function executed upon failure. (HTTP code != 200)
	*/
    souschef.ajax.get = function(url, success, fail) {
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;
		
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    success(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('GET', config.target, config.async);
        xhr.send(null);
    };

	/**
	* Sends an HTTP DELETE request to an end point. The url may include a query string.
	*
	* @param {string or AJAX config} url If it's a string, then it must a valid argument for souschef.ajax.config.
	* @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
	* @param {function} fail (optional) Callback function executed upon failure. (HTTP code != 200)
	*/
    souschef.ajax.remove = function(url, success, fail) {
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;
		alert(url);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    success(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('DELETE', config.target, config.async);
        xhr.send(null);
    };
    
    
	/**
	* Sends an HTTP GET request to an end point. The url may include a query string.
	*
	* @param {string or AJAX config} url If it's a string, then it must a valid argument for souschef.ajax.config.
	* @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
	* @param {function} fail (optional) Callback function executed upon failure. (HTTP code != 200)
	*/
    souschef.ajax.xml = function(url, success, fail) {
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;
               
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    success(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('GET', config.target, config.async);
        if(souschef.xpath.isIE())
        	xhr.responseType  =  'msxml-document';        
        xhr.send(null);
    };
    
	/**
	* Sends an HTTP POST request to an end point. The url should not include a query string.
	*
	* @param {string} url End point 
	* @param {object} data JSON object which is converted into an application/x-www-form-urlencoded string and posted to the server or, otherwise, a string that is posted unchanged to the server.
	* @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
	* @param {function} fail (optional) Callback function executed upon failure. (HTTP code != 200)
	*/	
    souschef.ajax.post = function (url, data, success, fail) {
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;
        var encoded = data !== null ? (typeof data === 'string' ? data : souschef.ajax.encode(data)) : data;

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    success(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('POST', config.target, config.async);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send(encoded);
    };

	/**
	* Sends an HTTP PUT request to an end point. The url should not include a query string.
	*
	* @param {string} url End point, it must include name of the file to be created.
	* @param {string} contentType String to set as Content-Type header.
	* @param {string} data Data sent to the server; binary arrays are converted to base64.
	* @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
	* @param {function} fail (optional) Callback function executed upon failure. (HTTP code != 200)
	*/    
    souschef.ajax.put = function (url, contentType, data, success, fail) {    
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    success(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('PUT', config.target, config.async);
        xhr.setRequestHeader('Content-type', contentType);
        xhr.send(data);    	
    };
    
	/**
	* Sends an HTTP DELETE request to an end point. The url query string should include the search parameters that identify the resource one wants to delete.
	*
	* @param {string} url End point, it must include name of the file to be deleted.
	* @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
	* @param {function} fail (optional) Callback function executed upon failure. (HTTP code != 200)
	*/    
    souschef.ajax.remove = function (url, success, fail) {    
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    success(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('DELETE', config.target, config.async);
        xhr.send(null);    	
    };
    
	/**
	* Returns the DOMDocument encapsulated by an XMLHttpRequest instance passed as argument.
	* @param {object} XMLHttpRequest instance whose status code is 200.
	* @return If current browser is IE then we return an DOMDocument created by IE's DOMParser utility, otherwise xhr.responseXML is returned.
	*/
	souschef.ajax.responseXML = function(xhr){
		
	}
	
    /**
	* This function could be an instance of souschef.ajax.post, please consider refactoring.
    * @param {string} url The url to make the ajax request to.
    * @param data -    Plain Javascript object to be encoded into JSON for transmission to the server.
    * @param {function} success Callback function executed upon successful completion. (HTTP code == 200)
    * @param{ function} fail Callback function executed upon failure. (HTTP code != 200)
    */
    souschef.ajax.jsonRpc = function (url, data, success, fail) {
        var xhr = new XMLHttpRequest();
		var config = typeof url == "string" ? souschef.ajax.config(url) : url;
        var encoded = JSON.stringify(data);
        var successWrapper = success;

        if(fail){
        	successWrapper = function(xhr){
        		var text = xhr.responseText;
        		if(text.indexOf('"error":null') > -1)
        			success(xhr);
        		else
        			fail(xhr);

        	}
        }
		
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                	successWrapper(xhr);
                } else if (fail) {
                    fail(xhr);
                }
            }
        };

        xhr.open('POST', config.target, config.async);
        xhr.setRequestHeader('Content-type', 'text/json');
        xhr.send(encoded);
    };
	
	return souschef.ajax;
})();