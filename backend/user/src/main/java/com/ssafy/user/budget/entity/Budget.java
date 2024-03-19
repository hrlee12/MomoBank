package com.ssafy.user.budget.entity;

import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupInfo.entity.GroupInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private int budgetId;

    @Column(length = 255, name = "name")
    private String name;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "total_money")
    private long totalMoney;

    @ManyToOne
    @JoinColumn(name = "group_info_id")
    private GroupInfo groupInfo;
}
