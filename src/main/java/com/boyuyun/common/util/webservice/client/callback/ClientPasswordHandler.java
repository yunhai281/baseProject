package com.boyuyun.common.util.webservice.client.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class ClientPasswordHandler implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		if(pc.getUsage()==WSPasswordCallback.USERNAME_TOKEN){
			pc.setPassword("123456");
		}else if(pc.getUsage()==WSPasswordCallback.SIGNATURE){
			// set the password for our message.
			pc.setPassword("keypass");
		}
        System.out.println("Server Identifier=" + pc.getIdentifier());
        System.out.println("Server Password=" + pc.getPassword());
	}

}
