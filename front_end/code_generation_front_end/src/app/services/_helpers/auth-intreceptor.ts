import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "../auth.service";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    
    constructor(
        private auth: AuthService,
        private router: Router
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = !this.router.url.includes("/auth") && 
        !this.router.url.includes("/switch-password") && !this.router.url.includes("/sign_up") 
        ? this.auth.getHoldUserData().token : undefined;
        


        if(token != null) {
            req = req.clone({
                setHeaders: {
                    'Authorization' : `Bearer ${token}`
                }
            })
        }

        return next.handle(req);
    }

}