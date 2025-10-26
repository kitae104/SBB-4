package com.mysite.sbb.member.controller;

import com.mysite.sbb.member.dto.MemberDto;
import com.mysite.sbb.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/signup")
  public String signUp(Model model) {
    model.addAttribute("memberDto", new MemberDto());
    return "member/signup"; // signup.html 파일을 반환
  }

  @PostMapping("/signup")
  public String signUp(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
    log.info("=====> memberDto: {}", memberDto);

    if (bindingResult.hasErrors()) {
      return "member/signup";
    }

    if (!memberDto.getPassword1().equals(memberDto.getPassword2())) {
      // 필드명, 오류 코드, 오류 메시지
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
      return "member/signup";
    }

    memberService.create(memberDto);

    return "redirect:/";
  }

  @GetMapping("/login")
  public String login() {

    return "member/login";
  }

  @GetMapping(value = "/login/error")
  public String loginError(Model model){
    model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
    return "/member/login";
  }
}
