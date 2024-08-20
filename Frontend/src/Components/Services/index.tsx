import axios from "axios";
import { API_ENDPOINTS } from "../API";
import { api } from "../Interceptor/Interceptor";

export const apiService={
    login: (credencials)=>{ return api.post(API_ENDPOINTS.LOGIN, credencials)},
    register: (credencials)=>{return api.post(API_ENDPOINTS.REGISTER, credencials)},
    getCountries: ()=>{ return api.get(API_ENDPOINTS.COUNTRY_MEDAL)}
};