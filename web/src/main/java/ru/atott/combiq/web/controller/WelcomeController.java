package ru.atott.combiq.web.controller;

import org.parboiled.common.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Controller
public class WelcomeController extends BaseController {

    @Value("${web.sitemap.location}")
    private String sitemapLocation;

    @RequestMapping(value = {"/", "index.do"}, method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "sitemap.xml", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> sitemap() throws IOException {
        File file = new File(sitemapLocation);

        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(FileUtils.readAllText(file, Charset.forName("utf-8")), HttpStatus.OK);
    }
}