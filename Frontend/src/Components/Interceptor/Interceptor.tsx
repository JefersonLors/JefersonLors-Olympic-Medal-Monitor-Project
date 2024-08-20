import axios from "axios";
import { API_ENDPOINTS } from "../API";


export const api = axios.create({});

api.interceptors.request.use(config => {
    const token = localStorage.getItem('authToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
