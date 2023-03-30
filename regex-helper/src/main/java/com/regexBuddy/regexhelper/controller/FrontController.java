package com.regexBuddy.regexhelper.controller;


import com.google.gson.JsonObject;
import com.regexBuddy.regexhelper.dtos.ChatCompletion;
import com.regexBuddy.regexhelper.dtos.UserRequest;
import com.regexBuddy.regexhelper.dtos.UserResponse;
import com.regexBuddy.regexhelper.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController()
@RequestMapping("/regex-buddy")
public class FrontController {


    @Autowired
    GptService gptService;
    @GetMapping(value = "/test")
    public String test(){
        return "Process is Deployed";
    }

    @PostMapping(value = "/knowRegex")
    public UserResponse getRegexResponse(@RequestBody  UserRequest userRequest) throws URISyntaxException, IOException {
        return gptService.getGptResponse(userRequest);
    }



}
