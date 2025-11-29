package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pageStr = req.getParameter("page");
        String search = req.getParameter("search");
        String catStr = req.getParameter("category");

        int page = pageStr == null || pageStr.isEmpty() ? 1 : Integer.parseInt(pageStr);
        int categoryId = catStr == null || catStr.isEmpty() ? 0 : Integer.parseInt(catStr);
        int offset = (page - 1) * PAGE_SIZE;

        List<Book> books = bookDAO.findWithPaginationAndSearch(offset, PAGE_SIZE, search, categoryId);
        int totalBooks = bookDAO.countAll(search, categoryId);
        int totalPages = (int) Math.ceil((double) totalBooks / PAGE_SIZE);

        List<Category> categories = bookDAO.getAllCategories();

        req.setAttribute("books", books);
        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("search", search != null ? search : "");
        req.setAttribute("selectedCategory", categoryId);

        req.getRequestDispatcher("/books.jsp").forward(req, resp);
    }
}