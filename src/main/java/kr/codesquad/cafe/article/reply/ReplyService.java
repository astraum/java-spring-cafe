package kr.codesquad.cafe.article.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository repository;

    @Autowired
    public ReplyService(ReplyRepository repository) {
        this.repository = repository;
    }


    public List<Reply> retrieveByArticleId(long articleId) {
        return repository.findByArticleId(articleId);
    }

    public void post(Reply reply) {
        repository.save(reply);
    }
}
