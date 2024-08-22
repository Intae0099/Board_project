package com.board.board.Post;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


// MappedSuperclass : JPA Entity 클래스들이 TimeEntity를 상속할 경우 이 클래스의 변수인 createdDate와 modifiedDate도 Column으로 인식하게한다.
// EntityListeners(AuditingEntityListener.class) : TimeEntity 클래스에 Auditing 기능을 포함시킨다.
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class PostTimeEntity {
    //엔티티 생성시 당시 시간 저장
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    //엔티티 값 변경시 당시 시간 저장
    @LastModifiedDate
    private LocalDateTime updatedDate;
}
