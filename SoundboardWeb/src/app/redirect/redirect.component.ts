import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html',
  styleUrls: ['./redirect.component.scss']
})
export class RedirectComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.authService.requestToken(params.code)
        .then(() => this.router.navigate(['soundboard']))
        .catch(() => this.router.navigate(['']));
    });
  }
}
