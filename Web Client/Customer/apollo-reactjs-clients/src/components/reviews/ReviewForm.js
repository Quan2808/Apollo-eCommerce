import React, { useState } from 'react';
import { createReview } from '../../api/reviewAPI';

function ReviewForm({ variantId, userId }) {
    const [reviewData, setReviewData] = useState({
        star: 0,
        title: '',
        content: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setReviewData({
            ...reviewData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await createReview({ review: reviewData, variantId, userId });
        if (response) {
            // Handle the successful response (e.g., show a success message)
        } else {
            // Handle the error (e.g., show an error message)
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Star Rating:
                <input type="number" name="star" value={reviewData.star} onChange={handleChange} min="1" max="5" required />
            </label>
            <label>
                Title:
                <input type="text" name="title" value={reviewData.title} onChange={handleChange} required />
            </label>
            <label>
                Content:
                <textarea name="content" value={reviewData.content} onChange={handleChange} required />
            </label>
            <button type="submit">Submit Review</button>
        </form>
    );
}

export default ReviewForm;
