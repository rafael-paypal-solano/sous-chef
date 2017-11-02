(function(document) {
    'use strict';
    var souschef;
    
    if (window.souschef) {
        return window.souschef;
    }
    
    souschef = {};
    window.souschef = souschef;
    
    souschef.trim2Null = function(value) {

        var string = null;
        
        if(value == null)
            return value;
            
        switch(typeof value){
            case "string":
                string = value.trim();
                break;
                
            case "number":
            case "boolean":
            case "object":
                string = value.toString();
            default:
                break; 
                
        } 
        
        return string.length > 0 ? string : null;
    }
    
    souschef.isInteger = function (value) {
		return /^(\+|\-)?\d+$/.test(value);
	}

    souschef.coerceToBoolean = function(value) {
        var string;

        if(typeof value === 'number')
            return value != 0;
        else if(typeof value === 'boolean')
            return value;
        else if(typeof value === 'string') {
            string = value.toUpperCase();
            return string == 'TRUE' ? true : false;
        } else if( typeof value == "object" ) {
        	return value != null;
        }

        return false;
    }   
    
    return window.souschef;
})(document);