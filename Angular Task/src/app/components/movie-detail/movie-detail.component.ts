import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MovieService } from '../../services/movie.service';

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css'],
})
export class MovieDetailComponent implements OnInit {
  movie: any = null; // Holds the movie details
  movieReviews: any[] = []; // Holds reviews for the movie
  movieId!: number; // Holds movieId after parsing
  user: any = JSON.parse(localStorage.getItem('user') || '{}'); // Get logged-in user

  // Add Review Variables
  reviewText: string = '';
  rating: number | null = null;

  constructor(private route: ActivatedRoute, private movieService: MovieService) {}

  ngOnInit(): void {
    const movieIdParam = this.route.snapshot.paramMap.get('id');

    if (movieIdParam) {
      this.movieId = parseInt(movieIdParam, 10); // Convert to number safely

      if (isNaN(this.movieId)) {
        console.error('Invalid movie ID format!');
        return;
      }

      this.fetchMovieDetails(this.movieId); // Fetch movie details
      this.fetchMovieReviews(this.movieId); // Fetch movie reviews
    } else {
      console.error('Movie ID not provided in URL!');
    }
  }

  // Fetch movie details by ID
  fetchMovieDetails(movieId: number): void {
    this.movieService.getMovies().subscribe(
      (movies) => {
        console.log('Fetched Movies:', movies);

        this.movie = movies.find((m: any) => Number(m.id) === movieId);

        if (this.movie) {
          console.log('Movie found:', this.movie);
        } else {
          console.warn(`Movie with ID ${movieId} not found!`);
        }
      },
      (error) => {
        console.error('Error fetching movies:', error);
      }
    );
  }

  // Fetch movie reviews by movieId
  fetchMovieReviews(movieId: number): void {
    this.movieService.getReviews().subscribe(
      (reviews) => {
        console.log('Fetched Reviews:', reviews);

        this.movieReviews = reviews.filter((r: any) => Number(r.movieId) === movieId);

        if (this.movieReviews.length > 0) {
          console.log(`Found ${this.movieReviews.length} review(s) for movie ID ${movieId}`);
        } else {
          console.warn('No reviews found for this movie.');
        }
      },
      (error) => {
        console.error('Error fetching reviews:', error);
      }
    );
  }

  // Handle Review Submitted from ReviewComponent
  onReviewSubmitted(newReview: any): void {
    console.log('New review submitted:', newReview);

    newReview.user = this.user.name || 'Anonymous';
    
    this.movieReviews.push(newReview);

    // Optionally, save to the backend via the service if needed
    this.movieService.addReview(newReview).subscribe(
      (response) => {
        console.log('Review successfully added to the backend!');
      },
      (error) => {
        console.error('Error saving the review to the backend:', error);
      }
    );
  }
}
