package stub.review;

import model.Review;

import java.util.Date;

public class ReviewStub {

    public static Review getReview(){
        return new Review(null, 5, new Date(), "Nice movie", "user3", null, null, null);
    }

}
