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

    private HockeyCustomCrash crash;


    public void setCrash( HockeyCustomCrash crash ) {
	this.crash = crash;
    }


    public HockeyCustomCrash getCrash() {
	return this.crash;
    }


    public void upload() throws UnsupportedEncodingException,
	    ClientProtocolException,
	    IOException {

	if( crash.getLog() == null )
	    return;

	HttpClient client = new DefaultHttpClient();
	MultipartEntity reqEntity = new MultipartEntity();

	FileBody bin = new FileBody( crash.getLog(), MIME_TEXT_PLAIN );
	reqEntity.addPart( PARAM_LOG, bin );

	if( crash.getDescription() != null ) {
	    reqEntity.addPart( PARAM_DESCRIPTION,
			       new FileBody( crash.getDescription(),
					     MIME_TEXT_PLAIN ) );
	}

	if( crash.getAttachment0() != null ) {
	    reqEntity.addPart( PARAM_ATTACHMENT0,
			       new FileBody( crash.getAttachment0(),
					     MIME_TEXT_PLAIN ) );
	}

	if( crash.getUserID() != null ) {
	    reqEntity.addPart( PARAM_USERID, new StringBody( crash.getUserID() ) );
	}

	if( crash.getContact() != null ) {
	    reqEntity.addPart( PARAM_CONTACT,
			       new StringBody( crash.getContact() ) );
	}

	URI uri = null;
	try {
	    uri = new URI( "https://rink.hockeyapp.net/api/2/apps/" +
		    crash.getApiKey() + "/crashes/upload" );
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
	    System.out.println( "error sending crashlog to hockeyapp with status code: " +
		    statusCode );
	    if( crash.getLog() != null ) {
		if( crash.getLog().exists() ) {
		    crash.getLog().delete();
		}
	    }
	    if( crash.getDescription() != null ) {
		if( crash.getDescription().exists() ) {
		    crash.getDescription().delete();
		}
	    }
	    if( crash.getAttachment0() != null ) {
		if( crash.getAttachment0().exists() ) {
		    crash.getDescription().delete();
		}
	    }

	} else {
	    // hooray finished
	    crash.getLog().delete();
	    if( crash.getDescription() != null ) {
		if( crash.getDescription().exists() ) {
		    crash.getDescription().delete();
		}
	    }
	    if( crash.getAttachment0() != null ) {
		if( crash.getAttachment0().exists() ) {
		    crash.getDescription().delete();
		}
	    }

	}

	Thread.currentThread().interrupt();

    }
}
