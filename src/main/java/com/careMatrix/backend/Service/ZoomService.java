package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Meeting;
import com.careMatrix.backend.Entity.MeetingRequest;
import com.careMatrix.backend.Repo.MeetingRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ZoomService {

    private final MeetingRepository meetingRepository;
    private final AtomicReference<String> accessToken = new AtomicReference<>();

    public ZoomService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void setAccessToken(String token) {
        accessToken.set(token);
    }

    public String createMeeting(MeetingRequest request) {
        if (accessToken.get() == null) {
            return "Error: Access token is missing. Please login via /zoom/login first.";
        }

        String url = "https://api.zoom.us/v2/users/me/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken.get());

        Map<String, Object> body = new HashMap<>();
        body.put("topic", request.getTopic());
        body.put("type", 2);
        body.put("start_time", request.getStartTime());
        body.put("duration", request.getDuration());
        body.put("timezone", "UTC");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        Map responseBody = response.getBody();

        // Save meeting to DB
        Meeting meeting = new Meeting();
        meeting.setMeetingId(String.valueOf(responseBody.get("id")));
        meeting.setTopic((String) responseBody.get("topic"));
        meeting.setStartTime((String) responseBody.get("start_time"));
        meeting.setDuration((Integer) responseBody.get("duration"));
        meeting.setJoinUrl((String) responseBody.get("join_url"));
        meeting.setStartUrl((String) responseBody.get("start_url"));

        meetingRepository.save(meeting);

        return "Meeting Scheduled: " + meeting.getJoinUrl();
    }
}
