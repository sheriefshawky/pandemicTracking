import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PersonInteracitonTrackingService } from './person-interaciton-tracking.service';
import { PersonInteracitonTrackingDeleteDialogComponent } from './person-interaciton-tracking-delete-dialog.component';

@Component({
  selector: 'jhi-person-interaciton-tracking',
  templateUrl: './person-interaciton-tracking.component.html'
})
export class PersonInteracitonTrackingComponent implements OnInit, OnDestroy {
  personInteracitonTrackings?: IPersonInteracitonTracking[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected personInteracitonTrackingService: PersonInteracitonTrackingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.personInteracitonTrackingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPersonInteracitonTracking[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInPersonInteracitonTrackings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPersonInteracitonTracking): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPersonInteracitonTrackings(): void {
    this.eventSubscriber = this.eventManager.subscribe('personInteracitonTrackingListModification', () => this.loadPage());
  }

  delete(personInteracitonTracking: IPersonInteracitonTracking): void {
    const modalRef = this.modalService.open(PersonInteracitonTrackingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.personInteracitonTracking = personInteracitonTracking;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPersonInteracitonTracking[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/person-interaciton-tracking'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.personInteracitonTrackings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
