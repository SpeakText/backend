package com.speaktext.backend.member.domain;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true, length = 64)
    private String id;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 200)
    private String email;

}
