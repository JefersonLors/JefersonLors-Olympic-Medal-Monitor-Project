import axios from 'axios';

export const api = axios.create({});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('authToken');
    config.headers.Authorization = `Bearer `

    if (token) {
        config.headers.Authorization += `${token}`;
    }
    return config;
});
