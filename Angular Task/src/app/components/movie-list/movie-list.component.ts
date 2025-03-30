import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../services/movie.service';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  movies: any[] = []; // All movies fetched from API
  genres: string[] = ['All', 'Action', 'Drama', 'Crime', 'Adventure', 'Science Fiction']; // Available genres
  selectedGenre: string = 'All'; // Default genre
  searchTerm: string = ''; // Search term for filtering

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.movieService.getMovies().subscribe((data) => {
      this.movies = data;
    });
  }
}
