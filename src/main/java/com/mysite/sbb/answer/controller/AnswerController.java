package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.dto.AnswerFormDto;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.dto.QuestionFormDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

  private final QuestionService questionService;
  private final AnswerService answerService;

  @PostMapping("/create/{id}")
  public String createAnswer(@PathVariable("id") Long id,
                             //@RequestParam(value = "content") String content,
                             @Valid AnswerFormDto answerFormDto,
                             BindingResult bindingResult,
                             Model model ) {
    Question question = questionService.getQuestion(id);
    if(bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question/detail";
    }
    answerService.create(question, answerFormDto.getContent());
    log.info("Creating answer for question ID: {}, content: {}", id, answerFormDto.getContent());
    return "redirect:/question/detail/" + id;
  }
}
