package com.ssafy.api.controller;

import com.ssafy.api.request.OpenviduPostReq;
import com.ssafy.api.request.OpenviduRemoveUserReq;
import com.ssafy.api.response.OpenviduPostRes;
import com.ssafy.common.model.response.BaseResponseBody;
import io.openvidu.java.client.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OpenviduController {
    // OpenVidu object as entrypoint of the SDK
    private OpenVidu openVidu;

    // Collection to pair session names and OpenVidu Session objects
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    // Collection to pair session names and tokens (the inner Map pairs tokens and
    // role associated)
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    // URL where our OpenVidu server is listening
    private String OPENVIDU_URL;
    // Secret shared with our OpenVidu server
    private String SECRET;

    public OpenviduController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

//    @RequestMapping(value = "/session", method = RequestMethod.POST)
//    public String joinSession(@RequestParam(name = "data") String clientData,
//                              @RequestParam(name = "session-name") String sessionName, Model model, HttpSession httpSession) {
//
//        try {
//            checkUserLogged(httpSession);
//        } catch (Exception e) {
//            return "index";
//        }
//        System.out.println("Getting sessionId and token | {sessionName}={" + sessionName + "}");
//
//        // Role associated to this user
//        OpenViduRole role = OpenViduRole.PUBLISHER;
//
//        // Optional data to be passed to other users when this user connects to the
//        // video-call. In this case, a JSON with the value we stored in the HttpSession
//        // object on login
//        String serverData = "{\"serverData\": \"" + httpSession.getAttribute("loggedUser") + "\"}";
//
//        // Build connectionProperties object with the serverData and the role
//        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
//                .role(role).data(serverData).build();
//
//        if (this.mapSessions.get(sessionName) != null) {
//            // Session already exists
//            System.out.println("Existing session " + sessionName);
//            try {
//
//                // Generate a new token with the recently created connectionProperties
//                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();
//
//                // Update our collection storing the new token
//                this.mapSessionNamesTokens.get(sessionName).put(token, role);
//
//                // Add all the needed attributes to the template
//                model.addAttribute("sessionName", sessionName);
//                model.addAttribute("token", token);
//                model.addAttribute("nickName", clientData);
//                model.addAttribute("userName", httpSession.getAttribute("loggedUser"));
//
//                // Return session.html template
//                return "session";
//
//            } catch (Exception e) {
//                // If error just return dashboard.html template
//                model.addAttribute("username", httpSession.getAttribute("loggedUser"));
//                return "dashboard";
//            }
//        } else {
//            // New session
//            System.out.println("New session " + sessionName);
//            try {
//
//                // Create a new OpenVidu Session
//                Session session = this.openVidu.createSession();
//                // Generate a new token with the recently created connectionProperties
//                String token = session.createConnection(connectionProperties).getToken();
//
//                // Store the session and the token in our collections
//                this.mapSessions.put(sessionName, session);
//                this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
//                this.mapSessionNamesTokens.get(sessionName).put(token, role);
//
//                // Add all the needed attributes to the template
//                model.addAttribute("sessionName", sessionName);
//                model.addAttribute("token", token);
//                model.addAttribute("nickName", clientData);
//                model.addAttribute("userName", httpSession.getAttribute("loggedUser"));
//
//                // Return session.html template
//                return "session";
//
//            } catch (Exception e) {
//                // If error just return dashboard.html template
//                model.addAttribute("username", httpSession.getAttribute("loggedUser"));
//                return "dashboard";
//            }
//        }
//    }

    @ApiOperation(value = "session 토큰 가져오기", notes = "세션 id의 정보에 따라 토큰을 가져온다.")//body{tokenName:'aaa'}
    @PostMapping("/get-token")
    public ResponseEntity<OpenviduPostRes> getToken(@RequestBody @ApiParam(value="token가져오기", required = true) OpenviduPostReq openviduPostReq) {

        System.out.println("Getting sessionId and token | {sessionName}=" +openviduPostReq.getSessionName());
        String sessionName=openviduPostReq.getSessionName();

        // The video-call to connect ("TUTORIAL")

        // Role associated to this user
        OpenViduRole role = OpenViduRole.PUBLISHER;

        // Build connectionProperties object with the serverData and the role
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                .role(role).data("").build();

        //세션이 있으면 -> 참가자로서 입장
        if (this.mapSessions.get(sessionName) != null) {
            // Session already exists
            System.out.println("Existing session " + sessionName);
            try {

                // Generate a new token with the recently created connectionProperties
                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

                // Update our collection storing the new token
                this.mapSessionNamesTokens.get(sessionName).put(token, role);

                // Prepare the response with the token
                // Return the response to the client
                return ResponseEntity.ok(OpenviduPostRes.of(200, "Success",token));

            } catch (OpenViduJavaClientException e1) {
                // If internal error generate an error message and return it to client
                return ResponseEntity.status(401).body(OpenviduPostRes.of(401, "Invalid", null));
            } catch (OpenViduHttpException e2) {
                if (404 == e2.getStatus()) {
                    // Invalid sessionId (user left unexpectedly). Session object is not valid
                    // anymore. Clean collections and continue as new session
                    this.mapSessions.remove(sessionName);
                    this.mapSessionNamesTokens.remove(sessionName);
                }
            }
        }

        // New session
        try {
            // Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            System.out.println("New session " + sessionName);
            // Generate a new token with the recently created connectionProperties
            String token = session.createConnection(connectionProperties).getToken();

            // Store the session and the token in our collections
            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionName).put(token, role);

            // Prepare the response with the sessionId and the token
            System.out.println(token);
            // Return the response to the client
            return ResponseEntity.ok(OpenviduPostRes.of(200, "Success",token ));

        } catch (Exception e) {
            // If error generate an error message and return it to client
            return ResponseEntity.status(401).body(OpenviduPostRes.of(401, "Invalid", null));
        }
    }



    @ApiOperation(value = "user 지우기", notes = "유처가 세션을 나간다. 마지막 사람까지 나갈경우 세션은 없어진다.")//body{tokenName:'aaa'}
    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public ResponseEntity<? extends BaseResponseBody> removeUser(@RequestBody @ApiParam(value="user지우기", required = true) OpenviduRemoveUserReq openviduRemoveUserReq) throws Exception {

        System.out.println("Removing user | {sessionName, token}=" + openviduRemoveUserReq.toString());

        // Retrieve the params from BODY
        String sessionName = openviduRemoveUserReq.getSessionName();
        String token = openviduRemoveUserReq.getToken();

        // If the session exists
        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

            // If the token exists
            if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
                // User left the session
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    // Last user left: session must be removed
                    this.mapSessions.remove(sessionName);
                }
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
            } else {
                // The TOKEN wasn't valid
                System.out.println("Problems in the app server: the TOKEN wasn't valid");
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Problems in the app server: the TOKEN wasn't valid"));
            }

        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Problems in the app server: the SESSION does not exist"));
        }
    }


    @ApiOperation(value = "session 닫기", notes = "해당하는 sessionName의 세션을 지운다.")//body{tokenName:'aaa'}
    @RequestMapping(value = "/close-session/{sessionName}", method = RequestMethod.DELETE)
    public ResponseEntity<? extends BaseResponseBody> closeSession(@PathVariable  String sessionName) throws Exception {

        System.out.println("Closing session | {sessionName}="+sessionName );

        // Retrieve the param from BODY
        String session = sessionName;

        // If the session exists
        if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
            Session s = this.mapSessions.get(session);
            s.close();
            this.mapSessions.remove(session);
            this.mapSessionNamesTokens.remove(session);
            //this.sessionRecordings.remove(s.getSessionId());
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Problems in the app server: the SESSION does not exist"));
        }

    }
}
