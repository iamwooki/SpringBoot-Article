package me.iamwooki.toyproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.iamwooki.toyproject.dto.CommentDto;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne //해당 댓글 엔티티 여러개가, 하나의 Article에 연관
    @JoinColumn(name= "article_id") //해당 컬럼명으로 생성(FK)
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생
        if(dto.getId()!=null)
            throw new IllegalArgumentException("댓글 생성 실패, 댓글의 id가 없어야 합니다.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패, 게시글의 id가 잘못되었습니다.");

        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }
}
