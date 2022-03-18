package kr.codesquad.cafe.article.reply;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

public class Reply {

    private Long id;
    private Long articleId;
    private LocalDateTime timestamp;
    private String writerUserId;
    private String writerName;
    private String contents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getWriterUserId() {
        return writerUserId;
    }

    public void setWriterUserId(String writerUserId) {
        Assert.hasText(writerUserId, "작성자 ID는 공백이어선 안 됩니다.");
        this.writerUserId = writerUserId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        Assert.hasText(writerName, "작성자 이름은 공백이어선 안 됩니다.");
        this.writerName = writerName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        Assert.hasText(contents, "댓글 내용은 공백이어선 안 됩니다.");
        this.contents = contents;
    }
}
