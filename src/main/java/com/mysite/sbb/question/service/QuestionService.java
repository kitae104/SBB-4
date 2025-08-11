package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dto.QuestionFormDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  public List<Question> getList() {
    List<Question> questionList = questionRepository.findAll();
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
