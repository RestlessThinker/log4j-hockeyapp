package com.newvisioninteractive.log4j.hockeyapp;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class HockeyAppenderTest {

    private static final String ERROR_MESSAGE = "error message";


    @Test
    public void testNewAppender() throws IOException {
	
	HockeyAppender appender = new HockeyAppender();
	
	HockeyCustomCrash crash = appender.getCrash( getStubException( ERROR_MESSAGE ) );
	assertThat( crash, is( notNullValue() ) );
    }

    
    private Exception getStubException( final String errorMessage ) {
	final String foo = null;
	try {
	    foo.toString();
	} catch( Exception e ) {
	   return new RuntimeException( errorMessage, e );
	}
	return null;
    }
}
