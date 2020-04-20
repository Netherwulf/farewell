import * as actionTypes from 'store/actions/actionTypes';

const clearState = () => {
    return {
        type: actionTypes.RESET_STATE
    }
}

export const resetState = () => {
    return dispatch => {
        dispatch(clearState());
    }
}