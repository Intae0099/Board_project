package com.board.board.Post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
//null값을 자동으로 insert 쿼리에 포함시키지 않게 해준다
@DynamicInsert
@Table(name="post", indexes = @Index(name = "search_title_index", columnList = "title"))
public class Post extends PostTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    
//      이건  default 제약사항을 넣어주는것, null값을 디폴트 값으로 바꿔주진 않는다.
    @ColumnDefault("0")
    private Integer views;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;


}
