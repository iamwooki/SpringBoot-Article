package me.iamwooki.toyproject.service;

import me.iamwooki.toyproject.dto.CommentDto;
import me.iamwooki.toyproject.entity.Article;
import me.iamwooki.toyproject.entity.Comment;
import me.iamwooki.toyproject.repository.ArticleRepository;
import me.iamwooki.toyproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
//        // 조회 : 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // 변환 Entity -> Dto
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for(int i= 0 ; i<comments.size();++i){
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//
//        // 반환
//        return dtos;

        //stream 문법, 추후 스터디
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패, 대상 게시글이 없습니다."));

        //댓글엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }
}
