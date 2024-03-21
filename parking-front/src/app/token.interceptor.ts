import { HttpInterceptorFn, HttpRequest } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  let tokenReq: HttpRequest<any> = req.clone({
    setHeaders: {
      "JWT": `${localStorage.getItem("token")}`
    }
  })
  return next(tokenReq);
};
