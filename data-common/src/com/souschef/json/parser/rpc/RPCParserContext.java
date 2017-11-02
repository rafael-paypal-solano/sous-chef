package com.souschef.json.parser.rpc;
import java.util.Stack;

import com.souschef.json.parser.*;

/**
 *<p>JSONParser context for JSON-RPC.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p> 
 *
 */
public class RPCParserContext extends JSONParserContext{
	
	/**
 *<p>Used internally for creating delegates.</p>
	 * @author root
	 *
	 */
    static interface PushMethodDelegate
    {
    	/**
    	 * 
    	 * @param uistructureelement .-
    	 */
        public abstract void push(JSONStructuredElement uistructureelement);
    }
	
    /**
     *<p>parsing stack's root element</p>
     */
	JSONStructuredElement root;
	
	/**
	 *<p>parsing stack's top element.</p>
	 */
	JSONStructuredElement top;
	
	/**
	 *<p>parsing stack</p>
	 */
	Stack<JSONStructuredElement> stack = new Stack<JSONStructuredElement>();	
	
	/**
 *	<p></p>
	 */
	PushMethodDelegate furtherPushsDelegates = new PushMethodDelegate() {

		/**
		 * {@inheritDoc}
		 */
        public void push(JSONStructuredElement element)
        {
            stack.push(element);
            top = element;
        }            
    };
    
    /**
     * 
     */
    PushMethodDelegate initialPushDelegate = new PushMethodDelegate() {

    	/**
    	 * {@inheritDoc}
    	 */
        public void push(JSONStructuredElement element)
        {
            root = element;
            stack.push(element);
            pushDelegate = furtherPushsDelegates;
            top = element;
        }
    };
    
    /**
     * 
     */
    PushMethodDelegate pushDelegate = initialPushDelegate;
    
    /**
     *<p>Returns the structured element that's currently being buit.</p>
     * @return .-
     */
    public JSONStructuredElement getTop()
    {
        return top;
    }

    /**
     *<p>Returns the structured element that's the root of the hierarchy being parsed.</p>
     * @return
     */
    public JSONStructuredElement getRoot()
    {
        return root;
    }	

    /**
     *<p>Pops off the last element in the internal stack.</p>
     * @return The last element in the internal stack.
     */
    public JSONStructuredElement pop()
    {
    	JSONStructuredElement top = (JSONStructuredElement)stack.pop();
        if(stack.size() > 0)
            this.top = (JSONStructuredElement)stack.lastElement();
        else
            this.top = root;
        return top;
    }    
    
    /**
     * <code>Push an element to the internal stack.</code>
     * @param element Element to be pushed.
     */
    public void push(JSONStructuredElement element)
    {
        pushDelegate.push(element);
    }

	@Override
	public void setRoot(Object root) {
				
	}
    
}
