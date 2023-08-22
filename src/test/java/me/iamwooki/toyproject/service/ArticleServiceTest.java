package me.iamwooki.toyproject.service;

import me.iamwooki.toyproject.dto.ArticleDto;
import me.iamwooki.toyproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스팅
class ArticleServiceTest {

    @Autowired ArticleService articleService;
    @Test
    void index() {
        //예상
        Article a = new Article(1L, "가가가가","1111");
        Article b = new Article(2L, "나나나나","2222");
        Article c = new Article(3L, "다다다다","3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));

        //실제
        List<Article> articles = articleService.index();

        //비교
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void show_successCase() {
        //예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가","1111");

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_failCase() { //존재하지 않는 경우
        //예상
        Long id = -1L;
        Article expected = null;  // orElse(null) 대응
        
        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected, article); // null은 toString화 할 수 없음
    }

    @Test
    @Transactional
    void create_successCase() {//title와 content만 있는 dto
        //예상
        String title ="라라라라";
        String content="4444";
        ArticleDto dto = new ArticleDto(null, title, content);
        Article expected = new Article(4L, title, content);

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_failCase() { //id가 포함된 dto
        //예상
        String title ="라라라라";
        String content="4444";
        ArticleDto dto = new ArticleDto(4L, title, content);
        Article expected = null;

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_successCase() { //존재하는 ID와 title, content가 있는 dto 입력
        //예상

        //실제

        //비교
    }

    @Test
    @Transactional
    void update_successCase2() { //존재하는 ID와 title만 있는 dto 입력
        //예상

        //실제

        //비교
    }

    @Test
    @Transactional
    void update_failCase() { //존재하지 않는 id의 dto입력
        //예상

        //실제

        //비교
    }

    @Test
    @Transactional
    void update_failCase2() { //실패 id만 있는 dto
        //예상

        //실제

        //비교
    }

    @Test
    @Transactional
    void delete_successCase() { //존재하는 id
        //예상

        //실제

        //비교
    }

    @Test
    @Transactional
    void delete_failCase() { //존재하지 않는 id
        //예상

        //실제

        //비교
    }
}