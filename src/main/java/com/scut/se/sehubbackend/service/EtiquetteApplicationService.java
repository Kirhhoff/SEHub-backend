package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.EtiquetteApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.EtiquetteApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.Etiquette;

@Service
public class EtiquetteApplicationService {

    private final EtiquetteApplicationRepository etiquetteApplicationRepository;
    private final CheckInfoUtil checkInfoUtil;
    private final EmailService emailService;

    public EtiquetteApplicationService(EtiquetteApplicationRepository etiquetteApplicationRepository, CheckInfoUtil checkInfoUtil, EmailService emailService) {
        this.etiquetteApplicationRepository = etiquetteApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
        this.emailService = emailService;
    }

    /**
     * <p>单独创建一张礼仪申请表，没有所属的活动申请表，同时发出一张通知</p>
     * @param etiquetteApplicationDTO 请求中的DTO数据
     */
    public void create(EtiquetteApplicationDTO etiquetteApplicationDTO) {

        //转化得到DO形式的表
        EtiquetteApplication etiquetteApplication=toDO(etiquetteApplicationDTO,null);

        //持久化
        etiquetteApplicationRepository.saveAndFlush(etiquetteApplication);

        //发出Email通知
        emailService.sendEmailByAuthority(Etiquette.toString(),"新表来了，看看吧");
    }

    /**
     * <p>将请求的数据转化为礼仪申请表</p>
     * @param etiquetteApplicationDTO 请求提供的数据
     * @param parentActivityApplication 礼仪申请表所隶属的活动申请表，单独创建一张礼仪申请表时为空
     * @return DO形式的表，尚未持久化
     */
    public EtiquetteApplication toDO(EtiquetteApplicationDTO etiquetteApplicationDTO,
                       ActivityApplication parentActivityApplication){
        return EtiquetteApplication.builder()
                .activityBasicInfo(etiquetteApplicationDTO.getActivityBasicInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .numOfEtiquette(etiquetteApplicationDTO.getNumOfEtiquette())
                .rehearsalTime(etiquetteApplicationDTO.getRehearsalTime())
                .rehearsalSite(etiquetteApplicationDTO.getRehearsalSite())
                .descOfJob(etiquetteApplicationDTO.getDescOfJob())
                .activityThisBelongsTo(parentActivityApplication)
                .build();
    }

    @PreAuthorize("hasRole('Relation') and hasAuthority('Etiquette')")
    public List<EtiquetteApplication> findAll() {
        return etiquetteApplicationRepository.findAll();
    }
    @PreAuthorize("hasRole('Relation') and hasAuthority('Etiquette')")
    public EtiquetteApplication findById(Long id) throws InvalidIdException { return etiquetteApplicationRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(EtiquetteApplication etiquetteApplication){etiquetteApplicationRepository.saveAndFlush(etiquetteApplication);}

}
