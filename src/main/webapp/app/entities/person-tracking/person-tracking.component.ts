import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonTracking } from 'app/shared/model/person-tracking.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PersonTrackingService } from './person-tracking.service';
import { PersonTrackingDeleteDialogComponent } from './person-tracking-delete-dialog.component';

@Component({
  selector: 'jhi-person-tracking',
  templateUrl: './person-tracking.component.html'
})
export class PersonTrackingComponent implements OnInit, OnDestroy {
  personTrackings?: IPersonTracking[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected personTrackingService: PersonTrackingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.personTrackingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPersonTracking[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInPersonTrackings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPersonTracking): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPersonTrackings(): void {
    this.eventSubscriber = this.eventManager.subscribe('personTrackingListModification', () => this.loadPage());
  }

  delete(personTracking: IPersonTracking): void {
    const modalRef = this.modalService.open(PersonTrackingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.personTracking = personTracking;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPersonTracking[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/person-tracking'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.personTrackings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
