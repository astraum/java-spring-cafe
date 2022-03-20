package kr.codesquad.cafe.article.reply;

import kr.codesquad.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/questions/{articleId}/answers")
public class ApiReplyController {

    private final ReplyService replyService;

    @Autowired
    public ApiReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("")
    public List<Reply> viewReply(@PathVariable("articleId") long articleId) {
        return replyService.retrieveByArticleId(articleId);
    }

    @PostMapping("")
    public List<Reply> createReply(@PathVariable("articleId") long articleId, String contents, HttpSession session) {
        Reply reply = new Reply();
        User writer = (User) session.getAttribute("currentUser");
        reply.setArticleId(articleId);
        reply.setWriterUserId(writer.getUserId());
        reply.setWriterName(writer.getName());
        reply.setContents(contents);

        replyService.post(reply);

        return replyService.retrieveByArticleId(articleId);
    }

    @DeleteMapping("/{replyId}")
    public List<Reply> deleteReply(@PathVariable("articleId") long articleId, @PathVariable("replyId") long replyId) {
        replyService.deleteById(replyId);

        return replyService.retrieveByArticleId(articleId);
    }
}
