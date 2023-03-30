package com.regexBuddy.regexhelper.controller;


import com.google.gson.JsonObject;
import com.regexBuddy.regexhelper.dtos.ChatCompletion;
import com.regexBuddy.regexhelper.dtos.UserRequest;
import com.regexBuddy.regexhelper.dtos.UserResponse;
import com.regexBuddy.regexhelper.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController(value = "/regex-buddy")
public class FrontController {


    @Autowired
    GptService gptService;
    @GetMapping(value = "/test")
    public String test(){
        return "Process is Deployed";
    }

    @PostMapping(value = "/getResp")
    public UserResponse getRegexResponse(@RequestBody  UserRequest userRequest) throws URISyntaxException, IOException {
        return gptService.getGptResponse(userRequest);
    }



}
