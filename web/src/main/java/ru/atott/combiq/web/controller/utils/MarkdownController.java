package ru.atott.combiq.web.controller.utils;

import org.apache.commons.lang3.StringUtils;
import org.pegdown.PegDownProcessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atott.combiq.web.controller.BaseController;

@Controller
public class MarkdownController extends BaseController {
    @RequestMapping(value = "/markdown/preview", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String preview(@RequestBody String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return markdown;
        }

        PegDownProcessor processor = new PegDownProcessor();
        return processor.markdownToHtml(markdown);
    }
}
