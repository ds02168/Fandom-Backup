package com.example.fandomTest.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "memID")
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memID; // 회원 pk

    @Column(unique = true, nullable = false)
    private String memEmail; // 회원 이메일

    @Column(nullable = false)
    private String memPassword; // 회원 비밀번호

    @Column(nullable = false)
    private String memNickName; // 회원 닉네임
}
