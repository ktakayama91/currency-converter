package com.bcp.challenge.adapter.input.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
@RestController
@RequestMapping("${applcation.secure.test.url}")
public class SecuredController {

    @GetMapping
    public ResponseEntity reachSecureEndpoint() {
        return new ResponseEntity("If your are reading this you reached a secure endpoint", HttpStatus.OK);
    }
}
