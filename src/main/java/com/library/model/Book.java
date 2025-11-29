package com.library.model;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String author;
    private Category category;
    private int totalCopies;
    private int availableCopies;
    private String coverImage;

    public Book() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getAvailabilityClass() {
        if (availableCopies == 0) return "bg-red-500";
        if (availableCopies <= totalCopies / 3) return "bg-orange-500";
        return "bg-green-500";
    }
}