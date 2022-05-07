package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitController {
    @RequestMapping
    public String initRedirect() {
        return "redirect:/api/v1/hello-world";
    }

    @RequestMapping(value = { "/docs/v1" })
    public String docs() {
        return "redirect:/api/v1/docs/swagger-ui/";
    }
}
