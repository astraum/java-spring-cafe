package kr.codesquad.cafe.system.interceptor;

import kr.codesquad.cafe.article.reply.ReplyService;
import kr.codesquad.cafe.user.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ReplyAuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private ReplyService replyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (currentUserIsWriter(request)) {
            return true;
        }

        response.sendRedirect("/badRequest");

        return false;
    }

    private boolean currentUserIsWriter(HttpServletRequest request) {
        String writerUserId = getWriterUserId(request);
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        return currentUser.userIdIs(writerUserId);
    }

    private String getWriterUserId(HttpServletRequest request) {
        Map pathVariable = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long replyId = Long.parseLong((String) pathVariable.get("replyId"));

        return replyService.retrieve(replyId).getWriterUserId();
    }
}
