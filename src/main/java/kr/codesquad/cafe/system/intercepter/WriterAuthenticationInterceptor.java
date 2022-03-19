package kr.codesquad.cafe.system.intercepter;

import kr.codesquad.cafe.article.ArticleService;
import kr.codesquad.cafe.user.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class WriterAuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private ArticleService articleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariable = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long targetArticleId = Long.parseLong((String) pathVariable.get("articleId"));
        String writerUserId = articleService.retrieve(targetArticleId).getWriterUserId();
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser.userIdIs(writerUserId)) {
            return true;
        }

        response.sendRedirect("/badRequest");

        return false;
    }

}
