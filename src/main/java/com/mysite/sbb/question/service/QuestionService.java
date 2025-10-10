package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dto.QuestionFormDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  // 페이지 기능 추가
  public Page<Question> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<Sort.Order>(); // 여러 정렬 기준을 위한 리스트
    sorts.add(Sort.Order.desc("created")); // 생성일 기준 내림차순 정렬
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 페이지당 10개씩 조회 + 정렬 기능
    Page<Question> questionList = questionRepository.findAll(pageable);
    return questionList;
  }

  public Question getQuestion(Long id) {
    Optional<Question> question = questionRepository.findById(id);
    if(question.isPresent()) {
      return question.get();
    } else {
      throw new IllegalArgumentException("id에 해당하는 질문이 없습니다 : " + id);
    }

    // 간략한 표현
//    Question question = questionRepository.findById(id)
//        .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + id));
//    return question;
  }

  public void create(QuestionFormDto questionFormDto) {
    Question question = Question.builder()
        .subject(questionFormDto.getSubject())
        .content(questionFormDto.getContent())
        .build();
    questionRepository.save(question);
  }
}
