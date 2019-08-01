package com.scut.se.sehubbackend.repository.activity;

import com.scut.se.sehubbackend.Domain.activityN.ActivityMainInfo;
import com.scut.se.sehubbackend.Domain.activityN.LectureTicketApplication;
import com.scut.se.sehubbackend.Repository.activityN.LectureTicketApplicationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LectureTicketApplicationRepositoryTest {

    @Autowired
    LectureTicketApplicationRepository lectureTicketApplicationRepository;

    @Test
    public void saveTest() {
        ActivityMainInfo activityMainInfo = new ActivityMainInfo();
        activityMainInfo.setActivityId(new Long(0));
        activityMainInfo.setName("黑框框");
        activityMainInfo.setLocation("B8");
        activityMainInfo.setStartTime(new Date());
        activityMainInfo.setEndTime(new Date());
        activityMainInfo.setDescription("大佬激情互殴");

        LectureTicketApplication lectureTicketApplication = new LectureTicketApplication();
        lectureTicketApplication.setActivityMainInfo(activityMainInfo);
        lectureTicketApplication.setId(activityMainInfo.getActivityId());
        lectureTicketApplication.setNumOfTicket(50);

        LectureTicketApplication result = lectureTicketApplicationRepository.save(lectureTicketApplication);
        Assert.assertNotNull(result);
    }
}
