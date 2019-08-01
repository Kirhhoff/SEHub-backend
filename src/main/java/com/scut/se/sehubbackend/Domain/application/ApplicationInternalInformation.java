package com.scut.se.sehubbackend.Domain.application;

import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Enumeration.ApplicationType;
import com.scut.se.sehubbackend.Enumeration.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

// @Data
// 使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去。
// @AllArgsConstructor
// 使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
// @NoArgsConstructor
// 使用后创建一个无参构造函数
// @Builder
// 关于Builder较为复杂一些，Builder的作用之一是为了解决在某个类有很多构造函数的情况，也省去写很多构造函数的麻烦，在设计模式中的思想是：用一个内部类去实例化一个对象，避免一个类出现过多构造函数
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 实现Serializable接口的目的是为类可持久化
// serialVersionUID适用于java序列化机制。简单来说，JAVA序列化的机制是通过判断类的serialVersionUID来验证的版本一致的。
public class ApplicationInternalInformation implements Serializable {

    private static final Long serialVersionUID=3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    ApprovalStatus status;

    @NotNull
    Date submission;

    @Column(name = "`check`")
    Date check;

    @NotNull
    Date lastModifiedTime;

    @NotNull
    @OneToOne
    UserAuthentication lastModifier;

    @ManyToOne
    @NotNull
    UserAuthentication sponsor; // 发起人

}
