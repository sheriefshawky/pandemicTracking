import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SymptomsSumbissionService } from './symptoms-sumbission.service';
import { SymptomsSumbissionDeleteDialogComponent } from './symptoms-sumbission-delete-dialog.component';

@Component({
  selector: 'jhi-symptoms-sumbission',
  templateUrl: './symptoms-sumbission.component.html'
})
export class SymptomsSumbissionComponent implements OnInit, OnDestroy {
  symptomsSumbissions?: ISymptomsSumbission[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected symptomsSumbissionService: SymptomsSumbissionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.symptomsSumbissionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISymptomsSumbission[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSymptomsSumbissions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISymptomsSumbission): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSymptomsSumbissions(): void {
    this.eventSubscriber = this.eventManager.subscribe('symptomsSumbissionListModification', () => this.loadPage());
  }

  delete(symptomsSumbission: ISymptomsSumbission): void {
    const modalRef = this.modalService.open(SymptomsSumbissionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptomsSumbission = symptomsSumbission;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISymptomsSumbission[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/symptoms-sumbission'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.symptomsSumbissions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
