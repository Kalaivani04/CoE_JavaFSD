import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MovieService } from '../../services/movie.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any = {}; // Holds the logged-in user data
  reviews: any[] = []; // Holds user-specific reviews

  constructor(private movieService: MovieService, private router: Router) {}

  ngOnInit(): void {
    // ✅ Get logged-in user from localStorage
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);

      // ✅ Ensure reviews array is initialized if not present
      if (!this.user.reviews) {
        this.user.reviews = [];
      }

      // ✅ Fetch user-specific reviews if reviews array is not empty
      if (this.user.reviews.length > 0) {
        this.fetchUserReviews();
      } else {
        console.warn('No reviews found for this user.');
      }
    } else {
      alert('User not logged in. Redirecting to login.');
      this.router.navigate(['/login']);
    }
  }

  // ✅ Fetch user-specific reviews from API
  fetchUserReviews(): void {
    this.movieService.getReviews().subscribe(
      (allReviews) => {
        console.log('All reviews from API:', allReviews); // Debugging

        // ✅ Filter reviews based on user-stored review IDs
        this.reviews = allReviews.filter((review: any) =>
          this.user.reviews.includes(String(review.id)) // Match by review ID
        );

        console.log('Filtered user reviews:', this.reviews); // Debugging

        if (this.reviews.length === 0) {
          console.warn('No matching reviews found for this user.');
        }
      },
      (error) => {
        console.error('Error fetching reviews:', error);
      }
    );
  }
  goToMovie(movieId: string): void {
    if (movieId) {
      this.router.navigate(['/movie', movieId]); // Navigating to the movie details page
    } else {
      alert('Movie details not available for this review.');
    }
  }

  // ✅ Logout function to clear localStorage and redirect to login
  logout(): void {
    localStorage.removeItem('user'); // Remove user data from localStorage
    this.router.navigate(['/login']); // Redirect to login page
    alert('You have been logged out successfully!'); // Confirmation alert
  }
}
