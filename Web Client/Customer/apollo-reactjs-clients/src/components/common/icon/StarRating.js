import React from 'react'
import StarIcon from '@mui/icons-material/Star';
import StarHalfIcon from '@mui/icons-material/StarHalf';
import StarOutlineIcon from '@mui/icons-material/StarOutline';

function StarRating({ rating, fontSize }) {
    // Calculate the number of filled stars and half star
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating - fullStars >= 0.5;
    const totalStars = 5;

    // Create an array to render the stars
    const stars = [];

    for (let i = 0; i < fullStars; i++) {
        stars.push(<StarIcon key={i} sx={{ fontSize: { fontSize } }} />);
    }

    if (hasHalfStar) {
        stars.push(<StarHalfIcon key={fullStars} sx={{ fontSize: { fontSize } }} />);
    }

    // Fill the rest with empty stars
    for (let i = stars.length; i < totalStars; i++) {
        stars.push(<StarOutlineIcon key={i} sx={{ fontSize: { fontSize } }} />);
    }

    return (
        <div className="text-yellow-500 text-sm items-center">
            {stars}
        </div>
    );
}

export default StarRating
