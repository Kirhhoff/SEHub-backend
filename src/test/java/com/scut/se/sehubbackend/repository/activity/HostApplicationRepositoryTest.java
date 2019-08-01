package com.scut.se.sehubbackend.repository.activity;

import com.scut.se.sehubbackend.Domain.activityN.ActivityMainInfo;
import com.scut.se.sehubbackend.Domain.activityN.HostApplication;
import com.scut.se.sehubbackend.Repository.activityN.HostApplicationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HostApplicationRepositoryTest {

    @Autowired
    HostApplicationRepository hostApplicationRepository;

    @Test
    public void saveTest() {
        ActivityMainInfo activityMainInfo = new ActivityMainInfo();
        activityMainInfo.setActivityId(new Long(0));
        activityMainInfo.setName("黑框框");
        activityMainInfo.setLocation("B8");
        activityMainInfo.setStartTime(new Date());
        activityMainInfo.setEndTime(new Date());
        activityMainInfo.setDescription("大佬激情互殴");

        HostApplication hostApplication = new HostApplication();
        hostApplication.setActivityMainInfo(activityMainInfo);
        hostApplication.setId(activityMainInfo.getActivityId());
        hostApplication.setDescOfJob("为大佬们喊666");
        hostApplication.setNumOfHost(2);

        HostApplication result = hostApplicationRepository.save(hostApplication);
        Assert.assertNotNull(result);
    }
}
