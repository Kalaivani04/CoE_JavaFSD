import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private userService: UserService, private router: Router) {}

  login() {
    this.userService.getUsers().subscribe(
      (users) => {
        const user = users.find(
          (u) => u.email === this.email && u.password === this.password
        );

        if (user) {
          // ✅ Ensure reviews array exists if missing
          if (!user.reviews) {
            user.reviews = []; // Initialize reviews array to avoid undefined errors
          }

          // ✅ Save updated user to localStorage
          localStorage.setItem('user', JSON.stringify(user));
          alert('Login successful!');
          this.router.navigate(['/profile']);
        } else {
          alert('Invalid email or password!');
        }
      },
      (error) => {
        console.error('Error fetching users:', error);
        alert('Error fetching user data. Please try again later.');
      }
    );
  }
}
