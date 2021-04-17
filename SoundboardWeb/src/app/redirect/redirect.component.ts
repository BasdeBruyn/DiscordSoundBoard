import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html',
  styleUrls: ['./redirect.component.scss']
})
export class RedirectComponent implements OnInit {
  constructor(private route: ActivatedRoute,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.authService.requestToken(params.code);
    });
  }

}
