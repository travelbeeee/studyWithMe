package travelbeeee.communityPjt.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import travelbeeee.communityPjt.domain.MainWriting;
import travelbeeee.communityPjt.domain.Member;
import travelbeeee.communityPjt.domain.UploadFile;
import travelbeeee.communityPjt.domain.Writing;
import travelbeeee.communityPjt.form.WritingForm;
import travelbeeee.communityPjt.service.IFilesService;
import travelbeeee.communityPjt.service.MemberService;
import travelbeeee.communityPjt.service.WritingService;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WritingController {

    private final MemberService memberService;
    private final IFilesService filesService;
    private final WritingService writingService;
    Logger logger = LoggerFactory.getLogger(WritingController.class);

    @GetMapping("/writing/write")
    public String writingForm(){
        return "/writing/writeForm";
    }

    @PostMapping("/writing/write")
    public String write(WritingForm writingForm, HttpSession session) throws NoSuchAlgorithmException {
        logger.info("Write 메소드 작동!!");
        Writing writing = new Writing();
        // input 에서 file에 아무것도 넣지 않아도 일단 클래스는 넘어온다...
        if(writingForm.getFiles().getOriginalFilename().isEmpty()){
            writing.setUploadFileId(-1L);
        }else{
            Long uploadFileId = filesService.fileUpload(writingForm.getFiles());
            writing.setUploadFileId(uploadFileId);
        }

        writing.setMemberId((Long)session.getAttribute("id"));
        writing.setTitle(writingForm.getTitle());
        writing.setContent(writingForm.getContent());
        writing.setWritingTime(LocalDateTime.now());

        writingService.insertWriting(writing);

        return "redirect:/main";
    }

    @GetMapping("/writing/{writingId}")
    public String writingDetail(@PathVariable("writingId") Long writingId, Model model, HttpSession session){
        Writing writing = writingService.getWritingById(writingId);
        Member member = memberService.getMemberById(writing.getMemberId());

        if(writing.getMemberId() != (Long)session.getAttribute("id"))
            writingService.updateViews(writingId);

        MainWriting mainWriting = new MainWriting();
        mainWriting.setWritingId(writing.getWritingId());
        mainWriting.setTitle(writing.getTitle());
        mainWriting.setContent(writing.getContent());
        mainWriting.setUsername(member.getUsername());
        mainWriting.setWritingTime(writing.getWritingTime());
        mainWriting.setUploadFileId(writing.getUploadFileId());

        model.addAttribute("mainWriting", mainWriting);

        return "/writing/writeDetail";
    }

    @PostMapping("/writing/delete/{writingId}")
    public String writingDelete(@PathVariable("writingId") Long writingId){
        Writing writing = writingService.getWritingById(writingId);
        UploadFile uploadFile = filesService.getUploadFileById(writing.getUploadFileId());
        if(uploadFile != null)
            filesService.deleteUploadFileById(uploadFile.getUploadFileId());
        writingService.deleteWriting(writingId);

        return "redirect:/main";
    }

    @GetMapping("/writing/modify/{writingId}")
    public String writingModifyForm(@PathVariable("writingId") Long writingId, Model model){
        Writing writing = writingService.getWritingById(writingId);
        model.addAttribute("writing", writing);

        return "/writing/modifyForm";
    }

    @PostMapping("/writing/modify/{writingId}")
    public String writingModify(@PathVariable("writingId") Long writingId, WritingForm writingForm) throws NoSuchAlgorithmException {
        Writing writing = writingService.getWritingById(writingId);
        // 새로운 파일 값이 들어왔으면 기존에 파일을 삭제하고 새롭게 넣자
        if(!writingForm.getFiles().getOriginalFilename().isEmpty()) {
            filesService.deleteUploadFileById(writing.getUploadFileId());
            Long res = filesService.fileUpload(writingForm.getFiles());
            writing.setUploadFileId(res);
        }
        writing.setWritingId(writingId);
        writing.setTitle(writingForm.getTitle());
        writing.setContent(writingForm.getContent());

        writingService.update(writing);

        return "redirect:/writing/" + writingId.toString();
    }
}
