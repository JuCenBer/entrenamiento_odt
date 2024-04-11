import { HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';

const headersConfig = {
  'Accept': 'application/json', //default headers
};

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  let tokenReq: HttpRequest<any> = req.clone({
    setHeaders: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  })
  return next(tokenReq);
}