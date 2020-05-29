import * as actionTypes from 'store/actions/actionTypes';

const initialState = {
    isAuthenticated: process.env.NODE_ENV === 'production' ? false : false,
    isEmployee: true,
    email: null,
    error: null,
    authToken: null,
    userId: null
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.AUTH_START: return authStart(state, action);
        case actionTypes.AUTH_SUCCESS: return authLogin(state, action);
        case actionTypes.AUTH_ERROR: return authError(state, action);
        case actionTypes.AUTH_LOGOUT: return authLogout(state, action);
        default:
            return state;
    }
}

const authStart = (state, action) => {
    return {
        ...state,
        isStarted: true,
        error: null
    }
}

const authLogin = (state, action) => {
    return {
        ...state,
        isAuthenticated: true,
        isEmployee: true,
        email: action.email,
        authToken: action.authToken,
        userId: action.userId,
        isStarted: false
    }
}

const authError = (state, action) => {
    return {
        ...state,
        error: action.error,
        isStarted: false
    }
}

const authLogout = (state, action) => {
    return {
        ...state,
        isAuthenticated: false,
        isEmployee: false,
        authToken: null,
        email: null,
        userId: null
    }
}

export default reducer;