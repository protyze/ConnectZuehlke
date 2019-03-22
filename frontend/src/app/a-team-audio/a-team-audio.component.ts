import { Component, OnInit, Input, OnChanges, OnDestroy, SimpleChanges, ViewChild, ElementRef, AfterViewInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-a-team-audio',
  templateUrl: './a-team-audio.component.html',
  styleUrls: ['./a-team-audio.component.scss']
})
export class ATeamAudioComponent implements OnInit, OnChanges, AfterViewInit, OnDestroy {
  @Input() play = false;
  @Output() finishedPlaying = new EventEmitter<void>();

  @ViewChild('audioControl') audioSource: ElementRef<HTMLAudioElement>;

  playTimer = null;

  constructor() { }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.play) {
      this.audioSource.nativeElement.currentTime = 19;
      this.audioSource.nativeElement.play();
      this.playTimer = setTimeout(this.onTimerEnd.bind(this), 15000);
    }
  }

  ngAfterViewInit() {
    this.audioSource.nativeElement.currentTime = 19;
  }

  onTimerEnd() {
    this.audioSource.nativeElement.pause();
    this.audioSource.nativeElement.currentTime = 19;
    this.finishedPlaying.emit();
  }

  ngOnDestroy() {
    clearTimeout(this.playTimer);
  }
}
