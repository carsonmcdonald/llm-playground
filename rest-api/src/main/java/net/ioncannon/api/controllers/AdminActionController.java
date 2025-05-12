package net.ioncannon.api.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminActionController {

    private static final Log log = LogFactory.getLog(AdminActionController.class);

    @PostMapping("/admin/add-document")
    public void addDocument(@RequestBody String documentLocation) {
        log.error("Adding document " + documentLocation);
        // TODO send document to pipeline for processing
    }

}
