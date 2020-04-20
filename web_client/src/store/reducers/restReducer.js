import * as actionTypes from 'store/actions/actionTypes';

const initialState = {
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.RESET_STATE: return resetState(state, action);
        default:
            return state;
    }
}

const resetState = (state, action) => {
    return {
        ...state,
        ...initialState
    }
}

export default reducer;