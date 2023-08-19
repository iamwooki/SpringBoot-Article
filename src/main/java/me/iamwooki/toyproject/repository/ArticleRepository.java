package me.iamwooki.toyproject.repository;

import me.iamwooki.toyproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> {  // <관리대상 entity, 관리대상 entity의 대표값 타입 (Long id)>
    // front end -> controller - JPA[Repository - Data / Entity] - DB

    @Override
    ArrayList<Article> findAll();
}
