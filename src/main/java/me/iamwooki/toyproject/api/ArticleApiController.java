package me.iamwooki.toyproject.api;

import lombok.extern.slf4j.Slf4j;
import me.iamwooki.toyproject.dto.ArticleDto;
import me.iamwooki.toyproject.entity.Article;
import me.iamwooki.toyproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController // RestAPI 용 컨트롤러, 데이터(JSON)을 반환
public class ArticleApiController {
    @Autowired // DI, 생성 객체를 가져와 연결.
    private ArticleService articleService;

    //GET
    //전체 리스트 조회
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    //단건 조회
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //POST
    //데이터 추가
    @PostMapping("/api/articles/")
    public ResponseEntity<Article> create(@RequestBody ArticleDto dto){
        Article created = articleService.create(dto);
        return (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //PATCH
    //데이터 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleDto dto){
        Article updated = articleService.update(id, dto);
        return (updated != null)?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELTE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //트랜잭션 -> 실패 시-> 롤백
    @PostMapping("api/transactionTest")
    public ResponseEntity<List<Article>> trasactionTest(@RequestBody List<ArticleDto> dto){
        List<Article> createdList= articleService.createArticles(dto);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
