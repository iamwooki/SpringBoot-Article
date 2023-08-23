package me.iamwooki.toyproject.controller;

import lombok.extern.slf4j.Slf4j;
import me.iamwooki.toyproject.dto.ArticleDto;
import me.iamwooki.toyproject.entity.Article;
import me.iamwooki.toyproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // simple loging facade for java
public class ArticleController {
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new"; // templeate 기준
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleDto form){
        //System.out.println(form.toString()); -> 로깅으로 대체!, 서버에서 일어나는 모든 것들을
        log.info(form.toString());

        //ref : https://youtu.be/ZGgf_1OXcAY
        // 1. Convert Dto to Entity, 인풋 데이터를 Dto로 감싼다음 해당 내용을 Entity로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        // 2. Save Entity of Repo in DB, 변환된 entity를 Repo에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        // 3. 결과 페이지
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id - " +id);
        // front end <- controller(Model) <-  Entity <- Repository <- Data(id) <- DB
        // 1. id로 데이터 가져옴, Repo에 저장된 값을 id 키로 가져와서 Entity화 시킴
        Article articleEntity = articleRepository.findById(id).orElse(null); //
        // orElse(null) => 데이터가 없는 경우도 처리해야함
        //또는 Optional<Article> articleEntity = articleRepository.findById(id);

        // 2. 가져온 데이터를 model에 등록, 가져온 Entity를 DTO(Model)형식으로 바꿔서, 페이지에 뿌려줄 수 있도록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        log.info("id - " +id);
        // 1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); //
        // orElse(null) => 데이터가 없는 경우도 처리해야함
        //또는 Optional<Article> articleEntity = articleRepository.findById(id);

        // 2. 가져온 데이터를 model에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지 설정
        return "articles/edit";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 Article을 가져 온다  findAll() -> List<Entity>
        List<Article> articleEntityList = articleRepository.findAll();
        //Iterable<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 article 묶음을 View로 전달 한다.
        model.addAttribute("articleList",articleEntityList);
        // 3. 뷰 페이지를 설정한다.
        return "articles/index"; //articles/index.mustache
    }

    @PostMapping("/articles/update")
    public String update(ArticleDto form){
        log.info(form.toString());
        // 1. Dto를 Entity로 변환
        Article articleEntity = form.toEntity();
        // 2. Entity -> DB 저장
        // 2-1. DB의 기존 데이터를 가져온다.
        Optional<Article> target = articleRepository.findById(articleEntity.getId());

        // 2-2 기존 데이터 값을 수정 / 갱신한다.
        if(target!=null){
            articleRepository.save(articleEntity); // entity 가 DB로 갱신
        }


        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes rttr){
        // 1. 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상을 삭제 한다.
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
            /*
            컨트롤러에서 jsp로 값을 보내야할 때 RedirectAttributes와 Model의 차이가 있다.
            RedirectAttributes는 redirect로 리턴하는 코드가 있어야한다.
            redirect없이 jsp 페이지를 거쳐갈때는 model로 값을 보내주면 된다.
             */
        }
        // 3. 결과 페이지 리다이렉트
        return "redirect:/articles";
    }
}

