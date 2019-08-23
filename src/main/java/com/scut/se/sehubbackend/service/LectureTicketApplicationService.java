package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.LectureTicketApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.LectureTicketApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.dto.LectureTicketApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureTicketApplicationService {

    final LectureTicketApplicationRepository lectureTicketApplicationRepository;
    final CheckInfoUtil checkInfoUtil;

    public LectureTicketApplicationService(LectureTicketApplicationRepository lectureTicketApplicationRepository, CheckInfoUtil checkInfoUtil) {
        this.lectureTicketApplicationRepository = lectureTicketApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
    }

    /**
     * 参考{@link EtiquetteApplicationService#create(EtiquetteApplicationDTO)}
     */
    public void create(LectureTicketApplicationDTO lectureTicketApplicationDTO){
        LectureTicketApplication lectureTicketApplication=toDO(lectureTicketApplicationDTO,null);
        lectureTicketApplicationRepository.saveAndFlush(lectureTicketApplication);
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
