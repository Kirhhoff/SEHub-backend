package com.scut.se.sehubbackend.dao.activity;

import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.member.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    /**
     * 查询一个部门发起过的所有<b>活动</b>申请表
     * @param department 要查询的部门
     * @return 这个部门发起过的所有<b>活动</b>申请表
     */
    @Query("select activity " +
            "from ActivityApplication activity join activity.checkInfo.initializer initializer " +
            "where initializer.department=:department")
    List<ActivityApplication> findAllByDepartment(@Param("department") Department department);

}
