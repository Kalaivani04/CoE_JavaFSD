import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:4500/users'; // API URL for users

  constructor(private http: HttpClient) {}

  // Add user during registration
  addUser(user: any): Observable<any> {
    return this.http.post(this.apiUrl, user);
  }

  // Fetch all registered users
  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // ✅ Get user by email to check if the user already exists
  getUserByEmail(email: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}?email=${email}`);
  }
}
