package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.PosterApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.PosterApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.dto.PosterApplicationDTO;
import com.scut.se.sehubbackend.email.SendEmailCreatedEvent;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.Etiquette;
import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.Poster;

@Service
public class PosterApplicationService {

    private final PosterApplicationRepository posterApplicationRepository;
    private final CheckInfoUtil checkInfoUtil;
    @Autowired
    private ApplicationContext context;

    public PosterApplicationService(PosterApplicationRepository posterApplicationRepository, CheckInfoUtil checkInfoUtil, EmailService emailService) {
        this.posterApplicationRepository = posterApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
    }

    /**
     * 参考{@link EtiquetteApplicationService#create(EtiquetteApplicationDTO)}
     */
    public void create(PosterApplicationDTO posterApplicationDTO){
        PosterApplication posterApplication=toDO(posterApplicationDTO,null);
        posterApplicationRepository.saveAndFlush(posterApplication);
        context.publishEvent(new SendEmailCreatedEvent(this, Poster.toString()));
    }

    /**
     * 参考{@link EtiquetteApplicationService#toDO(EtiquetteApplicationDTO, ActivityApplication)}
     */
    public PosterApplication toDO(PosterApplicationDTO posterApplicationDTO,
                       ActivityApplication parentActivityApplication) {
        return PosterApplication.builder()
                .activityBasicInfo(posterApplicationDTO.getActivityBasicInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .deadline(posterApplicationDTO.getDeadline())
                .propagandaTextRequirement(posterApplicationDTO.getPropagandaTextRequirement())
                .posterSize(posterApplicationDTO.getPosterSize())
                .activityThisBelongsTo(parentActivityApplication)
                .build();
    }

    @PreAuthorize("hasRole('Propaganda') and hasAuthority('Poster')")
    public List<PosterApplication> findAll() { return posterApplicationRepository.findAll(); }
    @PreAuthorize("hasRole('Propaganda') and hasAuthority('Poster')")
    public PosterApplication findById(Long id) throws InvalidIdException { return posterApplicationRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(PosterApplication posterApplication){posterApplicationRepository.saveAndFlush(posterApplication);}
}
