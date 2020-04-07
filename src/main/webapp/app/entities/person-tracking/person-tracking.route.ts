import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonTracking, PersonTracking } from 'app/shared/model/person-tracking.model';
import { PersonTrackingService } from './person-tracking.service';
import { PersonTrackingComponent } from './person-tracking.component';
import { PersonTrackingDetailComponent } from './person-tracking-detail.component';
import { PersonTrackingUpdateComponent } from './person-tracking-update.component';

@Injectable({ providedIn: 'root' })
export class PersonTrackingResolve implements Resolve<IPersonTracking> {
  constructor(private service: PersonTrackingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonTracking> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personTracking: HttpResponse<PersonTracking>) => {
          if (personTracking.body) {
            return of(personTracking.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonTracking());
  }
}

export const personTrackingRoute: Routes = [
  {
    path: '',
    component: PersonTrackingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pandemicTrackingApp.personTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonTrackingDetailComponent,
    resolve: {
      personTracking: PersonTrackingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.personTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonTrackingUpdateComponent,
    resolve: {
      personTracking: PersonTrackingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.personTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonTrackingUpdateComponent,
    resolve: {
      personTracking: PersonTrackingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pandemicTrackingApp.personTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
