package com.example.NewJeans.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cmtID")
@Builder

@Entity
public class Comment {

    @Id
//    @GeneratedValue(generator = "system-uuid") // 중복없는 랜덤문자로 pk 생성전략
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmtID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardID")
    private Board boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memID")
    private Member memId;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String cmtContent; //댓글 내용

    @CreationTimestamp
    private LocalDateTime cmtDate;

    //댓글 수정
    public void update(String comment){
        this.cmtContent=comment;
    }


}