package com.scut.se.sehubbackend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 存储部门成员的信息包括权限
public class UserHistory implements Serializable,Comparable {

    private static final Long serialVersionUID=5L;

    @Id
    // 此注解是类注解，作用是json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响。
    // 需要把一个List<HistoryOrderBean>转换成json格式的数据传递给前台。
    //  但实体类中基本属性字段的值都存储在快照属性字段中。
    // 此时我可以在业务层中做处理，把快照属性字段的值赋给实体类中对应的基本属性字段。
    // 最后，我希望返回的json数据中不包含这两个快照字段，
    // 那么在实体类中快照属性上加注解@JsonIgnore，那么最后返回的json数据，将不会包含goodsInfo和extendsInfo两个属性值。
    @JsonIgnore
    // 默认情况下，将打印所有非静态字段。如果要跳过某些字段，可以使用@ToString.Exclude标注这些字段。
    @ToString.Exclude //字面意思
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    Long year;

    @Enumerated(EnumType.STRING)
    DepartmentNameEnum departmentNameEnum;

    @NotNull
    @Enumerated(EnumType.STRING)
    PositionEnum positionEnum;

    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    UserAuthentication userAuthentication;

    @Override
    public int compareTo(Object o) {
        return year.compareTo(((UserHistory)o).getYear());
    }
}
