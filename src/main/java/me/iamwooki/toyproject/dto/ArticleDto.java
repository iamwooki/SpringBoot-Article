package me.iamwooki.toyproject.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import me.iamwooki.toyproject.entity.Article;


@AllArgsConstructor
@ToString
public class ArticleDto { // DTO : input form에서 entity로 전달하기 위해, 페이지와 소통하는 값
    private Long id;
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
