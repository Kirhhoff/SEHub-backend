package com.scut.se.sehubbackend.unit;


import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.DynamicDetail;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.security.authorization.HashAuthorityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class HashAuthorityMapperTest {

    HashAuthorityMapper mapper=new HashAuthorityMapper();

    @Test
    //测试字符串映射方法
    public void testMapString(){
        assertEquals(new SimpleGrantedAuthority("Minister_Research"),mapper.map("Minister_Research"));//用部长做测试
        assertEquals(new SimpleGrantedAuthority("Staff_Research"), mapper.map("Staff_Research"));//用部员做测试
        assertEquals(new SimpleGrantedAuthority("StandingCommittee"), mapper.map("StandingCommittee"));//用常委做测试
        assertEquals(new SimpleGrantedAuthority("Dynamic_Research"), mapper.map("Dynamic_Research"));//用动态权限做测试
        assertEquals(null,mapper.map("randomTest"));//尝试获取不存在的权限
    }

    @Test
    //测试职位和部门映射方法
    public void testMapPositionAndDepartment(){
        assertEquals(new SimpleGrantedAuthority("Minister_Research"),mapper.map(PositionEnum.Minister, DepartmentNameEnum.Research));//用部长做测试
        assertEquals(new SimpleGrantedAuthority("Staff_Research"),mapper.map(PositionEnum.Staff, DepartmentNameEnum.Research));//用部员做测试
        assertEquals(new SimpleGrantedAuthority("StandingCommittee"),mapper.map(PositionEnum.StandingCommittee,null));//用常委做测试
        assertEquals(null,mapper.map(null,null));//测试不存在的权限
    }

    @Test
    //测试动态权限映射
    public void testMapDynamic(){
        assertEquals(new SimpleGrantedAuthority("Dynamic_Research"),mapper.mapDynamic(DepartmentNameEnum.Research,null));//动态部门权限
        assertEquals(null,mapper.mapDynamic(DepartmentNameEnum.Research, DynamicDetail.General));//试图生成不存在的权限时得到null
        assertEquals(null,mapper.mapDynamic(null,null));//试图传入一个不存在的权限
    }
}
