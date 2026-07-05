package com.example.bmt.model;

public class ReviewDto {
    private String comment;
    private int fullStars;
    private boolean halfStar;
    private int emptyStars;
    private String decryptedUsername;
    private String img;

    // Getters and setters
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getFullStars() { return fullStars; }
    public void setFullStars(int fullStars) { this.fullStars = fullStars; }

    public boolean isHalfStar() { return halfStar; }
    public void setHalfStar(boolean halfStar) { this.halfStar = halfStar; }

    public int getEmptyStars() { return emptyStars; }
    public void setEmptyStars(int emptyStars) { this.emptyStars = emptyStars; }

    public String getDecryptedUsername() { return decryptedUsername; }
    public void setDecryptedUsername(String decryptedUsername) { this.decryptedUsername = decryptedUsername; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
}
