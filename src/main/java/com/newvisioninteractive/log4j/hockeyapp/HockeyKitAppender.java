package com.newvisioninteractive.log4j.hockeyapp;

import java.io.IOException;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class HockeyKitAppender extends AppenderSkeleton {

    private HockeyKitCrashUploader hockeyKitUploader = new HockeyKitCrashUploader();

    private String apiKey;

    private String env;

    private boolean enabled;


    public HockeyKitAppender() {
	setThreshold( Level.ERROR );
    }


    public HockeyKitAppender( final String apiKey_ ) {
	setApi_key( apiKey_ );
    }


    public void close() {
    }


    public boolean requiresLayout() {
	return false;
    }


    @Override
    protected void append( LoggingEvent loggingEvent_ ) {
	if( !enabled )
	    return;

	if( isThrowable( loggingEvent_ ) ) {
	    try {
		hockeyKitUploader.upload( getCrash( getThrowableFromLoggingEvent( loggingEvent_ ) ) );
	    } catch( Exception e ) {

	    }
	}

    }


    public HockeyKitCustomCrash getCrash( final Throwable throwable_ ) throws IOException {
	return new HockeyKitCrashBuilder( apiKey,
					  env,
					  throwable_ ).newCrash();
    }


    private Boolean isThrowable( final LoggingEvent loggingEvent_ ) {
	return loggingEvent_.getThrowableInformation() != null;
    }


    private Throwable getThrowableFromLoggingEvent( final LoggingEvent loggingEvent_ ) {
	return loggingEvent_.getThrowableInformation().getThrowable();
    }


    public void setApi_key( final String apiKey_ ) {
	this.apiKey = apiKey_;
    }


    public void setEnabled( final boolean enabled_ ) {
	this.enabled = enabled_;
    }


    public void setEnv( final String env_ ) {
	this.env = env_;
    }

}
