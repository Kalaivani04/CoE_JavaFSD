import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  name: string = '';
  email: string = '';
  password: string = '';

  constructor(private userService: UserService, private router: Router) {}

  register() {
    // Check if the user already exists
    this.userService.getUserByEmail(this.email).subscribe(
      (existingUser) => {
        if (existingUser && existingUser.length > 0) {
          alert('User already exists. Please log in or use a different email.');
        } else {
          const user = {
            name: this.name,
            email: this.email,
            password: this.password,
            reviews: [] 
          };

          this.userService.addUser(user).subscribe(
            (response) => {
              alert('Registration successful! You can now log in.');
              this.router.navigate(['/login']);
            },
            (error) => {
              console.error('Error registering user:', error);
              alert('Error registering user. Please try again later.');
            }
          );
        }
      },
      (error) => {
        console.error('Error checking user existence:', error);
        alert('Error checking user existence. Please try again later.');
      }
    );
  }
}
