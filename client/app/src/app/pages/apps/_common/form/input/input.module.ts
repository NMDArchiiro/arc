import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { GlobitsInputComponent } from './input.component';
import { MatFormFieldModule } from '@angular/material/form-field';



@NgModule({
  declarations: [GlobitsInputComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule
  ],
  exports: [
    GlobitsInputComponent
  ]
})
export class GlobitsInputModule { }
