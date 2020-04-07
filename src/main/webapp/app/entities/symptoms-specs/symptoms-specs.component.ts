import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptomsSpecs } from 'app/shared/model/symptoms-specs.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SymptomsSpecsService } from './symptoms-specs.service';
import { SymptomsSpecsDeleteDialogComponent } from './symptoms-specs-delete-dialog.component';

@Component({
  selector: 'jhi-symptoms-specs',
  templateUrl: './symptoms-specs.component.html'
})
export class SymptomsSpecsComponent implements OnInit, OnDestroy {
  symptomsSpecs?: ISymptomsSpecs[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected symptomsSpecsService: SymptomsSpecsService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.symptomsSpecsService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISymptomsSpecs[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSymptomsSpecs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISymptomsSpecs): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSymptomsSpecs(): void {
    this.eventSubscriber = this.eventManager.subscribe('symptomsSpecsListModification', () => this.loadPage());
  }

  delete(symptomsSpecs: ISymptomsSpecs): void {
    const modalRef = this.modalService.open(SymptomsSpecsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptomsSpecs = symptomsSpecs;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISymptomsSpecs[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/symptoms-specs'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.symptomsSpecs = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
