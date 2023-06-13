import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, Observable, of} from 'rxjs';
import { Router } from '@angular/router';
import {map} from "rxjs/operators";
import {ToastrService} from "ngx-toastr";
import { ConfigInitService } from '../init/config-init.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private configInitService: ConfigInitService,
    private toast : ToastrService,
    private router: Router) { }

  private AUTH_API = this.configInitService.apiBaseUrl + '/oauth/token';

  login(credentials: any): Observable<any> {
    let requestBody = "username=" + credentials.username + "&password=" + credentials.password;
    return this.http.post(this.AUTH_API, requestBody, httpOptions);
  }

  logout() {
    window.localStorage.removeItem("auth-token");
    localStorage.removeItem("auth-token");
    this.router.navigate(['/login']);
  }
  
  resetPassword(object: any){
    return this.http.post(this.configInitService.apiBaseUrl + '/oauth/reset-password',object).pipe(
      map(value => {
        return value;
      }),catchError(error => {
        this.toast.error("Có lỗi xảy ra vui lòng thử lại","Thông báo!")
        return of(false);
      })
    );
  }
}
