import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISymptomsSumbission, SymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';
import { SymptomsSumbissionService } from './symptoms-sumbission.service';
import { SymptomsSumbissionComponent } from './symptoms-sumbission.component';
import { SymptomsSumbissionDetailComponent } from './symptoms-sumbission-detail.component';
import { SymptomsSumbissionUpdateComponent } from './symptoms-sumbission-update.component';

@Injectable({ providedIn: 'root' })
export class SymptomsSumbissionResolve implements Resolve<ISymptomsSumbission> {
  constructor(private service: SymptomsSumbissionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISymptomsSumbission> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((symptomsSumbission: HttpResponse<SymptomsSumbission>) => {
          if (symptomsSumbission.body) {
            return of(symptomsSumbission.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SymptomsSumbission());
  }
}

export const symptomsSumbissionRoute: Routes = [
  {
    path: '',
    component: SymptomsSumbissionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pandemicTrackingApp.symptomsSumbission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SymptomsSumbissionDetailComponent,
    resolve: {
      symptomsSumbission: SymptomsSumbissionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSumbission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SymptomsSumbissionUpdateComponent,
    resolve: {
      symptomsSumbission: SymptomsSumbissionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSumbission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SymptomsSumbissionUpdateComponent,
    resolve: {
      symptomsSumbission: SymptomsSumbissionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.symptomsSumbission.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
