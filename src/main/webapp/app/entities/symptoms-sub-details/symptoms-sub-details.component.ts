import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SymptomsSubDetailsService } from './symptoms-sub-details.service';
import { SymptomsSubDetailsDeleteDialogComponent } from './symptoms-sub-details-delete-dialog.component';

@Component({
  selector: 'jhi-symptoms-sub-details',
  templateUrl: './symptoms-sub-details.component.html'
})
export class SymptomsSubDetailsComponent implements OnInit, OnDestroy {
  symptomsSubDetails?: ISymptomsSubDetails[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected symptomsSubDetailsService: SymptomsSubDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.symptomsSubDetailsService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISymptomsSubDetails[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSymptomsSubDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISymptomsSubDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSymptomsSubDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('symptomsSubDetailsListModification', () => this.loadPage());
  }

  delete(symptomsSubDetails: ISymptomsSubDetails): void {
    const modalRef = this.modalService.open(SymptomsSubDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptomsSubDetails = symptomsSubDetails;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISymptomsSubDetails[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/symptoms-sub-details'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.symptomsSubDetails = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
