import axios from "axios";
import { API_ENDPOINTS } from "../API";
import { api } from "../Interceptor/Interceptor";

export const apiService={
    login: (credencials)=>{ return api.post(API_ENDPOINTS.LOGIN, credencials)},
    register: (credencials)=>{return api.post(API_ENDPOINTS.REGISTER, credencials)},
    getCountries: ()=>{ return api.get(API_ENDPOINTS.COUNTRY_MEDAL)},
    getCountryById: (id)=>{ return api.get(API_ENDPOINTS.COUNTRY_MEDALBY_ID + id)},
    getAllModalities: ()=>{ return api.get(API_ENDPOINTS.COUNTRY_GET_ALL_MODALITIES)},
    getAllMedalTypes: ()=>{ return api.get(API_ENDPOINTS.COUNTRY_GET_ALL_MEDALS_TYPE)},
    addMedal: (data)=>{ return api.post(API_ENDPOINTS.COUNTRY_ADD_MEDAL, data)},
    followCountry: (followObject)=>{return api.post(API_ENDPOINTS.NOTIFIER_FOLLOW_COUNTRY, followObject)},
    unfollowCountry: (unfollowObject)=>{return api.post(API_ENDPOINTS.NOTIFIER_UNFOLLOW_COUNTRY, unfollowObject)},
    notifyUser: (data)=>{ return api.post(API_ENDPOINTS.NOTIFIER_NOTIFY_USER, data)},
    postUser: (user)=>{return api.post(API_ENDPOINTS.USER_POST, user)},
    getUserByEmail: (email)=>{ return api.get(API_ENDPOINTS.USER_GET_BY_EMAIL+email)},
    getFollowedCountries: (id)=>{ return api.get(API_ENDPOINTS.NOTIFER_GET_FOLLOWED_COUNTRIES+id)},
    getUserRoles: (token) => { return api.post(API_ENDPOINTS.GET_USER_ROLES, token)}
};