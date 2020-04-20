import * as actionTypes from 'store/actions/actionTypes';
import { authURL } from 'common/const';
import axios from 'axios';

export const login = (username, password) => {
    return dispatch => {
        dispatch(onLoginStart());
        const loginUrl = `${authURL}/login`;
        axios.post(loginUrl, { username: username, password: password })
            .then(response => {
                dispatch(onLogin(username));
            })
            .catch(error => {
                dispatch(onLoginError(error.response));
            });
    }
}

const onLogin = (username) => {
    return {
        type: actionTypes.AUTH_SUCCESS,
        username: username
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
