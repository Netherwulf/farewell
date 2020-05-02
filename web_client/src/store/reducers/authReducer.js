import * as actionTypes from 'store/actions/actionTypes';

const initialState = {
    isAuthenticated: process.env.NODE_ENV === 'production' ? false : false,
    username: null,
    error: null
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
        username: action.username,
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
        username: null
    }
}

export default reducer;