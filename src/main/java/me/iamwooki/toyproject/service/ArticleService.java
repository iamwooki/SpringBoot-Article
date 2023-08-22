package me.iamwooki.toyproject.service;

import lombok.extern.slf4j.Slf4j;
import me.iamwooki.toyproject.dto.ArticleDto;
import me.iamwooki.toyproject.entity.Article;
import me.iamwooki.toyproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 서비스 선언 (서비스 객체를 스프링 부트에 생성)
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleDto dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) return null; //post 는 수정이 아니므로 PK인 ID를 넣을 경우 error
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleDto dto) {
        //1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id, article.toString());

        //2. 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        //2-1. 잘못된 요청 처리(예외처리, 대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            //잘못된 요청 응답(400)
            log.info("잘못된 요청 id: {}, article: {}",id, article.toString());
            return null;
        }

        //3. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if (target == null){
            return null;
        }
        //대상 삭제
        articleRepository.delete(target);
        return target;
    }
    @Transactional //해당 메소드를 트랜잭션으로 묶음
    public List<Article> createArticles(List<ArticleDto> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
//        동일 코드
//        List<Article> articleList = new ArrayList<>();
//        for (int i=0; i< dtos.size(); ++i){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }
        
        // entity 묶음을 db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
//        동일코드
//        for(int i=0;i<articleList.size(); ++i){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 저장된 상태에서 강제 에러 발생
//        articleRepository.findById(-1L).orElseThrow(
//                () -> new IllegalArgumentException("결제 실패")
//        );
        // 결과값 반환
        return articleList;
    }
}
