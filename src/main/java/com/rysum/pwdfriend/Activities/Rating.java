package com.rysum.pwdfriend.Activities;

public class Rating {
    private String id;
    private String id2;
    public String reviewerName;
    public float reviewerRating;
    public String reviewerFeedback;
    public String reviewerImage;


    public Rating() {

    }

    public Rating(String id, String id2, String reviewerName, float reviewerRating, String reviewerFeedback, String reviewerImage) {
        this.id = id;
        this.id2 = id2;
        this.reviewerName = reviewerName;
        this.reviewerRating = reviewerRating;
        this.reviewerFeedback = reviewerFeedback;
        this.reviewerImage = reviewerImage;
    }

    public String getId() {
        return id;
    }

    public String getId2() {
        return id2;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public float getReviewerRating() {
        return reviewerRating;
    }

    public String getReviewerFeedback() {
        return reviewerFeedback;
    }

    public String getReviewerImage() {
        return reviewerImage;
    }
}