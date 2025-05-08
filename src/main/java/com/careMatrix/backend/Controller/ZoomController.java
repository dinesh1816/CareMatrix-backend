package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.MeetingRequest;
import com.careMatrix.backend.Service.ZoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zoom")
public class ZoomController {


    @Autowired
    private ZoomService zoomService;

    @PostMapping("/schedule")
    public String scheduleMeeting(@RequestBody MeetingRequest request) {
        return zoomService.createMeeting(request);
    }
}
