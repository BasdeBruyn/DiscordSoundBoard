import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RedirectComponent} from './redirect/redirect.component';
import {HomeComponent} from './home/home.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'redirect', component: RedirectComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
