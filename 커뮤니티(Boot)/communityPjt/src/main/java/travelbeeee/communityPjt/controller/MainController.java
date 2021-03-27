package travelbeeee.communityPjt.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import travelbeeee.communityPjt.domain.MainWriting;
import travelbeeee.communityPjt.domain.Writing;
import travelbeeee.communityPjt.service.MemberService;
import travelbeeee.communityPjt.service.WritingService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final WritingService writingService;
    private final MemberService memberService;
    Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/main")
    public String main(HttpSession session, Model model){
        if(session.getAttribute("id") == null) return "redirect:/";

        List<Writing> writings = writingService.getWritings();

        List<MainWriting> mainWritings = new ArrayList<>();
        for(Writing writing : writings){
            MainWriting mainWriting = new MainWriting();
            mainWriting.setUsername(memberService.getMemberById(writing.getMemberId()).getUsername());
            mainWriting.setTitle(writing.getTitle());
            mainWriting.setWritingTime(writing.getWritingTime());
            mainWriting.setViews(writing.getViews());
            mainWriting.setWritingId(writing.getWritingId());

            mainWritings.add(mainWriting);
        }
        model.addAttribute("writingList", mainWritings);
        return "/main";
    }
}
