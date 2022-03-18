package kr.codesquad.cafe.article.reply;

import kr.codesquad.cafe.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Reply retrieve(long id) {
        return repository.findOne(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 댓글입니다."));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
