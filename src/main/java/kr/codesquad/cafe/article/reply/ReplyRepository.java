package kr.codesquad.cafe.article.reply;

import kr.codesquad.cafe.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
public class ReplyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired

    public ReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Reply reply) {
        jdbcTemplate.update("INSERT INTO REPLY(article_id, writer_userId, writer_name, contents) VALUES (?, ?, ?, ?)",
                reply.getArticleId(), reply.getWriterUserId(), reply.getWriterName(), reply.getContents());
    }

    public List<Reply> findByArticleId(long articleId) {
        return jdbcTemplate.query("SELECT * FROM REPLY WHERE ARTICLE_ID=?", replyRowMapper(), articleId);
    }

    private RowMapper<Reply> replyRowMapper() {
        return ((rs, rowNum) -> {
            Reply reply = new Reply();
            reply.setId(rs.getLong("id"));
            reply.setArticleId(rs.getLong("article_id"));
            reply.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            reply.setWriterUserId(rs.getString("writer_userid"));
            reply.setWriterName(rs.getString("writer_name"));
            reply.setContents(rs.getString("contents"));

            return reply;
        });
    }

    public Optional<Reply> findOne(long id) {
        return jdbcTemplate.query("SELECT * FROM REPLY WHERE ID=?", replyRowMapper(), id)
                .stream().findAny();
    }

    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM REPLY WHERE ID=?", id);
    }
}
