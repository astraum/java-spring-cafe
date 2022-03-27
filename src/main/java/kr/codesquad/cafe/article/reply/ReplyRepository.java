package kr.codesquad.cafe.article.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
public class ReplyRepository {

    private static final String SQL_SAVE_REPLY =
            "INSERT INTO REPLY(article_id, writer_userId, writer_name, contents) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_REPLY_BY_ARTICLE_ID =
            "SELECT id, article_id, timestamp, writer_userid, writer_name, contents FROM REPLY WHERE deleted=FALSE AND article_id=?";
    private static final String SQL_FIND_REPLY =
            "SELECT id, article_id, timestamp, writer_userid, writer_name, contents FROM REPLY WHERE deleted=FALSE AND id=?";
    private static final String SQL_DELETE_REPLY =
            "UPDATE REPLY SET deleted=TRUE WHERE id=?";
    private static final String SQL_DELETE_REPLY_BY_ARTICLE_ID = "UPDATE REPLY SET DELETED=TRUE WHERE article_id=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Reply reply) {
        jdbcTemplate.update(SQL_SAVE_REPLY,
                reply.getArticleId(), reply.getWriterUserId(), reply.getWriterName(), reply.getContents());
    }

    public List<Reply> findByArticleId(long articleId) {
        return jdbcTemplate.query(SQL_FIND_REPLY_BY_ARTICLE_ID, replyRowMapper(), articleId);
    }

    public Optional<Reply> findOne(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_REPLY, replyRowMapper(), id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(long id) {
        jdbcTemplate.update(SQL_DELETE_REPLY, id);
    }

    public void deleteByArticleId(long articleId) {
        jdbcTemplate.update(SQL_DELETE_REPLY_BY_ARTICLE_ID, articleId);
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
}
