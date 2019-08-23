package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.ActivityApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ActivityApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityApplicationService {

    final ActivityApplicationRepository activityApplicationRepository;
    final DTOUtil dtoUtil;
    final ContextHelper<Member> contextHelper;
    final EtiquetteApplicationService etiquetteApplicationService;
    final HostApplicationService hostApplicationService;
    final LectureTicketApplicationService lectureTicketApplicationService;
    final PosterApplicationService posterApplicationService;
    final CheckInfoUtil checkInfoUtil;

    public ActivityApplicationService(ActivityApplicationRepository activityApplicationRepository, DTOUtil dtoUtil, ContextHelper<Member> contextHelper, EtiquetteApplicationService etiquetteApplicationService, HostApplicationService hostApplicationService, LectureTicketApplicationService lectureTicketApplicationService, PosterApplicationService posterApplicationService, CheckInfoUtil checkInfoUtil) {
        this.activityApplicationRepository = activityApplicationRepository;
        this.dtoUtil = dtoUtil;
        this.contextHelper = contextHelper;
        this.etiquetteApplicationService = etiquetteApplicationService;
        this.hostApplicationService = hostApplicationService;
        this.lectureTicketApplicationService = lectureTicketApplicationService;
        this.posterApplicationService = posterApplicationService;
        this.checkInfoUtil = checkInfoUtil;
    }

    //
//    @Autowired
//    private ActivityApplicationRepository activityApplicationRepository;
//
//    @Autowired
//    private EtiquetteApplicationService etiquetteApplicationService;
//
//    @Autowired
//    private HostApplicationService hostApplicationService;
//
//    @Autowired
//    private LectureTicketApplicationService lectureTicketApplicationService;
//
//    @Autowired
//    private PosterApplicationService posterApplicationService;
//
//    // DTO的目的是没有必要返回给前端礼仪申请等各种辅助信息，辅助信息可选展示即点击按钮再展示
//

    /**
     * 获取当前用户所在部门的所有申请表
     * @return 当前用户所在部门的所有申请表
     */
    public List<ActivityApplicationDTO> getAllActivityApplicationOfCurrentDepartment() {
        Member currentMember= contextHelper.getCurrentPrincipal();
        Department departmentOfCurrentMember=currentMember.getDepartment();
        List<ActivityApplication> activityApplications=activityApplicationRepository.findAllByDepartment(departmentOfCurrentMember);
        return dtoUtil.toDTO(activityApplications);
    }

    /**
     * 根据请求的数据创建一张
     * @param activityDTO 请求中包含的DTO请求表
     */
    public void create(ActivityApplicationDTO activityDTO){
        ActivityApplication activityApplication=ActivityApplication.builder()
                .activityBasicInfo(activityDTO.getActivityBasicInfo())
                .activitySupplementaryInfo(activityDTO.getActivitySupplementaryInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .build();
        if (activityDTO.getEtiquetteApplication()!=null)
            activityApplication.setEtiquetteApplication(
                    etiquetteApplicationService.toDO(
                            activityDTO.getEtiquetteApplication(),
                            activityApplication
                    )
            );
        if (activityDTO.getHostApplication()!=null)
            activityApplication.setHostApplication(
                    hostApplicationService.toDO(
                            activityDTO.getHostApplication(),
                            activityApplication
                    )
            );
        if (activityDTO.getLectureTicketApplication()!=null)
            activityApplication.setLectureTicketApplication(
                    lectureTicketApplicationService.toDO(
                            activityDTO.getLectureTicketApplication(),
                            activityApplication
                    )
            );
        if (activityDTO.getPosterApplication()!=null)
            activityApplication.setPosterApplication(
                    posterApplicationService.toDO(
                            activityDTO.getPosterApplication(),
                            activityApplication
                    )
            );
        activityApplicationRepository.saveAndFlush(activityApplication);
    }

    public List<ActivityApplication> findAll(){return activityApplicationRepository.findAll();}
    public ActivityApplication findById(Long id) throws InvalidIdException { return activityApplicationRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(ActivityApplication activityApplication) { activityApplicationRepository.saveAndFlush(activityApplication); }

}
