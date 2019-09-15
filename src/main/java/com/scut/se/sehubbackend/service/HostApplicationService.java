package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.HostApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.HostApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.dto.HostApplicationDTO;
import com.scut.se.sehubbackend.email.SendEmailCreatedEvent;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.Host;

@Service
public class HostApplicationService {

    private final HostApplicationRepository hostApplicationRepository;
    private final CheckInfoUtil checkInfoUtil;
    @Autowired
    private ApplicationContext context;

    public HostApplicationService(HostApplicationRepository hostApplicationRepository, CheckInfoUtil checkInfoUtil, EmailService emailService) {
        this.hostApplicationRepository = hostApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
    }


    /**
     * 参考{@link EtiquetteApplicationService#create(EtiquetteApplicationDTO)}
     */
    public void create(HostApplicationDTO hostApplicationDTO)  {
        HostApplication hostApplication= toDO(hostApplicationDTO,null);
        hostApplicationRepository.saveAndFlush(hostApplication);
        context.publishEvent(new SendEmailCreatedEvent(this, Host.toString()));
    }

    /**
     * 参考{@link EtiquetteApplicationService#toDO(EtiquetteApplicationDTO, ActivityApplication)}
     */
    public HostApplication toDO(HostApplicationDTO hostApplicationDTO,
                       ActivityApplication parentActivityApplication){
        return HostApplication.builder()
                .activityBasicInfo(hostApplicationDTO.getActivityBasicInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .numOfHost(hostApplicationDTO.getNumOfHost())
                .descOfJob(hostApplicationDTO.getDescOfJob())
                .activityThisBelongsTo(parentActivityApplication)
                .build();
    }
    @PreAuthorize("hasRole('Secretary') and hasAuthority('Host')")
    public List<HostApplication> findAll() {
        return hostApplicationRepository.findAll();
    }
    @PreAuthorize("hasRole('Secretary') and hasAuthority('Host')")
    public HostApplication findById(Long id) throws InvalidIdException { return hostApplicationRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(HostApplication hostApplication){hostApplicationRepository.saveAndFlush(hostApplication);}
}
