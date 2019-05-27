import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { LmsSharedLibsModule, LmsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [LmsSharedLibsModule, LmsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [LmsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsSharedModule {
  static forRoot() {
    return {
      ngModule: LmsSharedModule
    };
  }
}
