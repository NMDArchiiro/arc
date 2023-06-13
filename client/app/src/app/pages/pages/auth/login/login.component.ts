import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { fadeInUp400ms } from '../../../../../@vex/animations/fade-in-up.animation';
import { AuthService } from 'src/app/pages/apps/_service/auth.service';
import { TokenStorageService } from 'src/app/pages/apps/_service/token-storage.service';
import { AlertService } from 'src/app/pages/apps/_service/alert.service';
import { LayoutService } from 'src/@vex/services/layout.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'vex-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [
    fadeInUp400ms
  ]
})
export class LoginComponent implements OnInit {
  submitted = false;
  form: UntypedFormGroup;
  isLoggedIn = false;
  isLoginFailed = false;
  inputType = 'password';
  visible = false;
  errorMessage = '';
  roles: string[] = [];
  loading = false;
  returnUrl: string;
  isChecked = true;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private fb: UntypedFormBuilder,
    private cd: ChangeDetectorRef,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private alertService: AlertService,
    private layoutService: LayoutService,
    private loadingSpinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    this.loadingSpinner.hide();
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  toggleVisibility() {
    if (this.visible) {
      this.inputType = 'password';
      this.visible = false;
      this.cd.markForCheck();
    } else {
      this.inputType = 'text';
      this.visible = true;
      this.cd.markForCheck();
    }
  }

  get f() { return this.form.controls; }

  login(): void {
    this.submitted = true;
    if (this.form.invalid)
      return;

    this.authService.login(this.form.value)
      .subscribe({
        next: (response) => {
          this.tokenStorage.signOut();
          this.tokenStorage.saveToken(response.access_token);
          this.tokenStorage.saveUser(response);
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.layoutService.getCurrentUser().subscribe({
            next: (value: any) => {
              if (value) {
                this.layoutService.currentUser = value;
                if (value?.hivInfo && value?.vaacReport) {
                  this.tokenStorage.saveAccountIsHivInfo(value?.hivInfo);
                  this.tokenStorage.saveAccountIsVaac(value?.vaacReport);
                  this.router.navigate(['/choose-view-account']);
                }
                if (value?.hivInfo && !value?.vaacReport) {
                  this.tokenStorage.saveAccountIsHivInfo(value?.hivInfo);
                  this.router.navigate([this.returnUrl]);
                  return;
                }
                if (value?.vaacReport && !value?.hivInfo) {
                  this.tokenStorage.saveAccountIsVaac(value?.vaacReport);
                  this.router.navigate(['/tt05/dashboard']);
                }
              }
            }
          })
        },
        error: (error) => {
          this.isLoginFailed = true;
          if (error.status === 401) {
            this.alertService.error("Tên đăng nhập hoặc mật khẩu không chính xác!");
          } else {
            this.alertService.error(error.statusText);
          }
        }
      })
  }

}