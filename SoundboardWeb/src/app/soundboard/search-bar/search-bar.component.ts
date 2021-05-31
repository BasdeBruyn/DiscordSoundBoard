import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SoundEffectService} from '../../soundEffect.service';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit {
  @Output() searchTermUpdated = new EventEmitter<string>();

  constructor(
    private soundEffectService: SoundEffectService
  ) {
  }

  ngOnInit(): void {
  }

  updateSearchTerm(searchTerm: string): void {
    this.soundEffectService.search(searchTerm);
  }
}
