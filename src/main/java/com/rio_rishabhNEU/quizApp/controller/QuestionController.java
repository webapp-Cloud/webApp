package com.rio_rishabhNEU.quizApp.controller;


import com.rio_rishabhNEU.quizApp.QuestionModel;
import com.rio_rishabhNEU.quizApp.service.QuestionService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/healthz")
    public ResponseEntity<Void>healthCheck(@RequestBody(required = false)String payload)  {

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("No-Cache");
try {
    if (payload != null && !payload.isEmpty()) {
        return new ResponseEntity<>(headers,HttpStatusCode.valueOf(400));
    }
    boolean healthInfo = questionService.isDatabaseConnected();

    if (healthInfo) {
        return new ResponseEntity<>(headers,HttpStatusCode.valueOf(200));
    } else {
        return new ResponseEntity<>(headers,HttpStatusCode.valueOf(503));

    }
}catch (Exception e){
    return  new ResponseEntity<>(headers,HttpStatusCode.valueOf(503));

}
    }
}
