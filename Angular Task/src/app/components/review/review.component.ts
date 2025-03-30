import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css'],
})
export class ReviewComponent {
  @Input() movieId!: number; // Movie ID passed from the parent component
  @Input() user: any = JSON.parse(localStorage.getItem('user') || '{}'); // Get logged-in user
  @Output() reviewSubmitted: EventEmitter<any> = new EventEmitter(); // Event emitter to send review to parent component

  reviewText: string = '';
  rating: number | null = null;

  submitReview() {
    if (this.reviewText && this.rating) {
      const reviewData = {
        movieId: this.movieId,
        review: this.reviewText,
        rating: this.rating,
        user: this.user.name || 'Anonymous', // Add user name here
      };
      this.reviewSubmitted.emit(reviewData); // Send the review data to the parent component
      this.reviewText = '';
      this.rating = null;
    } else {
      alert('Please enter both a review and a rating!');
    }
  }
}
