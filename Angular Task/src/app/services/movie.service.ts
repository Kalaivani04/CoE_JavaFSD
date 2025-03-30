import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private apiUrl = 'http://localhost:4500'; 

  constructor(private http: HttpClient) {}

  // Fetch all movies
  getMovies(): Observable<any> {
    return this.http.get(`${this.apiUrl}/movies`);
  }

  // Fetch all reviews
  getReviews(): Observable<any> {
    return this.http.get(`${this.apiUrl}/reviews`);
  }

  // Add a new review and update the user's reviews array
  addReview(review: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/reviews`, review).pipe(
      tap((newReview) => {
        this.http.get<any[]>(`${this.apiUrl}/users`).subscribe((users) => {
          const userIndex = users.findIndex((u) => u.name === review.user);

          if (userIndex !== -1) {
            if (!users[userIndex].reviews) {
              users[userIndex].reviews = []; 
            }
            users[userIndex].reviews.push(newReview.id);
            this.updateUser(users[userIndex]).subscribe(
              () => console.log('User reviews updated successfully!'),
              (error) => console.error('Error updating user reviews:', error)
            );
          }
        });
      })
    );
  }

  // Update user details 
  updateUser(user: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/users/${user.id}`, user);
  }
}
