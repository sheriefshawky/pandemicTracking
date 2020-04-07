import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISymptomsSubDetails, SymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';
import { SymptomsSubDetailsService } from './symptoms-sub-details.service';
import { SymptomsSubDetailsComponent } from './symptoms-sub-details.component';
import { SymptomsSubDetailsDetailComponent } from './symptoms-sub-details-detail.component';
import { SymptomsSubDetailsUpdateComponent } from './symptoms-sub-details-update.component';

@Injectable({ providedIn: 'root' })
export class SymptomsSubDetailsResolve implements Resolve<ISymptomsSubDetails> {
  constructor(private service: SymptomsSubDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISymptomsSubDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((symptomsSubDetails: HttpResponse<SymptomsSubDetails>) => {
          if (symptomsSubDetails.body) {
            return of(symptomsSubDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SymptomsSubDetails());
  }
}

export const symptomsSubDetailsRoute: Routes = [
  {
    path: '',
    component: SymptomsSubDetailsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pandemicTrackingApp.symptomsSubDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SymptomsSubDetailsDetailComponent,
    resolve: {
      symptomsSubDetails: SymptomsSubDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSubDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SymptomsSubDetailsUpdateComponent,
    resolve: {
      symptomsSubDetails: SymptomsSubDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSubDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SymptomsSubDetailsUpdateComponent,
    resolve: {
      symptomsSubDetails: SymptomsSubDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSubDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
