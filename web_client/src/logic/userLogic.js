import * as RestClient from 'api/REST/RestClient';

export const registerUser = async (userData) => {
    const response = await RestClient.postUser(userData);
    const result = {
        isError: false,
        errorMessage: ''
    };
    if (response && response.authenticationToken) {
        const authToken = response.authenticationToken;
    } else {
        result.isError = true;
        if (response)
            result.errorMessage = response.message;
        else
            result.errorMessage = "No response from server"
    }
    return result;
}