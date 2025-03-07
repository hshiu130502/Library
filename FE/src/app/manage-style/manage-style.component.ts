import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ProductService} from "../product.service";
import {Account, Category, Storage} from "../app.module";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-style',
  templateUrl: './manage-style.component.html',
  styleUrls: ['./manage-style.component.css']
})
export class ManageStyleComponent implements OnInit{
  @ViewChildren('input') listInput: QueryList<ElementRef> | undefined;
  dataCategory:Category[]=[];
  inputName = '';
  inputSale = '';
  inputSearch = '';
  count = 0;
  dataCheckCategory:Category[]=[];
  category: Category | undefined;

  valueSave:any;


  admin:Account | undefined;

  isLogin2 = true;
  logOut = false;

  isClickMore = false;

  constructor(private productService:ProductService,
              private router: Router) {
  }
  ngOnInit() {
    this.getAllCategory(this.count);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }

  getAllCategory(count:number){
    this.productService.getCategory(count, 4).subscribe((res:any) => {
      this.dataCategory = res
    });
    this.productService.getCountCategories().subscribe((res:any) => {
      this.totalPages = Math.ceil(res/4);
    })
  }

  clickSearch(){
    this.productService.getCategoryByName(this.inputSearch, this.count, 4).subscribe((res:any) => {
      this.dataCategory = res;
      this.totalPages = 1;
    })
  }

  sendContentCategory(category:Category){
    let data = { messageFromManageStyle: category.name};
    this.router.navigate(['/manageBook', data]);
  }

  updateCategory(category:Category, input:string){
    let dataCheck:Category[]=[];
    const url = 'http://localhost:8080/category/updateCategorySale';
    const body = {id: category.id, sale: input}
    this.productService.putData(url, body).subscribe((res:any) => {
      dataCheck = res;
      if(dataCheck.length == 0) this.mess='Không có gì để thay đổi';
      else this.mess = 'Thay đổi thành công';
      this.isMess = true;
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCategory = res
      });
    });
  }

  saveNewCategory(){
    const url = 'http://localhost:8080/category/addNewCategory';
    if(this.inputSale == "") this.inputSale = "0";
    const body = {id: 0, name: this.inputName, sale: Number(this.inputSale)}
    let dataCheck:Category[]=[];
    this.productService.postData(url, body).subscribe((res:any) => {
      dataCheck = res;
      if(dataCheck.length == 0) this.mess='Thêm thất bại vui lòng kiểm tra lại các trường';
      else this.mess = 'Thêm mới thể loại ' + this.inputName + " thành công";
      this.isMess = true;
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCategory = res;
      });
      this.inputName = '';
      this.inputSale = '';
    });
  }

  clickMorefirst(){
    if (this.count < this.totalPages){
      this.count += 1;
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCategory = res;
      });
      this.inputName = '';
      this.inputSale = '';
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCategory = res
      })
    }
  }

  clickMore(){
    this.isClickMore = !this.isClickMore;
  }
  changeMoreStorage(){
    return{
      'display': this.isClickMore ? 'block' : 'none',
    }
  }
  changeLogout(){
    return{
      'display': this.logOut ? 'block' : 'none'
    }
  }

  clickUser(){
    this.logOut = !this.logOut;
  }

  clickLogout(){
    this.valueSave.clear();
  }
  clickManageStyle(){
    this.count = 0;
    this.getAllCategory(this.count);
  }
  isMess = false;
  mess = '';
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  clickCloseMess(){
    this.isMess = false;
    this.mess = '';
  }


  totalPages = 1;
  currentPage: number = 1;
  visiblePages: number = 3;

  getVisiblePages(): number[] {
    let pages: number[] = [];
    let startPage: number = Math.max(this.count, 2);
    let endPage: number = Math.min(startPage + this.visiblePages - 1, this.totalPages - 1);
    if(endPage)
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
    return pages;
  }

  selectPage(page: number) {
    this.count = page - 1;
    this.productService.getCategory(this.count, 4).subscribe((res:any) => {
      this.dataCategory = res
    })
  }
}
