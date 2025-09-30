import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../models/book';

@Injectable({
  providedIn: 'root'
})

export class BookService {
    private apiUrl = 'http://localhost:8080/api/book';

    constructor(private http: HttpClient) {}

    getAllBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(this.apiUrl);
    }

    addBook(book: Book): Observable<Book[]> {
        return this.http.post<Book[]>(this.apiUrl, book);
    } 
}