package com.example.bigbrotherbe.domain.meetings.entity;

import com.example.bigbrotherbe.domain.BaseTimeEntity;
import com.example.bigbrotherbe.global.file.entity.File;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meetings extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetings_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_public")
    private boolean isPublic;

    // 이것도 매핑시켜야하나,,? 안하고 해 ㄱㄱㄱ
    private Long affiliationId;

    @OneToMany
    @JoinColumn(name = "meetings_id")
    private List<File> files;

    public void update(String title, String content, boolean isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
    }
}
