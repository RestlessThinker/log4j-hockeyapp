package com.newvisioninteractive.log4j.hockeyapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;

public class HockeyCrashBuilder {

    private String apiKey;
    private String environmentName;
    private Throwable throwable;

    private File log;
    private File description;
    private String userID;
    private String contact;
    private File attachment;


    public HockeyCrashBuilder( final String apiKey_,
	    final String environmentName_,
	    final Throwable throwable_ ) throws IOException {
	this.apiKey = apiKey_;
	this.throwable = throwable_;
	this.environmentName = environmentName_;
	createLog();
    }


    public HockeyCrashBuilder( final String apiKey_,
	    final String environmentName_,
	    final Throwable throwable_,
	    final String userID_,
	    final String contact_ ) throws IOException {
	this( apiKey_, environmentName_, throwable_ );
	this.userID = userID_;
	this.contact = contact_;
    }


    public HockeyCrashBuilder( final String apiKey_,
	    final String environmentName_,
	    final Throwable throwable_,
	    final String description_,
	    final File attachment_ ) throws IOException {

	this( apiKey_, environmentName_, throwable_ );
	this.attachment = attachment_;

	if( description_ != null ) {
	    createDescription( description_ );
	}
    }


    public HockeyCustomCrash newCrash() {
	return new HockeyCustomCrash( apiKey,
					 environmentName,
					 log,
					 description,
					 attachment,
					 userID,
					 contact );
    }


    protected void createLog() throws IOException {
	log = File.createTempFile( "exception", ".log" );
	FileWriter writer = new FileWriter( log );
	BufferedWriter out = new BufferedWriter( writer );
	Date date = new Date();
	DateFormat df = DateFormat.getDateTimeInstance();
	String hockey = "Package: com.mystyleapp.services";
	hockey += "\n";
	hockey += "Version: " + this.environmentName;
	hockey += "\n";
	hockey += "Android: n/a";
	hockey += "\n";
	hockey += "Manufacturer: n/a";
	hockey += "\n";
	hockey += "Model: n/a";
	hockey += "\n";
	hockey += "Date: " + df.format( date );
	hockey += "\n\n";
	hockey += ExceptionUtils.getStackTrace( this.throwable );
	out.write( hockey );
	out.close();
    }


    protected void createDescription( String descriptionString ) throws IOException {
	description = File.createTempFile( "description", ".log" );
	FileWriter writer = new FileWriter( description );
	BufferedWriter out = new BufferedWriter( writer );
	out.write( descriptionString );
	out.close();
    }
}
