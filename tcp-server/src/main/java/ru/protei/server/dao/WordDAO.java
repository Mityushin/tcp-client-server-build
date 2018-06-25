package ru.protei.server.dao;

import ru.protei.server.database.DBPool;
import ru.protei.server.model.Word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {
    private static final String SQL_TABLE_COLUMN_ID = "ID";
    private static final String SQL_TABLE_COLUMN_TITLE = "TITLE";
    private static final String SQL_TABLE_COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String SQL_CREATE_TABLE_WORD = "CREATE TABLE WORD (" +
            "ID INTEGER NOT NULL AUTO_INCREMENT, " +
            "TITLE VARCHAR(20) NOT NULL UNIQUE, " +
            "DESCRIPTION VARCHAR(40))";
    private static final String SQL_FIND_BY_TITLE = "SELECT * FROM WORD WHERE WORD.TITLE = ?";
    private static final String SQL_FIND_BY_TITLE_REGEXP = "SELECT * FROM WORD WHERE WORD.TITLE REGEXP ?";
    private static final String SQL_CREATE_WORD = "INSERT INTO WORD (TITLE, DESCRIPTION) VALUES (?, ?)";
    private static final String SQL_UPDATE_WORD = "UPDATE WORD SET WORD.DESCRIPTION = ? WHERE WORD.TITLE = ?";
    private static final String SQL_DELETE_WORD_BY_TITLE = "DELETE FROM WORD WHERE WORD.TITLE = ?";
    private static final String SQL_CHECK_EXISTS_BY_TITLE = "SELECT * FROM WORD WHERE WORD.TITLE = ?";


    private static WordDAO instance;
    private DBPool dbPool;

    private WordDAO() {
        dbPool = DBPool.getInstance();
        try {
            Statement stmt = dbPool.getConnection().createStatement();
            stmt.executeUpdate(SQL_CREATE_TABLE_WORD);
            System.out.println("Table WORD created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static WordDAO getInstance() {
        if (instance == null) {
            instance = new WordDAO();
        }
        return instance;
    }

    public Word find(Word w) {
        try {
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(SQL_FIND_BY_TITLE);

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
            e.printStackTrace();
        }
        return null;
    }
    public List<Word> findAll(String mask) {
        List<Word> list = new ArrayList<Word>();
        try {
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(SQL_FIND_BY_TITLE_REGEXP);
            pstmt.setString(1, mask);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Word word = new Word();
                word.setId(rs.getInt("ID"));
                word.setTitle(rs.getString("TITLE"));
                word.setDescription(rs.getString("DESCRIPTION"));
                list.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean create(Word w) {
        try {
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(SQL_CREATE_WORD);
            pstmt.setString(1, w.getTitle());
            pstmt.setString(2, w.getDescription());

            if (pstmt.executeUpdate() > 0) {
                System.out.println("New word was inserted successfully!");
                return true;
            } else {
                System.out.println("Error while inserting new word.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(Word w) {
        try {
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(SQL_UPDATE_WORD);
            pstmt.setString(1, w.getDescription());
            pstmt.setString(2, w.getTitle());

            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(Word w) {
        try {
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(SQL_DELETE_WORD_BY_TITLE);
            pstmt.setString(1, w.getTitle());

            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean exists(Word w) {
        try {
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(SQL_CHECK_EXISTS_BY_TITLE);
            pstmt.setString(1, w.getTitle());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
