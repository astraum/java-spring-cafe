package kr.codesquad.cafe.article;

import kr.codesquad.cafe.article.reply.Reply;
import kr.codesquad.cafe.article.reply.ReplyService;
import kr.codesquad.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ReplyService replyService;

    @Autowired
    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/")
    public String viewQuestions(Model model) {
        model.addAttribute("articles", articleService.retrieveAll());

        return "index";
    }

    @PostMapping("/questions")
    public String processCreationForm(ArticleCreationForm form, HttpSession session) {
        Article article = new Article();
        User writer = (User) session.getAttribute("currentUser");
        article.setWriterUserId(writer.getUserId());
        article.setWriterName(writer.getName());
        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        articleService.post(article);

        return "redirect:/";
    }

    @GetMapping("/questions/{articleId}")
    public String viewQuestion(@PathVariable("articleId") long id, Model model) {
        model.addAttribute("article", articleService.retrieve(id));

        return "qna/show";
    }

    @GetMapping("/questions/{articleId}/form")
    public String viewUpdateForm(@PathVariable("articleId") long id, Model model) {
        model.addAttribute("article", articleService.retrieve(id));

        return "qna/updateForm";
    }

    @PutMapping("/questions/{articleId}/update")
    public String processUpdateForm(@PathVariable("articleId") long id, ArticleCreationForm form, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        Article article = new Article();
        article.setId(id);
        article.setWriterName(currentUser.getName());
        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        articleService.update(article);

        return "redirect:/questions/{articleId}";
    }

    @DeleteMapping("/questions/{articleId}/delete")
    public String deleteArticle(@PathVariable("articleId") long articleId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (articleHasReplyByOtherUser(articleId, currentUser)) {
            return "redirect:/badRequest";
        }

        replyService.deleteByArticleId(articleId);
        articleService.deleteById(articleId);

        return "redirect:/";
    }

    private boolean articleHasReplyByOtherUser(long articleId, User currentUser) {
        return replyService.retrieveByArticleId(articleId)
                .stream()
                .map(Reply::getWriterUserId)
                .anyMatch(writerUserId -> !currentUser.userIdIs(writerUserId));
    }

    @DeleteMapping("/questions/{articleId}/answers/{replyId}/delete")
    public String deleteReply(@PathVariable("replyId") long id) {
        replyService.deleteById(id);

        return "redirect:/questions/{articleId}";
    }
}
