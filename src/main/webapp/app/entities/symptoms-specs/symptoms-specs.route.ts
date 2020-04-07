import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISymptomsSpecs, SymptomsSpecs } from 'app/shared/model/symptoms-specs.model';
import { SymptomsSpecsService } from './symptoms-specs.service';
import { SymptomsSpecsComponent } from './symptoms-specs.component';
import { SymptomsSpecsDetailComponent } from './symptoms-specs-detail.component';
import { SymptomsSpecsUpdateComponent } from './symptoms-specs-update.component';

@Injectable({ providedIn: 'root' })
export class SymptomsSpecsResolve implements Resolve<ISymptomsSpecs> {
  constructor(private service: SymptomsSpecsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISymptomsSpecs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((symptomsSpecs: HttpResponse<SymptomsSpecs>) => {
          if (symptomsSpecs.body) {
            return of(symptomsSpecs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SymptomsSpecs());
  }
}

export const symptomsSpecsRoute: Routes = [
  {
    path: '',
    component: SymptomsSpecsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pandemicTrackingApp.symptomsSpecs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SymptomsSpecsDetailComponent,
    resolve: {
      symptomsSpecs: SymptomsSpecsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSpecs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SymptomsSpecsUpdateComponent,
    resolve: {
      symptomsSpecs: SymptomsSpecsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSpecs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SymptomsSpecsUpdateComponent,
    resolve: {
      symptomsSpecs: SymptomsSpecsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSpecs.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
