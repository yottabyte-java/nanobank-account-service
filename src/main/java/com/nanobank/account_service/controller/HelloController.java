package com.nanobank.account_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Smoke-test endpoint — confirms the service is up and routing correctly.
 *
 * <p>Not meant to stay in production long-term. Useful during local setup and
 * early CI to verify the container started cleanly before running heavier checks.
 */
@RestController
public class HelloController {

    /**
     * GET /hello
     *
     * <p>Returns a plain string. No auth, no DB hit — purely here to prove
     * the web layer is wired up and responding.
     *
     * @return a static greeting string
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Tebogo lets cook";
    }
}
