package com.ssafy.user.groupMember.domain;

//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Invite extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int inviteId;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Column(nullable = false)
//    private LocalDateTime expire_date;
//
//    @Size(min = 0, max = 100)
//    @Column(nullable = false, length = 100)
//    private String identifier;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)
//    private Group group;
//
//
//    @Builder
//    public Invite(LocalDateTime expire_date, String identifier, Group group){
//        this.expire_date = expire_date;
//        this.identifier = identifier;
//        this.group = group;
//    }
//}
