package com.scut.se.sehubbackend.repository.activity;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityApplicationRepositoryTest {
//
//    @Autowired
//    private ActivityApplicationRepository activityApplicationRepository;
//
//    @Test
//    public void saveTest() {
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        ActivityMainInfo activityMainInfo = new ActivityMainInfo();
//        activityMainInfo.setActivityId(new Long(0));
//        activityMainInfo.setName("黑框框");
//        activityMainInfo.setLocation("B8");
//        activityMainInfo.setStartTime(new Date());
//        activityMainInfo.setEndTime(new Date());
//        activityMainInfo.setDescription("大佬激情互殴");
//
//        ActivitySupplementaryInfo activitySupplementaryInfo = new ActivitySupplementaryInfo();
//        activitySupplementaryInfo.setActivityMainInfo(activityMainInfo);
//        activitySupplementaryInfo.setId(activityMainInfo.getActivityId());
//        activitySupplementaryInfo.setBackground("大家都闲得慌");
//        activitySupplementaryInfo.setObjective("寻找巨佬");
//        activitySupplementaryInfo.setOrganizer("学术部");
//        activitySupplementaryInfo.setExpectedNumOfParticipants(new Integer(5));
//        activitySupplementaryInfo.setNote("给多点经费行不行");
//
//        EtiquetteApplication etiquetteApplication = new EtiquetteApplication();
//        etiquetteApplication.setActivityMainInfo(activityMainInfo);
//        etiquetteApplication.setId(activityMainInfo.getActivityId());
//        etiquetteApplication.setDescOfJob("女装大佬为巨佬跳舞");
//        etiquetteApplication.setNumOfEtiquette(3);
//        etiquetteApplication.setRehearsalTime(new Date());
//
//        HostApplication hostApplication = new HostApplication();
//        hostApplication.setActivityMainInfo(activityMainInfo);
//        hostApplication.setId(activityMainInfo.getActivityId());
//        hostApplication.setDescOfJob("为大佬们喊666");
//        hostApplication.setNumOfHost(2);
//
//        LectureTicketApplication lectureTicketApplication = new LectureTicketApplication();
//        lectureTicketApplication.setActivityMainInfo(activityMainInfo);
//        lectureTicketApplication.setId(activityMainInfo.getActivityId());
//        lectureTicketApplication.setNumOfTicket(50);
//
//        PosterApplication posterApplication = new PosterApplication();
//        posterApplication.setActivityMainInfo(activityMainInfo);
//        posterApplication.setId(activityMainInfo.getActivityId());
//        posterApplication.setDeadline(new Date());
//        posterApplication.setPosterSize(PosterSizeEnum.K8.getCode());
//        posterApplication.setPropagandaTextRequirement("大力宣传");
//
////        Member initializerAndModifier = new Member();
////
////        Department department= new Department();
////        department.setName("软件学院");
////        department.setNumOfMember(30);
////        department.setMemberList(null);
//
////        initializerAndModifier.setStudentNumber(new Long(2017));
////        initializerAndModifier.setDepartment(department);
////        initializerAndModifier.setName("小明");
////        initializerAndModifier.setPhoneNumber("10000");
////        initializerAndModifier.setPosition(PositionEnum.MINISTER.getCode());
////        initializerAndModifier.setServedRecords(null);
////        List<ActivityApplication> activityApplicationList = new ArrayList<ActivityApplication>();
////        activityApplicationList.add(activityApplication);
////        List<ActivityApplication> activityModificationList = new ArrayList<ActivityApplication>();
////        activityModificationList.add(activityApplication);
////        initializerAndModifier.setActivityApplicationList(activityApplicationList);
////        initializerAndModifier.setActivityModificationList(activityModificationList);
//
//        activityApplication.setActivityMainInfo(activityMainInfo);
//        activityApplication.setId(activityMainInfo.getActivityId());
//        activityApplication.setActivitySupplementaryInfo(activitySupplementaryInfo);
//        activityApplication.setEtiquetteApplication(etiquetteApplication);
//        activityApplication.setHostApplication(hostApplication);
//        activityApplication.setLectureTicketApplication(lectureTicketApplication);
//        activityApplication.setPosterApplication(posterApplication);
//        activityApplication.setCheckStatus(CheckStatusEnum.WAIT.getCode());
//        activityApplication.setSubmissionDate(new Date());
//        activityApplication.setCheckDate(new Date());
//        activityApplication.setInitializer(null);
//        activityApplication.setLastModifier(null);
//
//        ActivityApplication result = activityApplicationRepository.save(activityApplication);
//        Assert.assertNotNull(result);
//
//    }
}
