package com.careMatrix.backend.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class GoogleCalendarService {
    private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarService.class);
    private static final String APPLICATION_NAME = "CareMatrix Telemedicine";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(
        CalendarScopes.CALENDAR,
        CalendarScopes.CALENDAR_EVENTS
    );
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static Calendar calendarService;

    public Calendar getCalendarService() throws IOException, GeneralSecurityException {
        if (calendarService != null) return calendarService;

        try {
            // Load client secrets from classpath
            logger.info("Loading credentials from: {}", CREDENTIALS_FILE_PATH);
            InputStream in = new ClassPathResource(CREDENTIALS_FILE_PATH).getInputStream();
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }

            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            logger.info("Successfully loaded client secrets for client ID: {}", 
                clientSecrets.getDetails().getClientId());

            // Build flow and trigger user authorization request
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .setApprovalPrompt("force")
                    .build();

            // Use port 8888 for OAuth callback
            LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                    .setPort(8888)
                    .setCallbackPath("/oauth2callback")
                    .build();
            
            logger.info("Starting local server for OAuth flow on port 8888 with callback path /oauth2callback");
            logger.info("Requesting user authorization...");
            Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            logger.info("Successfully obtained user authorization");

            // Build the Calendar service
            calendarService = new Calendar.Builder(
                    new NetHttpTransport(),
                    JSON_FACTORY,
                    credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Test the connection
            try {
                logger.info("Testing Calendar API connection...");
                calendarService.calendarList().list().execute();
                logger.info("Successfully connected to Google Calendar API");
            } catch (Exception e) {
                logger.error("Failed to connect to Google Calendar API: {}", e.getMessage(), e);
                throw e;
            }

            return calendarService;
        } catch (Exception e) {
            logger.error("Error initializing Google Calendar service: {}", e.getMessage(), e);
            throw e;
        }
    }

    public String createMeetEvent(String summary, String description, LocalDate date, LocalTime startTime, int durationMinutes, String timeZone) throws Exception {
        try {
            Calendar service = getCalendarService();

            // Format date and time for Google Calendar API
            String startDateTime = date + "T" + startTime + ":00" + getTimeZoneOffset(timeZone);
            LocalTime endTime = startTime.plusMinutes(durationMinutes);
            String endDateTime = date + "T" + endTime + ":00" + getTimeZoneOffset(timeZone);

            logger.info("Creating calendar event: {} at {}", summary, startDateTime);

            // Create the event
            Event event = new Event()
                    .setSummary(summary)
                    .setDescription(description);

            // Set start time
            EventDateTime start = new EventDateTime()
                    .setDateTime(new com.google.api.client.util.DateTime(startDateTime))
                    .setTimeZone(timeZone);
            event.setStart(start);

            // Set end time
            EventDateTime end = new EventDateTime()
                    .setDateTime(new com.google.api.client.util.DateTime(endDateTime))
                    .setTimeZone(timeZone);
            event.setEnd(end);

            // Add Google Meet conference
            ConferenceData conferenceData = new ConferenceData()
                    .setCreateRequest(new CreateConferenceRequest()
                            .setRequestId("carematrix-" + UUID.randomUUID().toString())
                            .setConferenceSolutionKey(new ConferenceSolutionKey()
                                    .setType("hangoutsMeet")));
            event.setConferenceData(conferenceData);

            // Insert the event with conference data
            logger.info("Inserting calendar event with Meet conference...");
            event = service.events().insert("primary", event)
                    .setConferenceDataVersion(1)
                    .execute();

            String meetLink = event.getHangoutLink();
            logger.info("Successfully created calendar event with Meet link: {}", meetLink);
            return meetLink;
        } catch (Exception e) {
            logger.error("Error creating calendar event: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String getTimeZoneOffset(String timeZone) {
        // For now, hardcode to America/Chicago timezone
        return "-05:00";
    }
} 