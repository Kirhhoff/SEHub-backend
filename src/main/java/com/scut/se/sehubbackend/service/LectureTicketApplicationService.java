package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.LectureTicketApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.LectureTicketApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.dto.LectureTicketApplicationDTO;
import com.scut.se.sehubbackend.email.SendEmailCreatedEvent;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.Etiquette;
import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.LectureTicket;

@Service
public class LectureTicketApplicationService {

    private final LectureTicketApplicationRepository lectureTicketApplicationRepository;
    private final CheckInfoUtil checkInfoUtil;
    @Autowired
    private ApplicationContext context;

    public LectureTicketApplicationService(LectureTicketApplicationRepository lectureTicketApplicationRepository, CheckInfoUtil checkInfoUtil, EmailService emailService) {
        this.lectureTicketApplicationRepository = lectureTicketApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
    }

    /**
     * 参考{@link EtiquetteApplicationService#create(EtiquetteApplicationDTO)}
     */
    public void create(LectureTicketApplicationDTO lectureTicketApplicationDTO) {
        LectureTicketApplication lectureTicketApplication=toDO(lectureTicketApplicationDTO,null);
        lectureTicketApplicationRepository.saveAndFlush(lectureTicketApplication);
        context.publishEvent(new SendEmailCreatedEvent(this, LectureTicket.toString()));
    }

    /**
     * 参考{@link EtiquetteApplicationService#toDO(EtiquetteApplicationDTO, ActivityApplication)}
     */
    public LectureTicketApplication toDO(LectureTicketApplicationDTO lectureTicketApplicationDTO,
                       ActivityApplication parentActivityApplication){
        return LectureTicketApplication.builder()
                .activityBasicInfo(lectureTicketApplicationDTO.getActivityBasicInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .numOfTicket(lectureTicketApplicationDTO.getNumOfTicket())
                .ticketScore(lectureTicketApplicationDTO.getTicketScore())
                .ticketType(lectureTicketApplicationDTO.getTicketType())
                .activityThisBelongsTo(parentActivityApplication)
                .build();
    }

    @PreAuthorize("hasRole('Research') and hasAuthority('LectureTicket')")
    public List<LectureTicketApplication> findAll() {
        return lectureTicketApplicationRepository.findAll();
    }
    @PreAuthorize("hasRole('Research') and hasAuthority('LectureTicket')")
    public LectureTicketApplication findById(Long id) throws InvalidIdException { return lectureTicketApplicationRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(LectureTicketApplication lectureTicketApplication){lectureTicketApplicationRepository.saveAndFlush(lectureTicketApplication);}
}
