import axios from "axios";
import { API_ENDPOINTS } from "../API";
import { api } from "../Interceptor/Interceptor";

export const apiService={
    login: (credencials)=>{ return api.post(API_ENDPOINTS.LOGIN, credencials)},
    register: (credencials)=>{return api.post(API_ENDPOINTS.REGISTER, credencials)},
    getCountries: ()=>{ return api.get(API_ENDPOINTS.COUNTRY_MEDAL)},
    getCountryById: (id)=>{ return api.get(API_ENDPOINTS.COUNTRY_MEDALBY_ID + id)},
    followCountry: (followObject)=>{return api.post(API_ENDPOINTS.NOTIFIER_FOLLOW_COUNTRY, followObject)},
    unfollowCountry: (unfollowObject)=>{return api.post(API_ENDPOINTS.NOTIFIER_UNFOLLOW_COUNTRY, unfollowObject)},
    postUser: (user)=>{return api.post(API_ENDPOINTS.USER_POST, user)},
    getUserByEmail: (email)=>{ return api.get(API_ENDPOINTS.USER_GET_BY_EMAIL+email)},
    getFollowedCountries: (id)=>{ return api.get(API_ENDPOINTS.NOTIFER_GET_FOLLOWED_COUNTRIES+id)}
};