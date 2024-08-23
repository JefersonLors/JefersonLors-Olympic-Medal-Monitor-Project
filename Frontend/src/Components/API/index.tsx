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
    COUNTRY_MEDALBY_ID: `http://localhost:8085/country/WithMedalsById/`,
    COUNTRY_GET_ALL_MODALITIES: `http://localhost:8085/sport`,
    COUNTRY_GET_ALL_MEDALS_TYPE: `http://localhost:8085/medal`,
    COUNTRY_ADD_MEDAL: `http://localhost:8085/medal`,
    NOTIFIER_FOLLOW_COUNTRY: `http://localhost:8084/notifier/follow`,
    NOTIFIER_UNFOLLOW_COUNTRY: `http://localhost:8084/notifier/unfollow`,
    NOTIFIER_NOTIFY_USER: `http://localhost:8084/notifier/notify`,
    NOTIFER_GET_FOLLOWED_COUNTRIES: `http://localhost:8084/notifier/followeds/id?id=`,
    USER_POST: `http://localhost:8082/user`,
    USER_GET_BY_EMAIL:`http://localhost:8082/user/email?email=`,
    GET_USER_ROLES: `http://localhost:8087/tokenValidator/roles`
};