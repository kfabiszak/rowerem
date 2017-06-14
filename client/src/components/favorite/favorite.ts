import { Component, Output, EventEmitter, ViewChild } from '@angular/core';
import { Storage } from '../../providers/storage';
import { FabContainer } from 'ionic-angular';

@Component({
  selector: 'favorite',
  templateUrl: 'favorite.html'
})
export class FavoriteComponent {

  @Output() updateByFavorite = new EventEmitter();
  @ViewChild('fab') fab: FabContainer;
  favoriteItems: any = [];
  visible: boolean = false;

  constructor(private storage: Storage) {
    this.favoriteItems = storage.getFavorite();
  }

  private update(item): void {
    this.updateByFavorite.emit(item);
  }

  private remove(item): void {
    this.storage.removeElFavorite(item);
  }

  private show(): void {
    this.fab.close();
  }

  private toggle(): void {
    this.visible = !this.visible;
  }

}
