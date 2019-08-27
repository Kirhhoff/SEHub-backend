package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.exception.CheckHasBeenOperatedException;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.CheckService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    private final CheckService checkService;

    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    @RequestMapping("/application/activity/check/{id}")
    public void checkActivity(@PathVariable Long id,
                                  @RequestParam("pass") boolean pass, @RequestParam("feedback") String feedback) throws CheckHasBeenOperatedException, InvalidIdException {
        checkService.activityCheck(id, pass, feedback);
    }

    @RequestMapping("/application/etiquette/check/{id}")
    public void checkEtiquette(@PathVariable Long id,
                                       @RequestParam("pass") boolean pass, @RequestParam("feedback") String feedback) throws CheckHasBeenOperatedException, InvalidIdException {
        checkService.etiquetteCheck(id, pass, feedback);
    }

    @RequestMapping("/application/host/check/{id}")
    public void checkHost(@PathVariable Long id,
                                   @RequestParam("pass") boolean pass, @RequestParam("feedback") String feedback) throws CheckHasBeenOperatedException, InvalidIdException {
        checkService.hostCheck(id, pass, feedback);
    }

    @RequestMapping("/application/poster/check/{id}")
    public void checkPoster(@PathVariable Long id,
                              @RequestParam("pass") boolean pass, @RequestParam("feedback") String feedback) throws CheckHasBeenOperatedException, InvalidIdException {
        checkService.posterCheck(id, pass, feedback);
    }

    @RequestMapping("/application/lecture-ticket/check/{id}")
    public void checkLectureTicket(@PathVariable Long id,
                                  @RequestParam("pass") boolean pass, @RequestParam("feedback") String feedback) throws CheckHasBeenOperatedException, InvalidIdException {
        checkService.lectureTicketCheck(id, pass, feedback);
    }
}
