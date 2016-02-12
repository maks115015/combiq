package ru.atott.combiq.web.controller.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.atott.combiq.service.file.FileDescriptor;
import ru.atott.combiq.service.file.FileService;
import ru.atott.combiq.service.file.Location;
import ru.atott.combiq.service.site.MarkdownService;
import ru.atott.combiq.web.bean.UploadFileBean;
import ru.atott.combiq.web.controller.BaseController;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

@Controller
public class MarkdownController extends BaseController {

    @Autowired
    private FileService fileService;

    @Autowired
    private MarkdownService markdownService;

    @RequestMapping(value = "/markdown/preview", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String preview(@RequestBody(required = false) String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return markdown;
        }

        return markdownService.toHtml(markdown);
    }

    @RequestMapping(value="/markdown/image/upload", method=RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Location location = Location.allocateLocalLocation(filename);
        try (OutputStream outputStream = fileService.getOutputStream(location)) {
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        }
        return new UploadFileBean(location);
    }

    @RequestMapping("/markdown/image")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadUserAvatarImage(
            @RequestParam("loc") String locationString) {
        Location location = Location.parse(locationString);
        FileDescriptor fileDescriptor = fileService.getFileDescriptor(location);

        return ResponseEntity.ok()
                .contentLength(fileDescriptor.getSize())
                .contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(location.getFilename())))
                .body(new InputStreamResource(fileDescriptor.getInputStream()));
    }
}
