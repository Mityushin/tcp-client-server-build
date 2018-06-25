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
    private static WordDAO instance;
    private DBPool dbPool;

    private WordDAO() {
        dbPool = DBPool.getInstance();
        try {
            Statement stmt = dbPool.getConnection().createStatement();
            String sql = "CREATE TABLE WORD (ID INTEGER NOT NULL AUTO_INCREMENT, TITLE VARCHAR(20) NOT NULL UNIQUE, DESCRIPTION VARCHAR(40))";
            stmt.executeUpdate(sql);
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
            String sql = "SELECT * FROM WORD WHERE WORD.TITLE = ?";
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(sql);

            pstmt.setString(1, w.getTitle());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Word word = new Word();
                word.setId(rs.getInt("ID"));
                word.setTitle(rs.getString("TITLE"));
                word.setDescription(rs.getString("DESCRIPTION"));
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
            Statement stmt = dbPool.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM WORD");
            System.out.println(rs.toString());

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
            String sql = "INSERT INTO WORD (TITLE, DESCRIPTION) VALUES (?, ?)";

            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(sql);
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
            String sql = "UPDATE WORD SET WORD.DESCRIPTION = ? WHERE WORD.TITLE = ?";
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(sql);
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
            String sql = "DELETE FROM WORD WHERE WORD.TITLE = ?";
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(sql);
            pstmt.setString(1, w.getTitle());

            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean exists(Word w) {
        try {
            String sql = "SELECT * FROM WORD WHERE WORD.TITLE = ?";
            PreparedStatement pstmt = dbPool.getConnection().prepareStatement(sql);
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
