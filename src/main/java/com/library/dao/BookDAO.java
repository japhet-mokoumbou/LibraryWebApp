package com.library.dao;

import com.library.model.Book;
import com.library.model.Category;
import com.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> findAll() {
        return findWithPaginationAndSearch(0, Integer.MAX_VALUE, null, 0);
    }

    public int countAll(String search, int categoryId) {
        String sql = "SELECT COUNT(*) FROM book b JOIN category c ON b.category_id = c.id WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            sql += " AND (b.title LIKE ? OR b.author LIKE ?)";
            String like = "%" + search + "%";
            params.add(like); params.add(like);
        }
        if (categoryId > 0) {
            sql += " AND b.category_id = ?";
            params.add(categoryId);
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Book> findWithPaginationAndSearch(int offset, int limit, String search, int categoryId) {
        String sql = """
            SELECT b.*, c.id as cat_id, c.name as cat_name 
            FROM book b 
            JOIN category c ON b.category_id = c.id 
            WHERE 1=1
            """;

        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            sql += " AND (b.title LIKE ? OR b.author LIKE ?)";
            String like = "%" + search + "%";
            params.add(like); params.add(like);
        }
        if (categoryId > 0) {
            sql += " AND b.category_id = ?";
            params.add(categoryId);
        }

        sql += " ORDER BY b.title LIMIT ? OFFSET ?";
        params.add(limit); params.add(offset);

        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setCoverImage(rs.getString("cover_image"));

                Category cat = new Category(rs.getInt("cat_id"), rs.getString("cat_name"));
                book.setCategory(cat);

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY name";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}