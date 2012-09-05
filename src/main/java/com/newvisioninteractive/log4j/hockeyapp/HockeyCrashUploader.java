package com.newvisioninteractive.log4j.hockeyapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class HockeyCrashUploader {

    private final static String PARAM_LOG = "log";
    private final static String PARAM_DESCRIPTION = "description";
    private final static String PARAM_ATTACHMENT0 = "attachment0";
    private final static String PARAM_USERID = "userID";
    private final static String PARAM_CONTACT = "contact";

    private final static String MIME_TEXT_PLAIN = "text/plain";


    public void upload( HockeyCustomCrash crash_ ) throws UnsupportedEncodingException,
	    ClientProtocolException,
	    IOException {

	if( crash_.getLog() == null )
	    return;

	HttpClient client = new DefaultHttpClient();
	MultipartEntity reqEntity = new MultipartEntity();

	FileBody bin = new FileBody( crash_.getLog(), MIME_TEXT_PLAIN );
	reqEntity.addPart( PARAM_LOG, bin );

	if( crash_.getDescription() != null ) {
	    reqEntity.addPart( PARAM_DESCRIPTION,
			       new FileBody( crash_.getDescription(),
					     MIME_TEXT_PLAIN ) );
	}

	if( crash_.getAttachment0() != null ) {
	    reqEntity.addPart( PARAM_ATTACHMENT0,
			       new FileBody( crash_.getAttachment0(),
					     MIME_TEXT_PLAIN ) );
	}

	if( crash_.getUserID() != null ) {
	    reqEntity.addPart( PARAM_USERID,
			       new StringBody( crash_.getUserID() ) );
	}

	if( crash_.getContact() != null ) {
	    reqEntity.addPart( PARAM_CONTACT,
			       new StringBody( crash_.getContact() ) );
	}

	URI uri = null;
	try {
	    uri = new URI( "https://rink.hockeyapp.net/api/2/apps/" +
		    crash_.getApiKey() + "/crashes/upload" );
	} catch( URISyntaxException e ) {

	}

	HttpPost post = new HttpPost( uri );
	post.setEntity( reqEntity );

	HttpResponse response = client.execute( post );

	// Process the response.
	HttpEntity responseEntity = response.getEntity();
	StatusLine responseStatus = response.getStatusLine();
	int statusCode = responseStatus.getStatusCode();

	if( responseEntity == null || statusCode != 201 ) {
	    // something failed
	} else {
	    // hooray finished
	    crash_.getLog().delete();
	    crash_.getDescription().delete();
	    crash_.getAttachment0().delete();
	}

    }
}
