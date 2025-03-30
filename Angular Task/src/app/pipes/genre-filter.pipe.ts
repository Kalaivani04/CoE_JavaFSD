import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'genreFilter'
})
export class GenreFilterPipe implements PipeTransform {
  transform(movies: any[], selectedGenre: string): any[] {
    if (!movies || movies.length === 0) return [];

    // Filter by genre only
    let filteredMovies = movies;
    if (selectedGenre && selectedGenre !== 'All') {
      filteredMovies = filteredMovies.filter((movie) =>
        movie.genre.toLowerCase().includes(selectedGenre.toLowerCase())
      );
    }

    return filteredMovies;
  }
}
