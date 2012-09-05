log4j-hockeyapp
===============

A log4j appender for hockeyapp.net custom crash reports

Example log4j config file using this appender:
    <appender name="HOCKEY" class="com.newvisioninteractive.log4j.hockeyapp.HockeyAppender">
    	<param name="api_key" value="<your key here>" />
    	<param name="env" value="development" />
    	<param name="enabled" value="true" />
    	<filter class="org.apache.log4j.varia.LevelRangeFilter">
            <param name="LevelMin" value="ERROR"/>
            <param name="LevelMax" value="FATAL"/>
        </filter>
    </appender>

    <!--                           -->
    <!-- setup log4j's root logger -->
    <!--                           -->
    <root>
        <level value="all" />
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="STDERR"/>
        <appender-ref ref="HOCKEY"/>
    </root>
