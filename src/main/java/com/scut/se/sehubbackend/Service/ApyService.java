package com.scut.se.sehubbackend.Service;

import com.scut.se.sehubbackend.Domain.Notice;
import com.scut.se.sehubbackend.Domain.application.ApplicationForm;
import com.scut.se.sehubbackend.Domain.application.ApplicationInternalInformation;
import com.scut.se.sehubbackend.Domain.application.ApplicationJoinInformation;
import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Enumeration.*;
import com.scut.se.sehubbackend.Others.ReqApplicationForm;
import com.scut.se.sehubbackend.Repository.NoticeRepository;
import com.scut.se.sehubbackend.Repository.application.ApplicationFormRepository;
import com.scut.se.sehubbackend.Repository.application.ApplicationInternalInformationRepository;
import com.scut.se.sehubbackend.Repository.application.ApplicationJoinInformationRepository;
import com.scut.se.sehubbackend.Repository.user.OwnerOnly;
import com.scut.se.sehubbackend.Repository.user.UserAuthorityRecordRepository;
import com.scut.se.sehubbackend.Security.Authorization.interfaces.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

// 提交申请表的服务
// 保存申请信息和保存通知用户
@Service
public class ApyService {

    @Autowired private UserAuthorityRecordRepository authorityRecordRepository;
    @Autowired private ApplicationInternalInformationRepository internalInformationRepository;
    @Autowired private ApplicationJoinInformationRepository joinInformationRepository;
    @Autowired private ApplicationFormRepository formRepository;
    @Autowired private NoticeRepository noticeRepository;

    // getAcceptors使用的
    private Map<ApplicationType, GrantedAuthority> applicationType2GrantedAuthority;

    @Autowired
    public ApyService(AuthorityMapper authorityMapper){
        applicationType2GrantedAuthority=new HashMap<>();
        // 每个部门对应一种动态权限
        applicationType2GrantedAuthority.put(ApplicationType.Etiquette,authorityMapper.mapDynamic(DepartmentNameEnum.Relation,null));
        applicationType2GrantedAuthority.put(ApplicationType.Event,authorityMapper.mapDynamic(DepartmentNameEnum.StandingCommittee,null));
        applicationType2GrantedAuthority.put(ApplicationType.Host,authorityMapper.mapDynamic(DepartmentNameEnum.Literary,null));
        applicationType2GrantedAuthority.put(ApplicationType.Material,authorityMapper.mapDynamic(DepartmentNameEnum.Secretary,null));
        applicationType2GrantedAuthority.put(ApplicationType.NewMedia,authorityMapper.mapDynamic(DepartmentNameEnum.Media, DynamicDetail.NewMediaApplication));
        applicationType2GrantedAuthority.put(ApplicationType.Publicity,authorityMapper.mapDynamic(DepartmentNameEnum.Propaganda,null));
        applicationType2GrantedAuthority.put(ApplicationType.Reporter,authorityMapper.mapDynamic(DepartmentNameEnum.Media,DynamicDetail.ReporterApplication));
    }

    // 获取所有有权限的接收者
    public List<UserAuthentication> getAcceptors(ApplicationType applicationType){
        List<OwnerOnly> owners=authorityRecordRepository
                .findAllByAuthority(
                        applicationType2GrantedAuthority.get(applicationType).getAuthority() //GrantedAuthority.getAuthority()
                );
        List<UserAuthentication> result=new ArrayList<>();
        for (OwnerOnly ownerOnly:owners)
            result.add(ownerOnly.getOwner());
        return result;
    }

    // 提交申请
    // ReqApplicationForm 申请表
    public SeStatus SubmitApplication(UserAuthentication applicant, ReqApplicationForm form){

        // ApplicationInternalInformation 申请提交信息，如审核状态，修改时间，发起人等
        ApplicationInternalInformation internalInformation = ApplicationInternalInformation.builder()
                .status(ApprovalStatus.Submit)
                .lastModifiedTime(new Date())
                .sponsor(applicant)
                .submission(new Date())
                .lastModifier(applicant)
                .build();
        internalInformationRepository.save(internalInformation);

        // ApplicationJoinInformation 申请信息，如活动申请，物资申请，礼仪队申请等
        ApplicationJoinInformation joinInformation;
        // 根据得到的申请表中的申请类型信息，构建不同的申请信息
        switch (form.getType()){
            case Etiquette: {
                joinInformation = ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .eventTime(form.getActtime())
                        .amount(form.getNumber())
                        .task(form.getWork())
                        .remarks(form.getOthers())
                        .build();
                break;
            }
            case Event: {
                joinInformation = ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .eventTime(form.getActtime())
                        .eventBackground(form.getActback())
                        .eventAim(form.getActaim())
                        .eventIntroduction(form.getActintro())
                        .eventHost(form.getHostunit())
                        .eventOrganization(form.getOrganizer())
                        .eventTarget(form.getTarget())
                        .build();
                break;
            }
            case Host:{
                joinInformation = ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .eventTime(form.getActtime())
                        .rehearsalTime(form.getRehtime())
                        .amount(form.getNumber())
                        .remarks(form.getOthers())
                        .build();
                break;
            }
            case NewMedia:{
                joinInformation = ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .eventTime(form.getActtime())
                        .deadline(form.getDeadline())
                        .task(form.getWork())
                        .remarks(form.getOthers())
                        .build();
                break;
            }
            case Reporter:{
                joinInformation= ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .eventTime(form.getActtime())
                        .deadline(form.getDeadline())
                        .task(form.getWork())
                        .remarks(form.getOthers())
                        .build();
                break;
            }
            case Publicity:{
                joinInformation= ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .eventTime(form.getActtime())
                        .deadline(form.getDeadline())
                        .eventIntroduction(form.getActintro())
                        .materialList(form.getNeeds())
                        .content(form.getContent())
                        .remarks(form.getOthers())
                        .build();
                break;
            }
            case Material:{
                joinInformation= ApplicationJoinInformation.builder()
                        .eventName(form.getActname())
                        .eventSite(form.getActaddr())
                        .lendTime(form.getLendtime())
                        .returnTime(form.getBacktime())
                        .materialList(form.getNeeds())
                        .remarks(form.getOthers())
                        .build();
                break;
            }
            case Ticket://to do 讲座票申请表
            default:
                return SeStatus.InvalidApyType;
        }

        // 保存表单
        joinInformation.setType(form.getType());
        joinInformationRepository.save(joinInformation);

        // 之前的ReqApplicationForm是用户提交的申请表，ApplicationForm是最终的申请表信息
        // 包括申请的提交信息和申请信息
        ApplicationForm applicationForm = ApplicationForm.builder()
                .internalInformation(internalInformation).joinInformation(joinInformation).build();
        formRepository.save(applicationForm);


        // 添加notices
        ArrayList<Notice> notices = new ArrayList<>();
        for(UserAuthentication accptor: getAcceptors(form.getType())){
            Notice notice = Notice.builder()
                    .type(NoticeType.ApplicationSubmit)
                    .sponsor(applicant)
                    .acceptor(accptor)
                    .form(applicationForm)
                    .initiateTime(new Date())
                    .build();
            notices.add(notice);
        }
        noticeRepository.saveAll(notices);

        return SeStatus.Success;
    }
}
