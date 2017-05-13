import { Component, Output, EventEmitter, ViewChild } from '@angular/core';
import { Storage } from '../../providers/storage';
import { FabContainer } from 'ionic-angular';

@Component({
  selector: 'history',
  templateUrl: 'history.html'
})
export class HistoryComponent {

  @Output() updateByHistory = new EventEmitter();
  @ViewChild('fab') fab: FabContainer;
  historyItems: any = [];
  visible: boolean = false;

  constructor(private storage: Storage) {
    this.historyItems = storage.getHistory();
  }

  private update(item): void {
    this.updateByHistory.emit(item);
  }

  private show(): void {
    this.fab.close();
  }

  private toggle(): void {
    this.visible = !this.visible;
  }
}
