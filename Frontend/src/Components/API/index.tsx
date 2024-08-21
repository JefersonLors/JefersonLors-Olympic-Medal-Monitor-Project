/*
const BASE_URL = "http://localhost:8081/";

export const API_ENDPOINTS={
    LOGIN: `${BASE_URL}authentication-ms/auth/signIn`,
    REGISTER: `${BASE_URL}authentication-ms/auth/signUp`
};
*/

export const API_ENDPOINTS={
    LOGIN: `http://localhost:8086/auth/signIn`,
    REGISTER: `http://localhost:8086/auth/signUp`,
    COUNTRY_MEDAL: `http://localhost:8085/country`,
    COUNTRY_MEDALBY_ID: `http://localhost:8085/country/ById/`,
    NOTIFIER_FOLLOW_COUNTRY: `http://localhost:8084/notifier/follow`,
    USER_POST: `http://localhost:8082/user`,
    USER_GET_BY_EMAIL:`http://localhost:8082/user/email?email=`
};