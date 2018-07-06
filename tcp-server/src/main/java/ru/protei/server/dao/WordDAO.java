package ru.protei.server.dao;

import org.apache.log4j.Logger;
import ru.protei.server.database.DBConnectionManager;
import ru.protei.server.model.Word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for the model {@code Word}.
 * @author Dmitry Mityushin
 */

public class WordDAO {
    private static final Logger log = Logger.getLogger(WordDAO.class);

    private static final String SQL_TABLE_COLUMN_ID = "ID";
    private static final String SQL_TABLE_COLUMN_TITLE = "TITLE";
    private static final String SQL_TABLE_COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String SQL_CREATE_TABLE_WORD = "CREATE TABLE WORD (" +
            "ID INTEGER NOT NULL AUTO_INCREMENT, " +
            "TITLE VARCHAR(20) NOT NULL UNIQUE, " +
            "DESCRIPTION VARCHAR(40))";
    private static final String SQL_FIND_BY_TITLE = "SELECT * FROM WORD WHERE WORD.TITLE = ?";
    private static final String SQL_FIND_BY_TITLE_REGEXP = "SELECT * FROM WORD WHERE WORD.TITLE LIKE ?";
    private static final String SQL_CREATE_WORD = "INSERT INTO WORD (TITLE, DESCRIPTION) VALUES (?, ?)";
    private static final String SQL_UPDATE_WORD = "UPDATE WORD SET WORD.DESCRIPTION = ? WHERE WORD.TITLE = ?";
    private static final String SQL_DELETE_WORD_BY_TITLE = "DELETE FROM WORD WHERE WORD.TITLE = ?";
    private static final String SQL_CHECK_EXISTS_BY_TITLE = "SELECT * FROM WORD WHERE WORD.TITLE = ?";


    private static WordDAO instance;
    private DBConnectionManager dbConnectionManager;

    private WordDAO() {
        dbConnectionManager = DBConnectionManager.getInstance();
        try {
            Statement stmt = dbConnectionManager.getConnection().createStatement();
            stmt.executeUpdate(SQL_CREATE_TABLE_WORD);
            log.info("Table WORD created");
        } catch (SQLException e) {
            log.fatal("Can't create table WORD", e);
        }
    }

    /**
     * Return {@code WordDAO} instance.
     *
     * @return {@code WordDAO} instance
     */
    public static WordDAO getInstance() {
        if (instance == null) {
            instance = new WordDAO();
        }
        return instance;
    }

    /**
     * Find {@code Word} by {@code Word} title.
     *
     * @param w the {@code Word} element wich contains title to be found
     * @return the {@code Word} of the element to be found
     * @return null if there isn't {@code Word} with specified title
     */
    public Word find(Word w) {
        try {
            PreparedStatement pstmt = dbConnectionManager.getConnection().prepareStatement(SQL_FIND_BY_TITLE);

            pstmt.setString(1, w.getTitle());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Word word = new Word();
                word.setId(rs.getInt(SQL_TABLE_COLUMN_ID));
                word.setTitle(rs.getString(SQL_TABLE_COLUMN_TITLE));
                word.setDescription(rs.getString(SQL_TABLE_COLUMN_DESCRIPTION));
                return word;
            }

        } catch (SQLException e) {
            log.error("Error find", e);
        }
        return null;
    }

    /**
     * Find {@code Word} by regexp.
     *
     * @param mask the regexp for search
     * @return the {@code List} of {@code Word} of elements to be found
     * @return null if there isn't {@code Word} with specified title
     */
    public List<Word> findAll(String mask) {
        List<Word> list = new ArrayList<Word>();
        try {
            PreparedStatement pstmt = dbConnectionManager.getConnection().prepareStatement(SQL_FIND_BY_TITLE_REGEXP);
            pstmt.setString(1, mask);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Word word = new Word();
                word.setId(rs.getInt(SQL_TABLE_COLUMN_ID));
                word.setTitle(rs.getString(SQL_TABLE_COLUMN_TITLE));
                word.setDescription(rs.getString(SQL_TABLE_COLUMN_DESCRIPTION));
                list.add(word);
            }
        } catch (SQLException e) {
            log.error("Error findAll", e);
        }
        return list;
    }
    public boolean create(Word w) {
        try {
            PreparedStatement pstmt = dbConnectionManager.getConnection().prepareStatement(SQL_CREATE_WORD);
            pstmt.setString(1, w.getTitle());
            pstmt.setString(2, w.getDescription());

            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error("Error insert", e);
        }
        return false;
    }
    public boolean update(Word w) {
        try {
            PreparedStatement pstmt = dbConnectionManager.getConnection().prepareStatement(SQL_UPDATE_WORD);
            pstmt.setString(1, w.getDescription());
            pstmt.setString(2, w.getTitle());

            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error("Error update", e);
        }
        return false;
    }
    public boolean delete(Word w) {
        try {
            PreparedStatement pstmt = dbConnectionManager.getConnection().prepareStatement(SQL_DELETE_WORD_BY_TITLE);
            pstmt.setString(1, w.getTitle());

            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error("Error delete", e);
        }
        return false;
    }
    public boolean exists(Word w) {
        try {
            PreparedStatement pstmt = dbConnectionManager.getConnection().prepareStatement(SQL_CHECK_EXISTS_BY_TITLE);
            pstmt.setString(1, w.getTitle());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Error exists", e);
        }
        return false;
    }
}
