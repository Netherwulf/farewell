import * as actionTypes from 'store/actions/actionTypes';
import { authURL } from 'common/const';
import axios from 'axios';

export const login = (email, password) => {
    return dispatch => {
        dispatch(onLoginStart());
        const url = `http://${authURL}/login`;
        axios.post(url, { email: email, password: password })
            .then(response => {
                const authToken = response.data.authenticationToken ? response.data.authenticationToken : undefined;
                dispatch(onLogin(email, authToken));
            })
            .catch(error => {
                dispatch(onLoginError(error.response));
            });
    }
}

const onLogin = (email, authToken) => {
    return {
        type: actionTypes.AUTH_SUCCESS,
        email: email,
        authToken: authToken
    }
}

const onLoginStart = () => {
    return {
        type: actionTypes.AUTH_START
    }
}

const onLoginError = (error) => {
    return {
        type: actionTypes.AUTH_ERROR,
        error: error
    }
}

const onLogout = () => {
    return {
        type: actionTypes.AUTH_LOGOUT
    }
}

export const logout = () => {
    return dispatch => {
        dispatch(onLogout());
    }
}
