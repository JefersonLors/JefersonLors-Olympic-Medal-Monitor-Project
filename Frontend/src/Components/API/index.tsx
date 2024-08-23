const BASE_URL = "http://localhost:8081";

export const API_ENDPOINTS={
    LOGIN: `${BASE_URL}/authentication-ms/auth/signIn`,
    REGISTER: `${BASE_URL}/authentication-ms/auth/signUp`,
    COUNTRY_MEDAL: `${BASE_URL}/country/country`,
    COUNTRY_MEDALBY_ID: `${BASE_URL}/country/country/WithMedalsById/`,
    COUNTRY_GET_ALL_MODALITIES: `${BASE_URL}/country/sport`,
    COUNTRY_GET_ALL_MEDALS_TYPE: `${BASE_URL}/country/medal`,
    COUNTRY_ADD_MEDAL: `${BASE_URL}/country/medal`,
    NOTIFIER_FOLLOW_COUNTRY: `${BASE_URL}/notifier-ms/notifier/follow`,
    NOTIFIER_UNFOLLOW_COUNTRY: `${BASE_URL}/notifier-ms/notifier/unfollow`,
    NOTIFIER_NOTIFY_USER: `${BASE_URL}/notifier-ms/notifier/notify`,
    NOTIFER_GET_FOLLOWED_COUNTRIES: `${BASE_URL}/notifier-ms/notifier/followeds/id?id=`,
    USER_POST: `${BASE_URL}/user-ms/user`,
    USER_GET_BY_EMAIL:`${BASE_URL}/user-ms/user/email?email=`,
    GET_USER_ROLES: `${BASE_URL}/token-validator-ms/tokenValidator/roles`
};