package com.regexBuddy.regexhelper.service;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.regexBuddy.regexhelper.dtos.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GptService {
    final static String OPEN_AI_API_KEY = System.getenv("OPEN_AI_API_KEY");


    private ChatRequest requestSetter(UserRequest userRequest){
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");
        chatRequest.setTemperature(0.2);
        String prompt = "Can you explain what this regular expression does :"+ userRequest.getRegularExpression();
        Message msg = new Message();
        msg.setRole("user");
        msg.setContent(prompt);
        List<Message> msgls = new ArrayList<>();
        msgls.add(msg);
        chatRequest.setMessages(msgls);
        return chatRequest;
    }

    public UserResponse getGptResponse(UserRequest userRequest) throws URISyntaxException, IOException {
        //creating the request
        ChatRequest req = requestSetter(userRequest);
        //converting pojo to json and then pass entity
        Gson gson = new Gson();

        String reqObj = gson.toJson(req,ChatRequest.class);
        System.out.println(reqObj);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost();
        post.setURI(new URI("https://api.openai.com/v1/chat/completions"));
        post.setHeader(HttpHeaders.CONTENT_TYPE,"application/json");
        post.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+ OPEN_AI_API_KEY);
        //setting the string as string entity
        post.setEntity(new StringEntity(reqObj, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String resp =  EntityUtils.toString(httpEntity);
        System.out.println("Have got the resp" + resp);
        ChatCompletion  chatResponse= gson.fromJson(resp, ChatCompletion.class);
        httpClient.close();
        return gptRequestTrimmer(chatResponse);
    }
    public UserResponse gptRequestTrimmer(ChatCompletion response){
        UserResponse resp = new UserResponse();
        String content = response.getChoices()[0].getMessage().getContent();
        String updatedContent = content.replaceAll("\n", "");
        resp.setResponse(updatedContent);
        return resp;
    }
}
