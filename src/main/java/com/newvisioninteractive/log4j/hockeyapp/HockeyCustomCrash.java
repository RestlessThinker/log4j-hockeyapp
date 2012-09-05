package com.newvisioninteractive.log4j.hockeyapp;

import java.io.File;

/**
 * As seen in {@link http
 * ://support.hockeyapp.net/kb/api/api-post-custom-crashes}
 * 
 * @author lpenaflor
 * 
 */
public class HockeyCustomCrash {
    
    private String apiKey;
    private String environmentName;

    /**
     * required, file with the crash log
     */
    private File log;

    /**
     * optional, file with optional information, e.g. excerpts from the system
     * log
     */
    private File description;

    /**
     * optional, file with optional binary data (3 attachments allowed)
     */
    private File attachment0;

    /**
     * optional, string with a user or device ID, limited to 255 chars
     */
    private String userID;

    /**
     * optional, string with contact information, limited to 255 chars
     */
    private String contact;


    public HockeyCustomCrash( final String apiKey_,
	    final String environment_,
	    final File log_,
	    final File description_,
	    final File attachment0_,
	    final String userID_,
	    final String contact_ ) {

	setApiKey( apiKey_ );
	setEnvironmentName( environment_ );
	setLog( log_ );
	setDescription( description_ );
	setAttachment0( attachment0_ );
	setUserID( userID_ );
	setContact( contact_ );
    }
    
    
    public String getApiKey() {
	return apiKey;
    }
    
    
    public void setApiKey( String key ) {
	this.apiKey = key;
    }
    
    
    public String getEnvironmentName() {
	return environmentName;
    }
    
    
    public void setEnvironmentName( String name ) {
	this.environmentName = name;
    }


    public File getLog() {
	return log;
    }


    public void setLog( File log ) {
	this.log = log;
    }


    public File getDescription() {
	return description;
    }


    public void setDescription( File description ) {
	this.description = description;
    }


    public File getAttachment0() {
	return attachment0;
    }


    public void setAttachment0( File attachment0 ) {
	this.attachment0 = attachment0;
    }


    public String getUserID() {
	return userID;
    }


    public void setUserID( String userID ) {
	this.userID = userID;
    }


    public String getContact() {
	return contact;
    }


    public void setContact( String contact ) {
	this.contact = contact;
    }
}
