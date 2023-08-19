package me.iamwooki.toyproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능, Repo와 커뮤니케이션하기 위한 값
@AllArgsConstructor // 모든 생성자 추가
@NoArgsConstructor // 디폴트 생성자 추가
@ToString
@Getter
public class Article {

    @Id //대표값을 지정 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동 생성
    private Long id;

    @Column
    private String title;
    @Column
    private String content;

    //null이 아닌 값의 경우에만 PATCH
    public void patch(Article article) {
        if(article.title!=null){
            this.title = article.title;
        }
        if(article.content!=null){
            this.content = article.content;
        }
    }
}
